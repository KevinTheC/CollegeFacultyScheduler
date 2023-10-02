package Q1;

import model.Course;
import model.Day;
import model.Instructor;
import model.Parser;
import model.Instructor.Campus;
import model.Instructor.Rank;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import chrono.Time;

public class InstructorParser extends Parser<Instructor>{
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
		bind(2,new int[] {2,3},(str,ints)->{
			for (String string : str.split("[ ]"))
				if (string.length()>1)
					ints.getCourses().add(new Course(string));});
		bind(3,0,(str,ints)->{ints.setRank(Rank.parse(str));});
		bind(4,0,(str,ints)->{ints.setOnline(str.equals("Y"));});
		bind(5,0,(str,ints)->{if (str.equals("")) return; 
		for (String string : str.split("[ ]")) {
			Campus c = Campus.parse(string.charAt(0));
			if (!ints.getPreferences().contains(c))
				ints.getPreferences().add(c);
		}});
		bind(6,new int[] {0,1},(str,ints)->{if (str.equals("Y")) ints.setCourseCount(ints.getCourseCount()+1);});
		
		//for reusability
		BiFunction<Time,Time,BiConsumer<String,Instructor>> avlParserGenerator = (tNormal,tAsterisk)->{return (str, ints)->{
			Time t = null;
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
					ints.getAvailability().put(t, Day.parse(str.substring(i,i+3)));
					i+=2;
				} else if (str.charAt(i)=='T'){
					if (tu) {
						ints.getAvailability().put(t, Day.T);
						tu = !tu;
					} else
						ints.getAvailability().put(t, Day.R);
				} else {
					ints.getAvailability().put(t,Day.parse(str.charAt(i)+""));
				}
			}
		};};
		
		bind(8,avlParserGenerator.apply(Time.Morning, Time.EarlyMorning));
		bind(9,avlParserGenerator.apply(Time.Afternoon, Time.LateAfternoon));
		bind(10,avlParserGenerator.apply(Time.Open, null));
		bind(11,avlParserGenerator.apply(Time.LaterAfternoon, null));
		bind(12,avlParserGenerator.apply(Time.Evening, null));
	}
	
	//weight taught recently, match skill level through weighting
	//seniors get full schedule, then juniors get scraps
	//student id barcode
}
