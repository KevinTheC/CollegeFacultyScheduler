package model;
import java.io.Serializable;
import java.util.TreeMap;

import model.chrono.TimeSchedule;
import model.chrono.TimeStamp;

public class Availability<K> implements Serializable{
	private static final long serialVersionUID = 2364232955575274264L;
	private TreeMap<Day,TimeSchedule<K>> map;
	public Availability() {
		map = new TreeMap<>();
		Day[] days = {Day.M,Day.T,Day.W,Day.R,Day.F,Day.S,Day.U};
		for (Day d : days)
			map.put(d, new TimeSchedule<K>());
	}
	public void put(K k,TimeStamp begin, TimeStamp end, Day...days) {
		for (Day d : days) {
			map.get(d).add(k,begin,end);
		}
	}
	public void remove(K k, TimeStamp begin, TimeStamp end, Day...days) {
		for (Day d : days) {
			map.get(d).remove(k,begin,end);
		}
	}
	public TimeSchedule<K> get(Day d) {
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
	public TimeSchedule<K> getSchedule(Day d){
		return map.get(d);
	}
}
