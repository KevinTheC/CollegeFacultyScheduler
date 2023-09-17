package control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Instructor;
import view.AVLView;

public class Controller {
	@FXML
	private TextArea infoOutputArea;
	@FXML
	private ListView<Instructor> results;
	@FXML
	private TextField searchTextField;
	@FXML
	private Pane pane;
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
		pane = (Pane) getComponent("pane").get();
		infoOutputArea = (TextArea) getComponent("infoOutputArea").get();
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
						String[] arr = instructorInfo(cell.getItem());
						infoOutputArea.setText(combine(arr));
					}});
				return cell;
			}});
		results.setItems(list);
		pane.getChildren().add(avl);
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
						return i.getName().contains(searchTextField.getText());
					}
				}).toList());
		int i =0 ;
	}
	//helper methods (string and node parsing)
	public String[] instructorInfo(Instructor in) {
		String[] arr = in.toString()
				.substring(0,in.toString().indexOf("Availability"))
				.split("[\\[\\]]");
		arr = new String[]{arr[1],arr[3],arr[5],arr[7]};
		for (int i=0;i<arr.length-1;i++) {
			arr[i]=arr[i].replaceAll(", (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)","\n");
		}
		return arr;
	}
	public String combine(String[] arr) {
		String str = arr[0];
		for (int i=1;i<arr.length;i++)
			str+="\n"+arr[i];
		return str;
	}
	@SuppressWarnings("unchecked")
	public <K> Optional<K> getComponent(String id) throws ClassCastException{
		//TODO this should check depth-wise for nodes in the future instead of just the root's children
		List<Node> list = root.getChildrenUnmodifiable()
			.filtered((Node n)->{return n.getId().equals(id);});
		if (list.size()!=1) return Optional.empty();
			return Optional.of((K)list.get(0));
	}
}
