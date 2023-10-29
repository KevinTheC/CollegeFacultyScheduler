package Q1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;

import model.Course;
import model.MyObjectInputStream;
import model.IO.SCCC.SCCCImports;

public class TestClass {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("src/Q1/Course.dat")))){
			ois.readObject();
		} catch (IOException e) {}
	}
}
