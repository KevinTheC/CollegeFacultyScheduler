package chrono;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Optional;


public class TimeSchedule <K>{
	private class Tri{
		public Tri(K type, boolean b,TimeStamp t) {
			this.type = type;
			this.b = b;
			this.t = t;
		}
		public K type;
		public boolean b;
		public TimeStamp t;
		public String toString() {
			return "["+type+","+b+","+t+"]";
		}
	}
	private LinkedList<Tri> range;
	public TimeSchedule() {
		range = new LinkedList<>();
		range.add(new Tri(null,true,new TimeStamp(0,0)));
		range.add(new Tri(null,false,new TimeStamp(23,59)));
	}
	public boolean add(K type, TimeStamp begin, TimeStamp end) {
		Optional<ListIterator<TimeSchedule<K>.Tri>> o = validate(type,begin,end);
		if (o.isEmpty()) return false;
		var itr = o.get();
		itr.next();
		itr.add(new Tri(null,false,begin));
		itr.add(new Tri(type,true,begin));
		itr.add(new Tri(type,false,end));
		itr.add(new Tri(null,true,end));
		return true;
	}
	public void forceAdd(K type, TimeStamp begin, TimeStamp end) {
		//TODO system to remove
		Optional<ListIterator<TimeSchedule<K>.Tri>> o = validate(type,begin,begin);
		var itr = o.get();
		Tri pair = itr.previous();
		itr.add(new Tri(null,false,begin));
		itr.add(new Tri(type,true,begin));
		itr.next();
		while (pair.t.compareTo(end)<0) {
			itr.remove();
			pair = itr.next();	
		}
		itr.previous();
		itr.add(new Tri(type,false,end));
		itr.add(new Tri(null,true,end));
	}
	public boolean remove() {
		return true;
	}
	/**
	 * 
	 * @param begintime
	 * @param endtime
	 * @return null if the period can't be placed without interrupts
	 */
	private Optional<ListIterator<TimeSchedule<K>.Tri>> validate(K type,TimeStamp begin,TimeStamp end) {
		ListIterator<TimeSchedule<K>.Tri> itr = range.listIterator();
		Tri pair = itr.next();
		itr.previous();
		while (pair.t.compareTo(begin)<0) {
			pair = itr.next();
		}
		itr.previous();
		if (pair.t.compareTo(end)<0)
			return Optional.empty();
		itr.previous();
		return Optional.of(itr);
	}
	public String toString() {
		return range.toString();
	}
}
