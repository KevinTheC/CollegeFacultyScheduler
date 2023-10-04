package parserSCCC;

import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Function;

import model.Course;
import model.Instructor;
import model.Parser;

public class CourseWeightParser extends Parser<Instructor>{
	public CourseWeightParser(char seperator) {
		super(seperator);
		Function<Integer,BiConsumer<String,Instructor>> func = (num)->{
			return (string,instructor)->{
				TreeMap<Course,Integer> tm = instructor.getWeights();
				string.replaceAll(" ", "");
				String[] arr = string.split(",");
				for (String string2 : arr) {
					Course crs = Course.CourseFactory.getInstance(string2, null);
					if (!tm.containsKey(crs))
						tm.put(crs, num);
					else
						tm.put(crs, num+tm.get(crs));
				}
			};
		};
		for (int i=2;i<9;i++)
			bind(i,func.apply(9-i));
	}
}
