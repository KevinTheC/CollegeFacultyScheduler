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
	//Kevin Culkin
	public void start(Stage stage) throws Exception {
		SCCCImports.load();
		FXMLLoader loader = new FXMLLoader(new File("src/view/Main.fxml").toURI().toURL());
		Parent root = loader.load();
		Scene scene = new Scene(root,800,600);
		stage.setScene(scene);
		stage.setMaximized(true);
		Controller c = loader.getController();
		c.setInstance(root,stage,scene);
		stage.setTitle("Scheduler");
		stage.show();
		stage.setOnCloseRequest(e->{
			SCCCImports.save();
		});
	}
}

