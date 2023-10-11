package control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Instructor;
@SuppressWarnings("unused")
public class InstructorController {
	@FXML
	private ListView<Instructor> instResults;
	@FXML
	private HBox hbox1;
	@FXML
	private Text instID;
	@FXML
	private Text instCell;
	@FXML
	private Text instHPhone;
	@FXML
	private Text instName;
	@FXML
	private Text instAddress;
	@FXML
	private Text instHire;
	@FXML
	private Text instRanking;
	@FXML
	private Text instHome;
	@FXML
	private Text instOnline;
	@FXML
	private Text instCourses;
	@FXML
	private Text instCount;
	private Parent root;
	private Stage stage;
	private Scene scene;
	private List<Instructor> originalInstructor = new LinkedList<>();
	private List<Instructor> observedInstructor = new LinkedList<>();
	private ObservableList<Instructor> listInstructor = FXCollections.observableList(observedInstructor);
	private AvailabilityController aController;
	
	public void setInstance(Parent root,Stage stage, Scene scene, Collection<Instructor> ints) {
		this.root = root;
		this.stage = stage;
		this.scene = scene;
		instResults.setCellFactory(new Callback<ListView<Instructor>,ListCell<Instructor>>(){
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
						aController.refresh(cell.getItem().getAvailability());
						instructorInfo(cell.getItem());
					}});
				return cell;
			}});
		instResults.setItems(listInstructor);
		FXMLLoader loader;
		try {
			loader = new FXMLLoader(new File("src/view/Availability.fxml").toURI().toURL());
			Parent aRoot = loader.load();
			hbox1.getChildren().add(1,aRoot);
			aController = loader.getController();
			aController.setInstance(aRoot, stage, scene);
		} catch (IOException e) {}
		originalInstructor.addAll(ints);
	}
	//helper methods (string and node parsing)
	public void instructorInfo(Instructor in) {
		String[] arr = in.toString()
				.substring(0,in.toString().indexOf("Availability"))
				.split("[\\[\\]]");
		int[] idxs = {1,3,5,7};
		ArrayList<String[]> list = new ArrayList<>();
		for (int i : idxs)
			list.add(arr[i].split(", (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"));
		Text[] texts = new Text[] {instID,instCell,instHPhone,instName,instAddress,instHire,instRanking,instHome,instCount};
		for (int i=0;i<list.get(0).length;i++) {
			texts[i].setText(list.get(0)[i].split(": ")[1].replace("\"", ""));
		}
		for (int i=0;i<list.get(1).length;i++) {
			texts[i+5].setText(list.get(1)[i].split(": ")[1].replace("\"", ""));
		}
		instOnline.setText(list.get(2)[0]);
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<list.get(3).length;i++) {
			sb.append(list.get(3)[i].split(",")[0]+" ");
			if (i%5==0&&i!=0)
				sb.append('\n');
		}
		instCourses.setText(sb.toString());
	}
	public void refresh(String str) {
		observedInstructor.clear();
		observedInstructor.addAll(originalInstructor);
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
}
