package model.IO.SCCC;

import model.Course;
import model.Day;
import model.Instructor;
import model.Section;
import model.IO.SVReader;
import model.Campus;
import model.Instructor.Rank;
import model.chrono.TimeRange;
import model.chrono.TimeStamp;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class InstructorParser extends SVReader<Instructor>{
	public InstructorParser(char seperator) {
		//implements logic for given spreadsheet
		super(seperator);
		bind(0,0,(str, ints)->{ints.setID(Integer.parseInt(str));});
		bind(0,1,(str, ints)->{ints.setHomeCampus(Campus.parse(str.charAt(0)));});
		bind(0,2,(str, ints)->{ints.setCell(str);});
		bind(1,0,(str, ints)->{ints.setName(str);});
		bind(1,1,(str, ints)->{ints.setAddress(str);});
		bind(1,2,(str, ints)->{ints.setAddress(ints.getAddress()+" "+str);});
		bind(2,0,(str,ints)->{ints.setHomePhone(str);});
		bind(2,1,(str,ints)->{ints.setHireDate(str);});
		//changed
		for (int i=2;i<=10;i++)
			bind(i,new int[] {2,3},(str,ints)->{
				if (str.contains("L")) {
					Course crs = Course.CourseFactory.getInstance(str.trim().substring(0,str.trim().length()-1),null);
					if (!ints.getCourses().contains(crs)&&!str.isEmpty())
						ints.getCourses().add(crs);
				}
				else {
					Course crs = Course.CourseFactory.getInstance(str.trim(),null);
					if (!ints.getCourses().contains(crs)&&!str.isEmpty())
						ints.getCourses().add(crs);
				}
			});
		bind(3,0,(str,ints)->{ints.setRank(Rank.parse(str));});
		bind(4,0,(str,ints)->{ints.setOnline(str.equals("Y"));});
		bind(5,0,(str,ints)->{if (str.isEmpty()) return; 
		for (String string : str.split("[ ]")) {
			Campus c = Campus.parse(string.charAt(0));
			if (!ints.getPreferences().contains(c))
				ints.getPreferences().add(c);
		}});
		bind(6,new int[] {0,1},(str,ints)->{if (ints.getCourseCount()==0) ints.setCourseCount(1); if (str.equals("Y")) ints.setCourseCount(ints.getCourseCount()+1);});
		//for reusability
		BiFunction<TimeRange<Course>,TimeRange<Course>,BiConsumer<String,Instructor>> avlParserGenerator = (tNormal,tAsterisk)->{return (str, ints)->{
			TimeRange<Course> t = null;
			if (str.length()==0)
				return;
			if (str.charAt(0)=='*')
				t=tAsterisk;
			else
				t=tNormal;
			boolean tu = true;
			for (int i=0;i<str.length();i++) {
				if (str.charAt(i)==' '||str.charAt(i)=='*')
					continue;
				else if (str.charAt(i)=='S') {
					ints.getAvailability().put(Section.empty,t.getBegin(),t.getEnd(),Day.parse(str.substring(i,i+3)));
					i+=2;
				} else if (str.charAt(i)=='T'){
					if (tu) {
						ints.getAvailability().put(Section.empty,t.getBegin(),t.getEnd(), Day.T);
						tu = !tu;
					} else
						ints.getAvailability().put(Section.empty,t.getBegin(),t.getEnd(), Day.R);
				} else {
					ints.getAvailability().put(Section.empty,t.getBegin(),t.getEnd(),Day.parse(str.charAt(i)+""));
				}
			}
		};};
		
		bind(8,new int[] {0,1},avlParserGenerator.apply(
				new TimeRange<Course>(Course.empty,new TimeStamp(8,0),new TimeStamp(12,0)),
				new TimeRange<Course>(Course.empty,new TimeStamp(7,0),new TimeStamp(8,0))));
		bind(9,new int[] {0,1},avlParserGenerator.apply(
				new TimeRange<Course>(Course.empty,new TimeStamp(12,0),new TimeStamp(15,0)),
				new TimeRange<Course>(Course.empty,new TimeStamp(15,0),new TimeStamp(16,0))));
		bind(10,new int[] {0,1},(str,ints)->{
			if (str.contains("Sat"))
				ints.getAvailability().put(Section.empty,new TimeStamp(8,0),new TimeStamp(15,0), Day.S);
			else if (str.contains("Sun"))
				ints.getAvailability().put(Section.empty,new TimeStamp(8,0),new TimeStamp(15,0), Day.U);
		});
		bind(11,new int[] {0,1},avlParserGenerator.apply(new TimeRange<Course>(Course.empty,new TimeStamp(16,0),new TimeStamp(18,0)), null));
		bind(12,new int[] {0,1},avlParserGenerator.apply(new TimeRange<Course>(Course.empty,new TimeStamp(18,0),new TimeStamp(22,0)), null));
	}
}
