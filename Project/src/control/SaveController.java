package control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Course;
import model.Instructor;
import model.Section;
import model.IO.SCCC.InstructorWriter;

public class SaveController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	public void setInstance(Parent root,Stage stage, Scene scene) {
		this.root = root;
		this.scene = scene;
		this.stage = stage;
	}
	public void reset(ActionEvent e) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText("Confirm mark for reset");
		alert.setContentText("Once OK is clicked, the data stored will be lost and the program will close.");
		Optional<ButtonType> optional = alert.showAndWait();
		if (optional.isEmpty())
			return;
		else if (optional.get()==ButtonType.OK) {
			stage.setOnCloseRequest(null);
			File f = new File("src/data/Courses.dat");
			f.delete();
			f = new File("src/data/Section.dat");
			f.delete();
			f = new File("src/data/Instructor.dat");
			f.delete();
			stage.close();
		}
	}
	public void export(ActionEvent e) {
		FileChooser fc = new FileChooser();
		fc.setSelectedExtensionFilter(new ExtensionFilter("Comma Seperated Values",".csv"));
		File f = fc.showSaveDialog(stage);
		if (f!=null) {
			try (FileWriter fw = new FileWriter(f)){
				Instructor.InstructorFactory.getStream().forEach((inst)->{try {
					InstructorWriter.write(inst,fw);
					fw.write("--------\n");
				} catch (IOException e1) {e1.printStackTrace();
				}});
				
			} catch (IOException e1) {e1.printStackTrace();}
		}
			
	}
}
