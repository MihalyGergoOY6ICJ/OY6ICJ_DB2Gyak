package jdbc;

import java.util.Scanner;

public class ConsoleMethods {
	Scanner scanner = new Scanner(System.in);
	
	public String ReadData(String s) {
		String data = "";
		System.out.println(s);
		data = scanner.nextLine();
		return data;
	}
	
	
}
