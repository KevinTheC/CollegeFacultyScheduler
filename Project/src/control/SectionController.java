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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
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
	}
	public void sectionInfo(Section sec) {
		sectCourse.setText(sec.getCourse().toString());
		sectCRN.setText(sec.getCrn()+"");
		sectCampus.setText(sec.getCampus().toString());
		Function<Boolean,String> func = (bool)->{if (bool) return "BLBD"; return "TR";};
		sectOnline.setText(func.apply(sec.getInPerson()));
		sectPart.setText(sec.getPartOfTerm().toString());
		sectDay.setText(sec.getDays().toString().substring(1,sec.getDays().toString().length()-1));
		sectTime.setText(sec.getBegin()+" - "+sec.getEnd());
	}
	public void refresh(String str) {
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
