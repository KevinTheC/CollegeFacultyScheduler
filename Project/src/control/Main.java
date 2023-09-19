package control;
import java.math.*;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Instructor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.*;
import java.lang.*;
@SuppressWarnings("unused")
public class Main extends Application{
	public static void main(String[] args){
		Application.launch(args);
	}
	public void start(Stage stage) throws Exception {
		Collection<Instructor> instructors = importInstructors(new File("src/Q1/Instructors.csv"));
		File f1 = new File("bin/view/Main.fxml");
		FXMLLoader loader = new FXMLLoader(f1.toURI().toURL());
		Parent root = loader.load();
		Scene scene = new Scene(root,820,500);
		stage.setScene(scene);
		scene.getStylesheets().add("view/application.css");
		Controller c = loader.getController();
		c.setInstance(root,stage,scene,instructors);
		stage.setTitle("Program");
		stage.show();
	}
	public static Collection<Instructor> importInstructors(File f1) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(f1));
		while (in.readLine().charAt(0)!='—') {}
		ArrayList<Instructor> ar = new ArrayList<>();
		while (in.ready()) {
			ArrayList<String> strings = new ArrayList<>();
			while (in.ready()) {
				String str = in.readLine();
				if (str.length()==0||str.charAt(0)=='—')
					break;
				strings.add(str);
			}
			ar.add(new Instructor(strings,','));
		}
		in.close();
		return ar;
	}
}
