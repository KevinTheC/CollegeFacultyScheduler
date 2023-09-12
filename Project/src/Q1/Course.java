package Q1;

public class Course {
	private String name;
	public Course(String name){
		this.name = name;
	}
	public String toString() {
		return name;
	}
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Course))
			return false;
		return name.equals(((Course)o).name);
	}
}
