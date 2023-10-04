package parserSCCC;

import model.Course;
import model.Parser;

public class CourseParser extends Parser<Course> {
	public CourseParser(char seperator) {
		super(seperator);
		bind(3,(str,course)->{course.setCourseSubject(str);});
		bind(2,(str,course)->{
			str = "00".substring(0,3-str.length())+str;
			if (course.getCourseID()!=null)
				course.setCourseID(course.getCourseID() + str);
			else
				course.setCourseID(str);
			});
		bind (1,(str,course)->{
			if (course.getCourseID()!=null)
				course.setCourseID(str+course.getCourseID());
			else
				course.setCourseID(str);
		});
	}

}
