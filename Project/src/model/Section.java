package model;

import java.util.ArrayList;
import java.util.List;

import chrono.TimeStamp;
import model.Campus;

public class Section {
	public static final Section empty = new Section();
	private int crn;
	private Course shell;
	private Part partOfTerm;
	private Campus campus;
	private boolean inPerson;
	private List<Day> days;
	private TimeStamp begin;
	private TimeStamp end;
	private Section() {
		crn = -1;
		shell = Course.empty;
	}
	public Section(Parser<Section> parser, String string) {
		days = new ArrayList<>();
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
}
