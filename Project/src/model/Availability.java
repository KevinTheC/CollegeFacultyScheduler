package model;
import java.util.TreeMap;

import chrono.TimeSchedule;
import chrono.TimeStamp;

public class Availability {
	private TreeMap<Day,TimeSchedule<Course>> map;
	public Availability() {
		map = new TreeMap<>();
		Day[] days = {Day.M,Day.T,Day.W,Day.R,Day.F,Day.S,Day.U};
		for (Day d : days)
			map.put(d, new TimeSchedule<Course>());
	}
	public void put(Course crs,TimeStamp begin, TimeStamp end, Day...days) {
		for (Day d : days) {
			map.get(d).add(crs,begin,end);
		}
	}
	public void remove(Course crs, TimeStamp begin, TimeStamp end, Day...days) {
		for (Day d : days) {
			map.get(d).remove(crs,begin,end);
		}
	}
	public TimeSchedule<Course> get(Day d) {
		return map.get(d);
	}
	public boolean available(TimeStamp begin, TimeStamp end, Day d) {
		return map.get(d).validate(begin, end);
	}
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Availability))
			return false;
		Availability avl = (Availability)o;
		for (Day d : map.keySet())
			if (!avl.map.get(d).equals(map.get(d)))
				return false;
		return true;
	}
	public String toString() {
		return map.toString();
	}
}
