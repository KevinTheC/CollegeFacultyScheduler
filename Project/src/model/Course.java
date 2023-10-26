package model;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Stream;
import model.IO.GenericReader;

public class Course implements Externalizable{
	private static final long serialVersionUID = -264001232753187946L;
	public static final Course empty = new Course("Free","");
	public static class CourseFactory{
		private final static TreeMap<String,Course> courseID = new TreeMap<>();
		private final static TreeMap<String,Course> subjects = new TreeMap<>();
		/**
		 * 
		 * @param ID
		 * @param subject
		 * @return
		 */
		public static Course getInstance(String ID, String subject){
			if (ID==null)
				ID = "";
			if (subject == null)
				subject = "";
			boolean sub = subjects.containsKey(subject)&&subject!="";
			boolean id = courseID.containsKey(ID)&&ID!="";
			if (sub&&id)
				return subjects.get(subject);
			else if (sub) {
				Course crs = subjects.get(subject);
				if (!ID.isEmpty()) {
					crs.courseID = ID;
					courseID.put(ID, crs);
				}
				return crs;
			} else if (id) {
				Course crs = courseID.get(ID);
				if (!subject.isEmpty()) {
					crs.courseSubject = subject;
					subjects.put(subject, crs);
				}
				return crs;
			}
			Course c = new Course(ID,subject);
			subjects.put(subject, c);
			courseID.put(ID, c);
			return c;
		}
		public static Course getInstance(GenericReader<Course> parser, String input) {
			List<String> inputs = new LinkedList<>();
			inputs.add(input);
			return CourseFactory.getInstance(parser, inputs);
		}
		public static Course getInstance(GenericReader<Course> parser, List<String> input) {
			Course crs = new Course(parser,input);
			return getInstance(crs.courseID,crs.courseSubject);
		}
		public static Course getInstance(Course crs) {
			return getInstance(crs.courseID, crs.courseSubject);
		}
		public static Stream<Course> getStream(){
			List<Course> list = new LinkedList<>();
			courseID.entrySet().forEach((i)->{list.add(i.getValue());});
			return list.stream();
		}
	}
	private String courseID;
	private String courseSubject;
	private Course(String ID, String sub) {
		courseID = ID;
		courseSubject = sub;
	}
	private Course(GenericReader<Course> parser, List<String> input) {
		parser.apply(this, input);
	}
	public String getCourseID() {
		return courseID;
	}
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	public String getCourseSubject() {
		return courseSubject;
	}
	public void setCourseSubject(String courseSubject) {
		this.courseSubject = courseSubject;
	}
	public String toString() {
		return courseID+","+courseSubject;
	}
	public boolean equals(Object o) {
		if (o==this)
			return true;
		if (!(o instanceof Course))
			return false;
		Course crs = (Course)o;
		return crs.courseID.equals(courseID)&&crs.courseSubject.equals(courseSubject);
	}
	public int hashCode() {
		return courseID.hashCode() * 31 + courseSubject.hashCode();
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeUTF(courseSubject);
		out.writeUTF(courseID);
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		courseSubject = in.readUTF();
		courseID = in.readUTF();
	}
}