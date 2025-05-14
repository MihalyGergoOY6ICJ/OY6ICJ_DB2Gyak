package application.menu;

import utils.input.InputType;
import utils.input.Read;
import utils.input.constraints.IntegerInputConstraintRange;
import utils.input.exception.InvalidInputException;

public class MainMenu {
	
	final static private String line = "==================================================";
	final static private String mainMenu =
			"\n" + line 
			+ "\nMain menu:"
			+ "\n(pick one by typing it's number)"
			+ "\n 0. Exit"
			+ "\n 1. Display table"
			+ "\n 2. Insert record"
			+ "\n 3. Modify record"
			+ "\n 4. Delete record"
			+ "\n 5. Get"
			+ "\n 6. Insert records from file (csv file, without headers)"
			+ "\n" + line;
	
	final static private String tableSelectorMenu = 
			"\n" + line
			+"\nSelect the table:"
			+ "\n(pick one by typing it's number)"
			+ "\n 0. Student table"
			+ "\n 1. Course table"
			+ "\n 2. Has table"
			+ "\n" + line;
	
	final static private String mainTableSelectorMenu =
			"\n" + line
			+"\nSelect the table:"
			+ "\n(pick one by typing it's number)"
			+ "\n 0. Student table"
			+ "\n 1. Course table"
			+ "\n" + line;
	final static private String studentUpdateFieldSelectorMenu = 
			"\n" + line
			+ "\nSelect the field to update:"
			+ "\n(pick one by typing it's number)"
			+ "\n 0. name"
			+ "\n 1. credit_sum"
			+ "\n 2. date_of_birth"
			+ "\n 3. email"
			+ "\n" + line;
	
	final static private String courseUpdateFieldSelectorMenu = 
			"\n" + line
			+ "\nSelect the field(s) to update:"
			+ "\n(pick one by typing it's number)"
			+ "\n 0. name"
			+ "\n 1. credit"
			+ "\n 2. exam_date"
			+ "\n 3. required"
			+ "\n" + line;
	
	final static private String getSelectorMenu =
			"\n" + line
			+ "\nSelect one of the options:"
			+ "\n(pick one by typing it's number)"
			+ "\n 0. Student name and email by neptun"
			+ "\n 1. Student names by date of birth (born after)"
			+ "\n 2. Courses by neptun and if it is required"
			+ "\n 3. Exam date and credit by neptun"
			+ "\n 4. Credit by course name"
			+ "\n" + line;
	
	
	public static Menu displayMainMenu() throws InvalidInputException{
		System.out.println(mainMenu);
		
		String input = Read.validatedRead(null,InputType.INTEGER, new IntegerInputConstraintRange(0, 6));
		int menuItemNumber = Integer.parseInt(input);
		return Menu.values()[menuItemNumber];
	}
	
	
	public static TableName displayTableSelectorMenu(boolean onlyMainTable) throws InvalidInputException{
		if(!onlyMainTable) {
			System.out.println(tableSelectorMenu);
			String input = Read.validatedRead(null, InputType.INTEGER, new IntegerInputConstraintRange(0, 2));
			int tableItemNumber = Integer.parseInt(input);
			return TableName.values()[tableItemNumber];
		}
		else {
			System.out.println(mainTableSelectorMenu);
			String input = Read.validatedRead(null, InputType.INTEGER, new IntegerInputConstraintRange(0, 1));
			int tableItemNumber = Integer.parseInt(input);
			return TableName.values()[tableItemNumber];
		}
		
	}
	
	public static int displayUpdateFieldSelectorMenu(TableName tableName) throws InvalidInputException{
		String input = "";
		
		switch(tableName) {
		case STUDENT:
			System.out.println(studentUpdateFieldSelectorMenu);
			input = Read.validatedRead(null, InputType.INTEGER, new IntegerInputConstraintRange(0,3));
			return Integer.valueOf(input);
			
		case COURSE:
			System.out.println(courseUpdateFieldSelectorMenu);
			input = Read.validatedRead(null, InputType.INTEGER, new IntegerInputConstraintRange(0,3));
			return Integer.valueOf(input);
			}
		return 0;
	}
	
	public static int displayGetSelectorMenu() throws InvalidInputException{
		System.out.println(getSelectorMenu);
		
		String input = Read.validatedRead(null, InputType.INTEGER, new IntegerInputConstraintRange(0, 4));
		return Integer.valueOf(input);
	}
}
