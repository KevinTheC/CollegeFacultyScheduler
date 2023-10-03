package control;
import java.math.*;
import java.util.Scanner;

import Q1.CourseParser;
import Q1.InstructorParser;
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
		Collection<Instructor> instructors = importInstructors(new File("src/Q1/Instructors.csv"));
		importCourses(new File("src/Q1/CourseInformation.csv"));
		File f1 = new File("src/view/Main.fxml");
		FXMLLoader loader = new FXMLLoader(f1.toURI().toURL());
		Parent root = loader.load();
		Scene scene = new Scene(root,820,670);
		stage.setScene(scene);
		//scene.getStylesheets().add("view/application.css");
		Controller c = loader.getController();
		c.setInstance(root,stage,scene,instructors);
		stage.setTitle("Program");
		stage.show();
	}
	public static Collection<Instructor> importInstructors(File f1){
		List<Instructor> result = new ArrayList<>();
		InstructorParser parser = new InstructorParser(',');
		try (BufferedReader in = new BufferedReader(new FileReader(f1))){
			while (in.readLine().charAt(0)!='—') {}
			
			while (in.ready()) {
				List<String> strings = new ArrayList<>();
				while (in.ready()) {
					String str = in.readLine();
					if (str.length()==0||str.charAt(0)=='—')
						break;
					strings.add(str);
				}
			Instructor instructor = new Instructor(parser,strings);
			result.add(instructor);
			}
		} catch (IOException e) {}
		return result;
	}
	public static void importCourses(File f1) {
		CourseParser parser = new CourseParser(',');
		try (BufferedReader in = new BufferedReader(new FileReader(f1))){
			in.readLine();
			while (in.ready()) {
				Course crs = Course.CourseFactory.getInstance(parser,in.readLine());
			}
		} catch (IOException e) {}
	}
}

