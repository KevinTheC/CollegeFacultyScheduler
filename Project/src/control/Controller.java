package control;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Instructor;
import model.Section;
@SuppressWarnings("unused")
public class Controller {
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
	
	private InstructorController iC;
	private SectionController sC;
	private AssignmentController aC;
	private Mode mode;
	private Parent root;
	private Stage stage;
	private Scene scene;
	private enum Mode{
		InstructorView,
		SectionView,
		AssignmentView;
		private final static Mode rotate(Mode old) {
			if (old == Mode.values()[Mode.values().length-1])
				return Mode.values()[0];
			return Mode.values()[1+old.ordinal()];
		}
	}
	
	public void setInstance(Parent root,Stage stage, Scene scene,Collection<Instructor> ints,Collection<Section> sec) {
		mode = Mode.AssignmentView;
		this.root = root;
		this.stage = stage;
		this.scene = scene;
		FXMLLoader loader;
		try {
			loader = new FXMLLoader(new File("src/view/InstructorView.fxml").toURI().toURL());
			Parent aRoot = loader.load();
			stack.getChildren().add(aRoot);
			iC = loader.getController();
			iC.setInstance(aRoot, stage, scene, ints);
		} catch (IOException e) {}
		try {
			loader = new FXMLLoader(new File("src/view/SectionView.fxml").toURI().toURL());
			Parent aRoot = loader.load();
			stack.getChildren().add(aRoot);
			sC = loader.getController();
			sC.setInstance(aRoot, stage, scene, sec);
		} catch (IOException e) {}
		try {
			loader = new FXMLLoader(new File("src/view/AssignmentView.fxml").toURI().toURL());
			Parent aRoot = loader.load();
			stack.getChildren().add(aRoot);
			aC = loader.getController();
			aC.setInstance(aRoot, stage, scene, ints, sec);
		} catch (IOException e) {}
	}
	public void onAction(ActionEvent e) {
		stack.getChildren().add(stack.getChildren().remove(0));
		if (mode == Mode.InstructorView) {
			keyRelease(null);
		}
		else if (mode == Mode.SectionView){
			titleText.setText("Type a section name");
			keyRelease(null);
		} else {
			titleText.setText("Type an ID or name");
			keyRelease(null);
		}
		mode = Mode.rotate(mode);
	}
	//handlers
	public void keyRelease(KeyEvent e) {
		if (mode == Mode.InstructorView)
			iC.refresh(searchTextField.getText());
		else if (mode == Mode.SectionView)
			sC.refresh(searchTextField.getText());
		else
			aC.refresh(searchTextField.getText());
	}
}