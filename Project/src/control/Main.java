package control;
import java.math.*;
import java.util.Scanner;

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
import parserSCCC.CourseParser;
import parserSCCC.SCCCImports;
import parserSCCC.InstructorParser;
import parserSCCC.SectionParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.lang.*;
@SuppressWarnings("unused")
public class Main extends Application{
	public static void main(String[] args){
		Application.launch(args);
	}
	public void start(Stage stage) throws Exception {
		Collection<Instructor> instructors = SCCCImports.importInstructors(new File("src/Q1/Instructors.csv"));
		SCCCImports.importCourses(new File("src/Q1/CourseInformation.csv"));
		Collection<Section> sections = SCCCImports.importSections(new File("src/Q1/CourseInformation.csv"));
		instructors.addAll(SCCCImports.importWeights(new File("src/Q1/Instructor_Recent_Courses.csv")));
		File f1 = new File("src/view/Main.fxml");
		FXMLLoader loader = new FXMLLoader(f1.toURI().toURL());
		Parent root = loader.load();
		Scene scene = new Scene(root,820,670);
		stage.setScene(scene);
		//scene.getStylesheets().add("view/application.css");
		Controller c = loader.getController();
		c.setInstance(root,stage,scene,instructors,sections);
		stage.setTitle("Program");
		stage.show();
	}
}

