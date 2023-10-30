package control;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Availability;
import model.Day;
import model.Instructor;
import model.Section;
import model.IO.SCCC.SCCCImports;
import model.chrono.TimeRange;

public class AssignmentController {
	@FXML
	private Text text;
	@FXML
	private HBox hboxInstructor;
	@FXML
	private HBox hboxSection;
	@FXML
	private ListView<Instructor> instructor;
	@FXML
	private ListView<Section> section;
	@FXML
	private TextField searchTextField;
	@FXML
	private Button addDrop;
	@FXML
	private VBox legend;
	
	
	private Section currSec;
	private Instructor currInst;
	private enum Mode {
		Instructor,
		Section;
	}
	private Mode mode;
	
	
	private List<Instructor> observedInstructor = new LinkedList<>();
	private ObservableList<Instructor> listInstructor = FXCollections.observableList(observedInstructor);
	private List<Section> observedSection = new LinkedList<>();
	private ObservableList<Section> listSection = FXCollections.observableList(observedSection);
	
	
	private AvailabilityController instructorAVLController;
	private AvailabilityController sectionAVLController;
	private Parent root;
	private Stage stage;
	private Scene scene;
	public void setInstance(Parent root,Stage stage, Scene scene) {
		mode = Mode.Instructor;
		this.root = root;
		this.stage = stage;
		this.scene = scene;
		this.currSec = null;
		this.currInst = null;
		instructor.setCellFactory(new Callback<ListView<Instructor>,ListCell<Instructor>>(){
			@Override
			public ListCell<Instructor> call(ListView<Instructor> arg) {
				ListCell<Instructor> cell = new ListCell<Instructor>(){
					@Override
					protected void updateItem(Instructor item, boolean empty) {
						super.updateItem(item, empty);
						if (!empty || item != null)
							setText(item.getName());
						else
							setText("");
					}
				};
				cell.setOnMouseClicked(e->{
					if (cell.getItem()!=null) {
						currInst = cell.getItem();
						refreshAVL(cell.getItem());
						mode = Mode.Section;
						addDrop.setText("");
						searchTextField.setText("");
						text.setText("Search for a section");
						currSec = null;
						refresh(null);
					}});
				return cell;
			}});
		section.setCellFactory(new Callback<ListView<Section>,ListCell<Section>>(){
			@Override
			public ListCell<Section> call(ListView<Section> arg) {
				ListCell<Section> cell = new ListCell<Section>(){
					@Override
					protected void updateItem(Section item, boolean empty) {
						super.updateItem(item, empty);
						if (!empty || item != null)
							setText(item.getCourse().toString().split("[,]")[0] + " " + ((item.getBegin()!=null)?item.getBegin():"Online") + " " + ((item.getEnd()!=null)?item.getEnd():""));
						else
							setText("");
					}
				};
				cell.setOnMouseClicked(e->{
					currSec = cell.getItem();
					Availability<Section> a = new Availability<>();
					Day[] days = new Day[currSec.getDays().size()];
					for (int i=0;i<currSec.getDays().size();i++)
						days[i] = currSec.getDays().get(i);
					a.put(currSec, currSec.getBegin(), currSec.getEnd(), days);
					sectionAVLController.refresh(a);
					if (currInst.getSections().toList().contains(currSec))
						addDrop.setText("Drop");
					else
						addDrop.setText("Add");
					});
				return cell;
			}});
		instructor.setItems(listInstructor);
		section.setItems(listSection);
		FXMLLoader loader;
		try {
			loader = new FXMLLoader(new File("src/view/Availability.fxml").toURI().toURL());
			Parent aRoot = loader.load();
			hboxInstructor.getChildren().add(aRoot);
			instructorAVLController = loader.getController();
			instructorAVLController.setInstance(aRoot, stage, scene);
		} catch (IOException e) {}
		try {
			loader = new FXMLLoader(new File("src/view/Availability.fxml").toURI().toURL());
			Parent aRoot = loader.load();
			hboxSection.getChildren().add(1,aRoot);
			sectionAVLController = loader.getController();
			sectionAVLController.setInstance(aRoot, stage, scene);
		} catch (IOException e) {}
	}
	public void refresh(KeyEvent e) {
		String str = searchTextField.getText();
		if (mode == Mode.Instructor) {
			listInstructor.setAll(Instructor.InstructorFactory.getStream()
					.filter((Instructor i)->{
						try {
							Integer.parseInt(str);
							return (i.getID()+"").contains(str);
						} catch (NumberFormatException exception) {
								return i.getName().toLowerCase()
									.contains(str.toLowerCase());
						}
					}).toList());
		} else {
			listSection.clear();
			listSection.addAll(currInst.getSections().toList());
			if (currInst.getSections().toList().size()<currInst.getCourseCount())
				listSection.addAll(Section.SectionFactory.getStream()
					.filter((Section s)->{
						return currInst.getCourses().contains(s.getCourse());
					})
					.filter((Section s)->{
						
						return !currInst.getSections().toList().contains(s);})
					.filter((Section s)->{
						if (s.getBegin()==null)
							return true;
						var avl = currInst.getAvailability();
						TimeRange<Section> t = new TimeRange<>(s,s.getBegin(),s.getEnd());
						for (Day d : s.getDays()) {
							if (avl.getSchedule(d).toRanges().stream()
									.filter((TimeRange range)->{return Section.empty.equals(range.getType());})
									.filter((TimeRange range)->{
											return (t.getBegin().compareTo(range.getBegin())>-1&&t.getEnd().compareTo(range.getEnd())<1);
										}).count()==0) {
								return false;
							}
							}
						return true;
					}).toList());
		}
	}
	public void addDropAction(ActionEvent e) {
		if (currInst==null||currSec==null)
			return;
		if (currInst.getSections().toList().contains(currSec)) {
			currInst.removeSection(currSec);
		} else {
			currInst.addSection(currSec);
		}
		SCCCImports.save();
		unlock(null);
	}
	public void unlock(ActionEvent e) {
		currInst = null;
		currSec = null;
		searchTextField.setText("");
		addDrop.setText("");
		text.setText("Search for Instructors");
		legend.getChildren().clear();
		mode = Mode.Instructor;
	}
	public void refreshAVL(Instructor in) {
		legend.getChildren().clear();
		instructorAVLController.refresh(in.getAvailability());
		List<Section> sec = in.getSections().toList();
		Color[] colors = {Color.BLACK,Color.GREEN,Color.YELLOW,Color.BROWN};
		for (int i=0;i<sec.size();i++) {
			legend.getChildren().add(new Leg(colors[i],sec.get(i)));
		}
	}
	public class Leg extends HBox{
		private Leg(Color c,Section s){
			super();
			getChildren().add(new Rectangle(5,5,c));
			getChildren().add(new Text(s.getCourse().toString()));
		}
	}
}