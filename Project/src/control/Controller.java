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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Instructor;
import model.Section;
@SuppressWarnings("unused")
public class Controller {
	@FXML
	private HBox hbox1;
	@FXML
	private Text titleText;
	@FXML
	private VBox anchor;
	@FXML
	private Button instructorTab;
	@FXML
	private Button sectionTab;
	@FXML
	private Button assignmentTab;
	@FXML
	private Button dataTab;
	
	
	private Button[] buttons;
	private Parent root;
	private Stage stage;
	private Scene scene;
	
	public void setInstance(Parent root,Stage stage, Scene scene) {
		this.root = root;
		this.stage = stage;
		this.scene = scene;
		buttons = new Button[4];
		buttons[0] = instructorTab;
		buttons[1] = sectionTab;
		buttons[2] = assignmentTab;
		buttons[3] = dataTab;
	}
	public void onAction(ActionEvent event) {
		for (Button b : buttons) {
			b.getStyleClass().removeIf((code)->{return code.contains("beige");});
			b.getStyleClass().add("beigeTab");
		}
		((Button)event.getSource()).getStyleClass().remove(((Button)event.getSource()).getStyleClass().size()-1);
		((Button)event.getSource()).getStyleClass().add("beigeTabClicked");
		anchor.getChildren().clear();
		FXMLLoader loader;
		if (event.getSource()==instructorTab) {
			try {
				loader = new FXMLLoader(new File("src/view/InstructorView.fxml").toURI().toURL());
				Parent aRoot = loader.load();
				anchor.getChildren().add(aRoot);
				InstructorController iC = loader.getController();
				iC.setInstance(aRoot, stage, scene);
			} catch (IOException e) {}
		} else if (event.getSource()==sectionTab) {
			try {
				loader = new FXMLLoader(new File("src/view/SectionView.fxml").toURI().toURL());
				Parent aRoot = loader.load();
				anchor.getChildren().add(aRoot);
				SectionController sC = loader.getController();
				sC.setInstance(aRoot, stage, scene);
			} catch (IOException e) {}
		} else if (event.getSource()==assignmentTab) {
			try {
				loader = new FXMLLoader(new File("src/view/AssignmentView.fxml").toURI().toURL());
				Parent aRoot = loader.load();
				HBox.setHgrow(aRoot, Priority.ALWAYS);
				VBox.setVgrow(aRoot, Priority.ALWAYS);
				anchor.getChildren().add(aRoot);
				AssignmentController aC = loader.getController();
				aC.setInstance(aRoot, stage, scene);
			} catch (IOException e) {e.printStackTrace();}
		} else {
			try {
				loader = new FXMLLoader(new File("src/view/SaveView.fxml").toURI().toURL());
				Parent aRoot = loader.load();
				anchor.getChildren().add(aRoot);
				SaveController aC = loader.getController();
				aC.setInstance(aRoot, stage, scene);
			} catch (IOException e) {e.printStackTrace();}
		}
	}
}