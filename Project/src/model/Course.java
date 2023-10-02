package model;

public class Course {
	private String name;
	private int Crn;
	public Course(String name) {
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
		if (!(o instanceof Course))
			return false;
		return name.equals(((Course)o).name);
	}
	public int hashCode() {
		return name.hashCode()*31;
	}
}
