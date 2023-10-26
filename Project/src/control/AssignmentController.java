package control;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Instructor;
import model.Section;

public class AssignmentController {
	
	@FXML
	private VBox vbox;
	@FXML
	private HBox hbox;
	@FXML
	private ListView<Instructor> instructor;
	@FXML
	private ListView<Section> section;
	@FXML
	private Text sectionName;
	@FXML
	private Button remove;
	@FXML
	private Button add;
	@FXML
	private Button clear;
	
	private Section currSec;
	private Instructor currInst;
	
	private List<Instructor> observedInstructor = new LinkedList<>();
	private ObservableList<Instructor> listInstructor = FXCollections.observableList(observedInstructor);
	private List<Section> observedSection = new LinkedList<>();
	private ObservableList<Section> listSection = FXCollections.observableList(observedSection);
	
	
	private AvailabilityController aController;
	private Parent root;
	private Stage stage;
	private Scene scene;
	public void setInstance(Parent root,Stage stage, Scene scene) {
		this.root = root;
		this.stage = stage;
		this.scene = scene;
		hbox.getChildren().remove(1);
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
						aController.refresh(cell.getItem().getAvailability());
						hbox.getChildren().remove(0);
						hbox.getChildren().add(0,section);
						observedSection.clear();
						listSection.setAll(Section.SectionFactory.getStream()
								.filter((Section s)->{
									return cell.getItem().getCourses().contains(s.getCourse());
								}).toList());
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
							setText(item.getCourse().toString() + " " + item.getBegin() + " " + item.getEnd());
						else
							setText("");
					}
				};
				cell.setOnMouseClicked(e->{
					currSec = cell.getItem();
					sectionName.setText(currSec.toString());
					});
				return cell;
			}});
		instructor.setItems(listInstructor);
		section.setItems(listSection);
		FXMLLoader loader;
		try {
			loader = new FXMLLoader(new File("src/view/Availability.fxml").toURI().toURL());
			Parent aRoot = loader.load();
			vbox.getChildren().add(0,aRoot);
			aController = loader.getController();
			aController.setInstance(aRoot, stage, scene);
		} catch (IOException e) {}
	}
	public void refresh(String str) {
		sectionName.setText("");
		currSec = null;
		currInst = null;
		if (hbox.getChildren().get(0)!=instructor) {
			hbox.getChildren().remove(0);
			hbox.getChildren().add(0,instructor);
		}
		observedInstructor.clear();
		observedInstructor.addAll(Instructor.InstructorFactory.getStream().toList());
		listInstructor.setAll(observedInstructor.stream()
				.filter((Instructor i)->{
					try {
						Integer.parseInt(str);
						return (i.getID()+"").contains(str);
					} catch (NumberFormatException exception) {
							return i.getName().toLowerCase()
								.contains(str.toLowerCase());
					}
				}).toList());
	}
	public void add(ActionEvent e) {
		currInst.addSection(currSec);
		refresh("");
	}
	public void remove(ActionEvent e) {
		currInst.removeSection(currSec);
		refresh("");
	}
	public void clear(ActionEvent e) {
		currInst.getSections().forEach((Section src)->{currInst.removeSection(src);});
		refresh("");
	}
}