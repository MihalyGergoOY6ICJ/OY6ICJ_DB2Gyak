package oy6icjdb2;

import java.awt.List;
import java.io.IOException;

public class Program {
	public static void main(String[] args) throws IOException{		
		DbMethods.Register();
		ListingMethods.listTable("Dolgozik");
		
		ListingMethods.listTable("Hallgato");
		ListingMethods.uniList("Hallgato", "id=1", "Vnev", "Knev");
		
		Hallgato[] hallgatos = {
				new Hallgato(4, "Lajos", "Béla", "2000-01-01", "utca4"),
				new Hallgato(5, "Olajos", "Lajos", "2001-02-02", "utca5"),
				new Hallgato(6, "Setőfi", "Pándor", "2002-03-03", "utca6"),
				new Hallgato(7, "Mekk", "Elek", "2003-04-04", "utca7"),
				new Hallgato(8, "János", "János", "2004-05-05", "utca8")
		};
		DbMethods.insert_with_ps(hallgatos);
		ListingMethods.listTable("Hallgato");
		ListingMethods.listTable("Dolgozik");
		int[][] ids = {
				{2,13},
				{3,14},
				{3,11},
				{2,12},
				{3,12},
		};
		
		DbMethods.deleteWithPs(ids);
		ListingMethods.listTable("Dolgozik");
		
		int ids2[] = {1,3};
		
		ListingMethods.listTable("Projekt");
		
		DbMethods.listHallgatoAndProjekt(ids2);
	}
	
	
	/*
	
	private static void menu() throws IOException{
		final String line = "----------------------------------------------------";
		System.out.println("\n");
		System.out.println("Menü");
		System.out.println(line);
		System.out.println("Hallgato tábla");
		System.out.println(line);
		System.out.println("0. Kilépés ");
		System.out.println("1. Listázás ");
		System.out.println("2. Beszúrás ");
		System.out.println("3. Törlés ");
		System.out.println("4. Módosítás");
		String input = Utils.readData("Add meg a menüpont számát: ");
		if(Utils.testInt(input, 0, 4)) {
			switch(Integer.valueOf(input)) {
			case 0:
				//EXIT
				System.exit(0);break; 
			case 1:
				//LIST
				ListingMethods.listTable("Hallgato");break;
			case 2:{
				//INSERT
				String id = Utils.readData("Add meg az id-t");
				String vnev = Utils.readData("Add meg a vezetéknevet");
				String knev = Utils.readData("Add meg a keresztnevet");
				String szuli = Utils.readData("Add meg a születési időt");
				String lakcim = Utils.readData("Add meg a lakcímet");
				DbMethods.hallgato_insert(id, vnev, knev, szuli, lakcim);
				break;
			}
			case 3:{
				//DELETE
				String id = Utils.readData("Add meg az id-t");
				DbMethods.hallgato_delete(id);
				break;
			}
			case 4:{
				//UPDATE
				String id = Utils.readData("Add meg az id-t");
				String vnev = Utils.readData("Add meg az új vezetéknevet");
				DbMethods.hallgato_update(id, vnev);
				break;
			}
			}
		}
		
		
	}
	
	*/
}
