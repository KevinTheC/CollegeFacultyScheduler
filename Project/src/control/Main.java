package control;
import java.math.*;
import java.util.Scanner;
import java.util.stream.Stream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Course;
import model.Instructor;
import model.Section;
import model.IO.SCCC.CourseParser;
import model.IO.SCCC.InstructorParser;
import model.IO.SCCC.SCCCImports;
import model.IO.SCCC.SectionParser;

import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.lang.*;
@SuppressWarnings("unused")
public class Main extends Application{
	public static void main(String[] args){
		Application.launch(args);
	}
	//export data
	//data persistence
	//Kevin Culkin
	public void start(Stage stage) throws Exception {
		SCCCImports.importCourses(new File("src/Q1/CourseInformation.csv"));
		SCCCImports.importSections(new File("src/Q1/CourseInformation.csv"));
		SCCCImports.importInstructors(new File("src/Q1/Instructors.csv"));
		SCCCImports.importWeights(new File("src/Q1/Instructor_Recent_Courses.csv"));
		FXMLLoader loader = new FXMLLoader(new File("src/view/Main.fxml").toURI().toURL());
		Parent root = loader.load();
		Pane pane = new Pane();
		pane.getChildren().add(root);
		Scene scene = new Scene(pane,800,600);
		stage.setScene(scene);
		stage.setMaximized(true);
		Controller c = loader.getController();
		c.setInstance(root,stage,scene);
		stage.setTitle("Program");
		stage.show();
		stage.setOnCloseRequest(e->{
			save("src/Q1/Courses.dat",Course.CourseFactory.getStream());
			save("src/Q1/Section.dat",Section.SectionFactory.getStream());
			save("src/Q1/Instructor.dat",Instructor.InstructorFactory.getStream());
		});
	}
	public static <K> void save(String filename, Stream<K> stream) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(filename)))){
			stream.forEach((obj)->{try {
				oos.writeObject(obj);
			} catch (IOException e) {}});
		} catch (IOException e) {}
	}
}

