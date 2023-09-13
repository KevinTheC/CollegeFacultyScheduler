package Q1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class Availability {
	private TreeMap<Day,ArrayList<Time>> map;
	public Availability() {
		map = new TreeMap<>();
		Day[] days = {Day.M,Day.T,Day.W,Day.R,Day.F,Day.S,Day.U};
		for (Day d : days)
			map.put(d, new ArrayList<Time>());
	}
	public void put(Time t, Day...days) {
		for (Day d : days)
			if (!map.get(d).contains(t))
				map.get(d).add(t);
	}
	public void remove(Time t, Day...days) {
		for (Day d : days)
			if (!map.get(d).contains(t))
				map.get(d).remove(t);
	}
	public boolean available(Time t, Day d) {
		return map.get(d).contains(t);
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
