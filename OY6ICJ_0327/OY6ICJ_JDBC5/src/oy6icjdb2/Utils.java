package oy6icjdb2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utils {
	public static String readData(String prompt) throws IOException{
		System.out.println(prompt);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String in = reader.readLine();
		
		return in;
	}
	
	public static boolean testInt(String s, int min, int max) {
		if(s.length() == 0) {
			System.out.println("Nem adtál meg semmit!");
			return false;
		}
		try {
			int x = Integer.valueOf(s);
			if(x > max || x < min) {
				System.out.println("Nem [" + min + "," + max + "] közötti szám" );
				return false;
			}
		}catch(NumberFormatException e) {
				System.out.println("Nem számot adtál meg!");
				return false;
		}
		return true;
		}
	}
