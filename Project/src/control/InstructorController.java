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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Course;
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
	
	@FXML
	private TextField searchTextField;
	
	private Parent root;
	private Stage stage;
	private Scene scene;
	private List<Instructor> observedInstructor = new LinkedList<>();
	private ObservableList<Instructor> listInstructor = FXCollections.observableList(observedInstructor);
	private AvailabilityController aController;
	
	public void setInstance(Parent root,Stage stage, Scene scene) {
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
	}
	public void instructorInfo(Instructor in) {
		instID.setText(in.getID()+"");
		instCell.setText(in.getCell());
		instHPhone.setText(in.getHomePhone());
		instName.setText(in.getName());
		instAddress.setText(in.getAddress());
		instHire.setText(in.getHireDate());
		instRanking.setText(in.getRank().name());
		instHome.setText(in.getHomeCampus().name());
		instCount.setText(in.getCourseCount()+"");
		instOnline.setText((in.getOnline()) ? "Yes": "No");
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (Course crs : in.getCourses()) {
			sb.append(crs.toString().split(",")[0]+" ");
			if (i%5==0&&i!=0)
				sb.append('\n');
			i++;
		}
		instCourses.setText(sb.toString());
	}
	public void refresh(KeyEvent e) {
		String str = searchTextField.getText();
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
}
