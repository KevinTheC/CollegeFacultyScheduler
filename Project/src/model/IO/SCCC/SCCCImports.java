package model.IO.SCCC;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import model.Course;
import model.Instructor;
import model.Section;
import model.IO.SVReader;

public class SCCCImports {
	private SCCCImports() {}
	public static void importInstructors(File f1){
		InstructorParser parser = new InstructorParser(',');
		try (BufferedReader in = new BufferedReader(new FileReader(f1))) {
			while (in.ready()) {
				List<String> strings = new ArrayList<>();
				while (in.ready()) {
					String str = in.readLine();
					if (str.length()==0||str.charAt(0)=='-')
						break;
					strings.add(str);
				}
				if (strings.get(0).replaceAll(",", "").length()!=0)
					Instructor.InstructorFactory.getInstance(parser,strings);
			}
		} catch (IOException e) {}
	}
	public static void importCourses(File f1) {
		CourseParser parser = new CourseParser(',');
		try (BufferedReader in = new BufferedReader(new FileReader(f1))){
			in.readLine();
			while (in.ready()) {
				Course.CourseFactory.getInstance(parser,in.readLine());
			}
		} catch (IOException e) {}
	}
	public static void importSections(File f1){
		List<Section> result = new LinkedList<>();
		SectionParser parser = new SectionParser(',');
		try (BufferedReader in = new BufferedReader(new FileReader(f1))){
			in.readLine();
			
			while (in.ready()) {
				Section section = Section.SectionFactory.getInstance(parser,in.readLine());
				result.add(section);
				}
		} catch (IOException e) {}
	}
	public static void importWeights(File f1){
		List<Instructor> result = new LinkedList<>();
		CourseWeightParser parser = new CourseWeightParser(',');
		try (BufferedReader in = new BufferedReader(new FileReader(f1))){
			in.readLine();
			while (in.ready()) {
				String str = in.readLine();
				String[] arr = str.split(",");
				Instructor inst = Instructor.InstructorFactory.getInstance(Integer.parseInt(arr[0]));
				parser.apply(inst, str);
				result.add(inst);
				}
		} catch (IOException e) {}
	}
	public static <K> void save(String filename, Stream<K> stream) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(filename)))){
			stream.forEach((obj)->{try {
				oos.writeObject(obj);
			} catch (IOException e) {}});
		} catch (IOException e) {}
	}
	public static void save() {
		save("src/data/Courses.dat",Course.CourseFactory.getStream());
		save("src/data/Section.dat",Section.SectionFactory.getStream());
		save("src/data/Instructor.dat",Instructor.InstructorFactory.getStream());
	}
	public static <K> void load(String filename) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(filename)))){
			while (true)
				ois.readObject();
		} catch (IOException e) {} catch (ClassNotFoundException e) {}
	}
	public static void load() {
		if (!new File("src/data/Courses.dat").exists()) {
			SCCCImports.importCourses(new File("src/data/CourseInformation.csv"));
			SCCCImports.importSections(new File("src/data/CourseInformation.csv"));
			SCCCImports.importInstructors(new File("src/data/InstructorsV2.csv"));
			SCCCImports.importWeights(new File("src/data/InstructorFrequencyV3.csv"));
			return;
		}
		load("src/data/Courses.dat");
		load("src/data/Section.dat");
		load("src/data/Instructor.dat");
	}
}
