package parserSCCC;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import model.Course;
import model.Instructor;
import model.Parser;
import model.Section;

public class SCCCImports {
	public static Collection<Instructor> importInstructors(File f1){
		List<Instructor> result = new LinkedList<>();
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
			Instructor instructor = Instructor.InstructorFactory.getInstance(parser,strings);
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
				Course.CourseFactory.getInstance(parser,in.readLine());
			}
		} catch (IOException e) {}
	}
	public static Collection<Section> importSections(File f1){
		List<Section> result = new LinkedList<>();
		SectionParser parser = new SectionParser(',');
		try (BufferedReader in = new BufferedReader(new FileReader(f1))){
			in.readLine();
			
			while (in.ready()) {
				Section section = new Section(parser,in.readLine());
				result.add(section);
				}
		} catch (IOException e) {}
		return result;
	}
	public static Collection<Instructor> importWeights(File f1){
		List<Instructor> result = new LinkedList<>();
		CourseWeightParser parser = new CourseWeightParser(',');
		try (BufferedReader in = new BufferedReader(new FileReader(f1))){
			in.readLine();
			while (in.ready()) {
				String str = in.readLine();
				String[] arr = str.split(",");
				Instructor inst = Instructor.InstructorFactory.getInstance(arr[0]+", "+arr[1]);
				parser.apply(inst, str);
				result.add(inst);
				}
		} catch (IOException e) {}
		return result;
	}
}
