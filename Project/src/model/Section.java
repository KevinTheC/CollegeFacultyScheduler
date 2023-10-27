package model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import model.IO.GenericReader;
import model.chrono.TimeStamp;

public class Section implements Externalizable{
	public static final Section empty = new Section();
	public static class SectionFactory{
		private final static HashMap<Integer,Section> set = new HashMap<>();
		public static Section getInstance(GenericReader<Section> parser, List<String> strings) {
			Section ints = new Section(parser,strings);
			if (set.containsKey(ints.crn))
				return set.get(ints.crn);
			set.put(ints.crn,ints);
			return ints;
		}
		public static Section getInstance(GenericReader<Section> parser, String string) {
			ArrayList<String> strings = new ArrayList<>();
			strings.add(string);
			return getInstance(parser,strings);
		}
		public static Section getInstance(Section s) {
			return set.get(s.crn);
		}
		public static Stream<Section> getStream(){
			List<Section> list = new LinkedList<>();
			set.entrySet().forEach((i)->{list.add(i.getValue());});
			return list.stream();
		}
	}
	private int crn;
	private Course shell;
	private Part partOfTerm;
	private Campus campus;
	private boolean inPerson;
	private List<Day> days;
	private TimeStamp begin;
	private TimeStamp end;
	public Section() {
		crn = -1;
		shell = Course.empty;
	}
	private Section(GenericReader<Section> parser, List<String> string) {
		days = new LinkedList<>();
		parser.apply(this, string);
	}
	public int getCrn() {
		return crn;
	}
	public void setCrn(int crn) {
		this.crn = crn;
	}
	public Course getCourse() {
		return shell;
	}
	public void setCourse(Course course) {
		this.shell = course;
	}
	public Part getPartOfTerm() {
		return partOfTerm;
	}
	public void setPartOfTerm(Part part) {
		this.partOfTerm = part;
	}
	public Campus getCampus() {
		return campus;
	}
	public void setCampus(Campus campus) {
		this.campus = campus;
	}
	public boolean getInPerson() {
		return inPerson;
	}
	public void setInPerson(boolean inPerson) {
		this.inPerson = inPerson;
	}
	public List<Day> getDays() {
		return days;
	}
	public TimeStamp getBegin() {
		return begin;
	}
	public void setBegin(TimeStamp begin) {
		this.begin = begin;
	}
	public TimeStamp getEnd() {
		return end;
	}
	public void setEnd(TimeStamp end) {
		this.end = end;
	}
	public String toString() {
		return crn+","+shell+','+partOfTerm+','+campus+','+inPerson+','+days+','+begin+','+end;
	}
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Section))
			return false;
		return crn == ((Section)o).crn;
	}
	public int hashCode() {
		return crn * 31 + shell.hashCode();
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(crn);
		out.writeObject(shell);
		out.writeObject(partOfTerm);
		out.writeObject(campus);
		out.writeBoolean(inPerson);
		out.writeObject(days);
		out.writeObject(begin);
		out.writeObject(end);
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		crn = in.readInt();
		shell = (Course)in.readObject();
		partOfTerm = (Part)in.readObject();
		campus = (Campus)in.readObject();
		inPerson = in.readBoolean();
		days = (List<Day>)in.readObject();
		begin = (TimeStamp)in.readObject();
		end = (TimeStamp)in.readObject();
	}
}