package chrono;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Optional;


public class TimeSchedule <K>{
	private LinkedList<Triple<K,Boolean,TimeStamp>> range;
	public TimeSchedule() {
		range = new LinkedList<>();
		range.add(new Triple<K,Boolean,TimeStamp>(null,true,new TimeStamp(0,0)));
		range.add(new Triple<K,Boolean,TimeStamp>(null,false,new TimeStamp(23,59)));
	}
	public boolean add(K type, TimeStamp begin, TimeStamp end) {
		Optional<ListIterator<Triple<K,Boolean,TimeStamp>>> o = validate(type,begin,end);
		if (o.isEmpty()) return false;
		var itr = o.get();
		shiftTo(itr,itr.nextIndex()+1);
		itr.add(new Triple<K,Boolean,TimeStamp>(null,false,begin));
		itr.add(new Triple<K,Boolean,TimeStamp>(type,true,begin));
		itr.add(new Triple<K,Boolean,TimeStamp>(type,false,end));
		itr.add(new Triple<K,Boolean,TimeStamp>(null,true,end));
		cleanse();
		return true;
	}
	private void cleanse() {
		var itr = range.listIterator();
		Triple<K,Boolean,TimeStamp> prev = itr.next();
		Triple<K,Boolean,TimeStamp> curr;
		while (itr.hasNext()) {
			curr = itr.next();
			if (Objects.equals(prev.k, curr.k)&&Objects.equals(prev.t, curr.t)) {
				itr.remove();
				itr.previous();
				itr.remove();
			}
			prev = curr;
		}
	}
	private void shiftTo(ListIterator<Triple<K,Boolean,TimeStamp>> itr,int index) {
		while (itr.nextIndex()<index) {
			itr.next();
		}
		while (itr.nextIndex()>index) {
			itr.previous();
		}
	}
	private Triple<K,Boolean,TimeStamp> current(ListIterator<Triple<K,Boolean,TimeStamp>> itr) {
		Triple<K,Boolean,TimeStamp> val= itr.next();
		itr.previous();
		return val;
	}
	public boolean remove(K type, TimeStamp begin, TimeStamp end) {
		if (!(range.contains(new Triple<K,Boolean,TimeStamp>(type,true,begin))&&range.contains(new Triple<K,Boolean,TimeStamp>(type,false,end))))
			return false;
		var itr = range.listIterator();
		while (itr.hasNext()) {
			Triple<K,Boolean,TimeStamp> tri = itr.next();
			if (Objects.equals(tri.k,type)&&Objects.equals(tri.t,begin)) {
				tri.k = null;
				itr.previous();
				break;
			}
		}
		while (itr.hasNext()) {
			Triple<K,Boolean,TimeStamp> tri = itr.next();
			if (Objects.equals(tri.k,type)&&Objects.equals(tri.t,end)) {
				tri.k = null;
				break;
			}
		}
		cleanse();
		return true;
	}
	/**
	 * 
	 * @param begintime
	 * @param endtime
	 * @return null if the period can't be placed without interrupts
	 */
	private Optional<ListIterator<Triple<K,Boolean,TimeStamp>>> validate(K type,TimeStamp begin,TimeStamp end) {
		ListIterator<Triple<K,Boolean,TimeStamp>> itr = range.listIterator();
		Triple<K,Boolean,TimeStamp> pair = current(itr);
		while (pair.t.compareTo(begin)<=0) {
			pair = itr.next();
		}
		itr.previous();
		if (pair.t.compareTo(end)<0)
			return Optional.empty();
		itr.previous();
		return Optional.of(itr);
	}
	public List<TimeRange<K>> toRanges(){
		List<TimeRange<K>> list = new LinkedList<TimeRange<K>>();
		var itr = range.iterator();
		while (itr.hasNext()) {
			var temp = itr.next();
			K k = temp.k;
			TimeStamp t = temp.t;
			list.add(new TimeRange<K>(k,t,itr.next().t));
		}
		return list;
	}
	public String toString() {
		return toRanges().toString();
	}
	public boolean validate(TimeStamp begin, TimeStamp end) {
		return !validate(null,begin,end).isEmpty();
	}
}
