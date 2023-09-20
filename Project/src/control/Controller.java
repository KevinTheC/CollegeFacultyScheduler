package control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Instructor;
import view.AVLView;

public class Controller {
	@FXML
	private ListView<Instructor> results;
	@FXML
	private TextField searchTextField;
	
	private AVLView avl;
	private ArrayList<Instructor> original = new ArrayList<>();
	private ArrayList<Instructor> observed = new ArrayList<>();
	private ObservableList<Instructor> list = FXCollections.observableList(observed);
	private Parent root;
	private Stage stage;
	private Scene scene;
	
	@SuppressWarnings("unchecked")
	public void setInstance(Parent root,Stage stage, Scene scene,Collection<Instructor> ints) {
		this.root = root;
		this.stage = stage;
		this.scene = scene;
		avl = new AVLView();
		results = (ListView<Instructor>) getComponent("results").get();
		searchTextField = (TextField) getComponent("searchTextField").get();
		results.setCellFactory(new Callback<ListView<Instructor>,ListCell<Instructor>>(){
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
		results.setItems(list);
		((HBox)getComponent("vbox1").get()).getChildren().add(1,avl);
		original.addAll(ints);
	}
	//handlers
	public void keyRelease(KeyEvent e) {
		observed.clear();
		observed.addAll(original);
		list.setAll(observed.stream()
				.filter((Instructor i)->{
					try {
						Integer.parseInt(e.getText());
						return (i.getID()+"").contains(searchTextField.getText());
					} catch (NumberFormatException exception) {
						return i.getName().toLowerCase()
								.contains(searchTextField.getText().toLowerCase());
					}
				}).toList());
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
		for (String str : list.get(0)) {
			arr = str.split(": ");
			arr[0] = arr[0].replace(" ", "_");
			((BorderPane)getComponent(arr[0]).get()).setCenter(new Text(arr[1].replace("\"", "")));
		}
		for (String str : list.get(1)) {
			arr = str.split(": ");
			arr[0] = arr[0].replace(" ", "_");
			((BorderPane)getComponent(arr[0]).get()).setCenter(new Text(arr[1].replace("\"", "")));
		}
		((BorderPane)getComponent("Online?").get()).setCenter(new Text(list.get(2)[0]));
		((BorderPane)getComponent("CourseList").get()).setCenter(new Text(list.get(3)[0]));
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
