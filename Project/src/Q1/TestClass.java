package Q1;

import java.util.LinkedList;

import chrono.TimeSchedule;
import chrono.TimeStamp;

public class TestClass {

	public static void main(String[] args) {
		TimeSchedule<Integer> sch = new TimeSchedule<>();
		LinkedList<Integer> list = new LinkedList<>();
		list.add(1);
		var v1 = list.listIterator(1);
		
		list.add(2);
		list.add(3);
		sch.add(0, new TimeStamp(02,03), new TimeStamp(03,03));
		sch.add(2, new TimeStamp(01,03), new TimeStamp(04,03));
		sch.add(1, new TimeStamp(04,03), new TimeStamp(10,03));
		System.out.println(sch.add(0, new TimeStamp(02,03), new TimeStamp(03,03)));
		System.out.println(sch.add(2, new TimeStamp(01,03), new TimeStamp(04,03)));
		System.out.println(sch.add(1, new TimeStamp(04,03), new TimeStamp(10,03)));
		System.out.println(sch);
	}

}
