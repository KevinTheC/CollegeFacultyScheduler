package Q1;

import model.Course;
import model.Parser;

public class CourseParser extends Parser<Course> {
	public CourseParser(char seperator) {
		super(seperator);
		bind(0,(str,crs)->{
			
		});
		bind(1,(str,crs)->{});
		bind(2,(str,crs)->{});
		bind(3,(str,crs)->{});
		bind(4,(str,crs)->{});
		bind(5,(str,crs)->{});
		bind(6,(str,crs)->{});
	}

}
