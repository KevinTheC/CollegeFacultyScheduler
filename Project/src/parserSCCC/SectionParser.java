package parserSCCC;

import chrono.TimeStamp;
import model.Campus;
import model.Course;
import model.Day;
import model.Parser;
import model.Part;
import model.Section;

public class SectionParser extends Parser<Section>{
	public SectionParser(char seperator) {
		super(seperator);
		bind(0,(string, section)->{});
		bind(3,(string, section)->{String str = null; section.setCourse(Course.CourseFactory.getInstance(str, string));});
		bind(4,(string, section)->{section.setCrn(Integer.parseInt(string));});
		bind(6,(string, section)->{section.setPartOfTerm(Part.parse(string));});
		bind(7,(string, section)->{section.setCampus(Campus.parse(string.charAt(0)));});
		bind(9,(string, section)->{
			if (string.equals("TR"))
				section.setInPerson(true);
			else
				section.setInPerson(false);
			});
		bind(18,(string, section)->{
			for (char c : string.toCharArray())
				section.getDays().add(Day.parse(c+""));
		});
		bind(19,(string, section)->{
			if (!string.isBlank())
				section.setBegin(TimeStamp.parseTimeStamp(string));
		});
		bind(20,(string, section)->{
			if (!string.isBlank())
				section.setEnd(TimeStamp.parseTimeStamp(string));
		});
	}

}
