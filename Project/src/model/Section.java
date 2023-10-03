package model;

import java.util.ArrayList;

import chrono.TimeStamp;
import model.Campus;

public class Section {
	private String name;
	private int Crn;
	private Course shell;
	private Part partOfTerm;
	private Campus campus;
	private boolean inPerson;
	private ArrayList<Day> days;
	private TimeStamp begin;
	private TimeStamp end;
	public Section(String name) {
		this.name = name;
	}
	public String toString() {
		return name;
	}
	public int getCrn() {
		return Crn;
	}
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Section))
			return false;
		return name.equals(((Section)o).name);
	}
	public int hashCode() {
		return name.hashCode()*31;
	}
}
