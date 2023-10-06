package control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Instructor;
import model.Section;
import view.AVLView;

public class Controller {
	@FXML
	private ListView<Instructor> instResults;
	@FXML
	private ListView<Section> sectionResults;
	@FXML
	private TextField searchTextField;
	@FXML
	private HBox hbox1;
	@FXML
	private Text titleText;
	@FXML
	private StackPane stack;
	@FXML
	private Button switcher;
	
	
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
	
	private AVLView avl;
	private List<Instructor> originalInstructor = new LinkedList<>();
	private List<Section> originalSection = new LinkedList<>();
	private List<Section> observedSection = new LinkedList<>();
	private List<Instructor> observedInstructor = new LinkedList<>();
	private ObservableList<Instructor> listInstructor = FXCollections.observableList(observedInstructor);
	private ObservableList<Section> listSection = FXCollections.observableList(observedSection);
	private Parent root;
	private Stage stage;
	private Scene scene;
	boolean instructorMode;
	
	public void setInstance(Parent root,Stage stage, Scene scene,Collection<Instructor> ints,Collection<Section> sec) {
		instructorMode = true;
		this.root = root;
		this.stage = stage;
		this.scene = scene;
		avl = new AVLView();
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
						avl.refresh(cell.getItem().getAvailability());
						instructorInfo(cell.getItem());
					}});
				return cell;
			}});
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
		instResults.setItems(listInstructor);
		sectionResults.setItems(listSection);
		hbox1.getChildren().add(1,avl);
		originalInstructor.addAll(ints);
		originalSection.addAll(sec);
	}
	public void onAction(ActionEvent e) {
		stack.getChildren().add(stack.getChildren().remove(0));
		if (instructorMode) {
			titleText.setText("Type an ID or name");
			keyRelease(null);
		}
		else
			titleText.setText("Type a section name");
		instructorMode = !instructorMode;
	}
	//handlers
	public void keyRelease(KeyEvent e) {
		if (instructorMode) {
			observedInstructor.clear();
			observedInstructor.addAll(originalInstructor);
			listInstructor.setAll(observedInstructor.stream()
					.filter((Instructor i)->{
						try {
							Integer.parseInt(searchTextField.getText());
							return (i.getID()+"").contains(searchTextField.getText());
						} catch (NumberFormatException exception) {
							return i.getName().toLowerCase()
									.contains(searchTextField.getText().toLowerCase());
						}
					}).toList());
		} else {
			observedSection.clear();
			observedSection.addAll(originalSection);
			listSection.setAll(observedSection.stream()
					.filter((Section s)->{
						return s.getCourse().getCourseID().toUpperCase().contains(searchTextField.getText().toUpperCase())||
								s.getCourse().getCourseSubject().toUpperCase().contains(searchTextField.getText().toUpperCase())||
								(s.getCrn()+"").contains(searchTextField.getText());
					}).toList());
		}
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
	public String combine(String[] arr) {
		String str = arr[0];
		for (int i=1;i<arr.length;i++)
			str+="\n"+arr[i];
		return str;
	}
	public Optional<Node> getComponent(String id){
		Node n = scene.lookup("#"+id);
		if (n==null)
			return Optional.empty();
		return Optional.of(n);
	}
}