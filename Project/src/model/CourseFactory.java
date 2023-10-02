package model;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class CourseFactory implements Factory<Course>{
	private final HashMap<Integer,Course> hs = new HashMap<>();
	@Override
	public Course getInstance(Parser<Course> parser, List<String> strings) {
		Course course = new Course(null);
		parser.apply(course,strings);
		if (hs.containsKey(course.getCrn()))
			return hs.get(course.getCrn());
		return course;
	}
	public Optional<Course> getInstance(int Crn) {
		if (hs.containsKey(Crn))
			return Optional.of(hs.get(Crn));
		return Optional.empty();
	}
}
