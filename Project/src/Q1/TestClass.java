package Q1;

import java.util.LinkedList;

import chrono.TimeSchedule;
import chrono.TimeStamp;

public class TestClass {

	public static void main(String[] args) {
		int n = 1000;
		int count = 0;
		for (int i=1; i<n; i++) {
		if (i%3 == 0 || i%7== 0) 
		count++;
		}
		System.out.println("Count=" + count);
	}
}
