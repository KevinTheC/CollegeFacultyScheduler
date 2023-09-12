package Q1;
import java.math.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.*;
import java.lang.*;
@SuppressWarnings("unused")
public class Main {
	public static void main(String[] args) throws IOException {
		FileReader fr = new FileReader("src/Q1/Instructors.csv");
		BufferedReader in = new BufferedReader(fr);
		while (in.readLine().charAt(0)!='—') {}
		ArrayList<Instructor> ar = new ArrayList<>();
		while (in.ready()) {
			ArrayList<String> strings = new ArrayList<>();
			while (in.ready()) {
				String str = in.readLine();
				if (str.length()==0||str.charAt(0)=='—')
					break;
				strings.add(str);
			}
			ar.add(new Instructor(strings,','));
		}
		for (Instructor i : ar) {
			System.out.println(i);
			System.out.println();
		}
		in.close();
		fr.close();
	}
}
