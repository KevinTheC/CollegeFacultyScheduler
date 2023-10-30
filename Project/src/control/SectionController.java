package control;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Availability;
import model.Day;
import model.Instructor;
import model.Section;
@SuppressWarnings("unused")
public class SectionController {
	@FXML
	private ListView<Section> sectionResults;
	@FXML
	private Text sectCourse;
	@FXML
	private Text sectCRN;
	@FXML
	private Text sectCampus;
	@FXML
	private Text sectOnline;
	@FXML
	private Text sectPart;
	@FXML
	private Text sectDay;
	@FXML
	private Text sectTime;
	@FXML
	private HBox hbox1;
	@FXML
	private TextField searchTextField;
	
	
	private AvailabilityController aController;
	private List<Section> observedSection = new LinkedList<>();
	private ObservableList<Section> listSection = FXCollections.observableList(observedSection);
	private Parent root;
	private Stage stage;
	private Scene scene;
	public void setInstance(Parent root,Stage stage, Scene scene) {

		this.root = root;
		this.stage = stage;
		this.scene = scene;
		sectionResults.setCellFactory(new Callback<ListView<Section>,ListCell<Section>>(){
			@Override
			public ListCell<Section> call(ListView<Section> arg) {
				ListCell<Section> cell = new ListCell<Section>(){
					@Override
					protected void updateItem(Section item, boolean empty) {
						super.updateItem(item, empty);
						if (!empty || item != null)
							setText(item.getCourse().getCourseID()+" "+item.getCrn());
						else
							setText("");
					}
				};
				cell.setOnMouseClicked(e->{
					if (cell.getItem()!=null) {
						sectionInfo(cell.getItem());
					}});
				return cell;
			}});
		sectionResults.setItems(listSection);
		try {
			FXMLLoader loader = new FXMLLoader(new File("src/view/Availability.fxml").toURI().toURL());
			Parent aRoot = loader.load();
			hbox1.getChildren().add(1,aRoot);
			aController = loader.getController();
			aController.setInstance(aRoot, stage, scene);
		} catch (IOException e) {}
	}
	public void sectionInfo(Section sec) {
		sectCourse.setText(sec.getCourse().toString());
		sectCRN.setText(sec.getCrn()+"");
		sectCampus.setText(sec.getCampus().toString());
		sectOnline.setText((sec.getInPerson())? "BLBD":"TR");
		sectPart.setText(sec.getPartOfTerm().toString());
		sectDay.setText(sec.getDays().toString().substring(1,sec.getDays().toString().length()-1));
		sectTime.setText((sec.getBegin()!=null) ? sec.getBegin().toString(false)+" - "+sec.getEnd().toString(false) : "Online");
		Availability<Section> a = new Availability<>();
		Day[] days = new Day[sec.getDays().size()];
		for (int i=0;i<sec.getDays().size();i++)
			days[i] = sec.getDays().get(i);
		a.put(sec, sec.getBegin(), sec.getEnd(), days);
		aController.refresh(a);
	}
	public void refresh(KeyEvent e) {
		String str = searchTextField.getText();
		observedSection.clear();
		observedSection.addAll(Section.SectionFactory.getStream().toList());
		listSection.setAll(observedSection.stream()
				.filter((Section s)->{
					return s.getCourse().getCourseID().toUpperCase().contains(str.toUpperCase())||
							s.getCourse().getCourseSubject().toUpperCase().contains(str.toUpperCase())||
							(s.getCrn()+"").contains(str);
				}).toList());
	}
}
