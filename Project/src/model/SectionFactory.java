package model;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class SectionFactory implements Factory<Section>{
	private final HashMap<Integer,Section> hs = new HashMap<>();
	@Override
	public Section getInstance(Parser<Section> parser, List<String> strings) {
		Section course = new Section(null);
		parser.apply(course,strings);
		if (hs.containsKey(course.getCrn()))
			return hs.get(course.getCrn());
		return course;
	}
	public Optional<Section> getInstance(int Crn) {
		if (hs.containsKey(Crn))
			return Optional.of(hs.get(Crn));
		return Optional.empty();
	}
}
