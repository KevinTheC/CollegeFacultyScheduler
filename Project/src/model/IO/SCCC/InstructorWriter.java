package model.IO.SCCC;

import java.io.FileWriter;
import java.io.IOException;

import model.Instructor;

public class InstructorWriter{
	public static void write(Instructor i, FileWriter f) throws IOException {
		f.write(i.getID()+", "+i.getName()+", "+i.getSections().count()+",\n");
		i.getSections().forEach((s)->{
			try {
				f.write(s.getCrn()+", "+s.getCourse()+", "+s.getCampus()+", "+s.getDays()+", "+(s.getInPerson()?"In person":"Not in person fully")+", "+s.getBegin()+" -> "+s.getEnd()+", \n");
			} catch (IOException e) {}
		});
	}
}
