package Q1;

import java.util.LinkedList;

import chrono.TimeSchedule;
import chrono.TimeStamp;

public class TestClass {

	public static void main(String[] args) {
		TimeSchedule<Integer> sch = new TimeSchedule<>();
		LinkedList<Integer> list = new LinkedList<>();
		TimeStamp t = new TimeStamp(12,00);
		System.out.println(t.toPercent());
		list.add(2);
		list.add(3);
		sch.add(1, new TimeStamp(02,03), new TimeStamp(03,03));
		sch.add(2, new TimeStamp(01,01), new TimeStamp(02,03));
		sch.add(2, new TimeStamp(03,03), new TimeStamp(12,44));
		System.out.println(sch);
		sch.remove(1, new TimeStamp(02,03), new TimeStamp(03,03));
		sch.remove(2, new TimeStamp(03,03), new TimeStamp(12,44));
		System.out.println(sch);
	}

}
