package oy6icjdb2;


public class Program {
	public static void main(String[] args) {		
		DbMethods.Register();

		ListingMethods.task_a("Dolgozik");
		ListingMethods.task_b("Hallgato", "ID");
		ListingMethods.task_c("Hallgato", "Id", false);
		ListingMethods.task_d();
		ListingMethods.listTable("Hallgato");
		ListingMethods.listTable("Projekt");
		ListingMethods.listTable("Dolgozik");
		
		//DbMethods.CommandExec(sqlp);
	}
}
