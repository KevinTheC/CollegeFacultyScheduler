package model.chrono;

import java.io.Serializable;

public class TimeRange<K> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4553750932791353714L;
	private TimeStamp begin;
	private TimeStamp end;
	private K type;
	public TimeRange(K type, TimeStamp begin, TimeStamp end) {
		this.type = type;
		this.end = end;
		this.begin = begin;
	}
	public TimeStamp getEnd() {
		return end;
	}
	public void setEnd(TimeStamp end) {
		this.end = end;
	}
	public TimeStamp getBegin() {
		return begin;
	}
	public void setBegin(TimeStamp begin) {
		this.begin = begin;
	}
	public K getType() {
		return type;
	}
	public static <K> void merge(TimeRange<K> t1, TimeRange<K> t2) {
		t1.setEnd(t2.getEnd());
	}
	public String toString() {
		return "["+((type==null) ? type : type.toString())+": "+begin+" -> "+end+"]";
	}
}
