package model;

import java.util.TreeMap;

public class Course {
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
		public static Course getInstance(Parser<Course> parser, String input) {
			Course crs = new Course(parser,input);
			return getInstance(crs.courseID,crs.courseSubject);
		}
	}
	private String courseID;
	private String courseSubject;
	private Course(String ID, String sub) {
		courseID = ID;
		courseSubject = sub;
	}
	private Course(Parser<Course> parser, String input) {
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
}
