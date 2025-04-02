package oy6icjdb2;

import java.sql.Connection;

public class Auto {
	private String rendszam;
	private String tipus;
	private String szin;
	private int kor;
	private int ar;
	private int tulaj;
	
	
	public Auto(String rendszam, String tipus, String szin, int kor, int ar, int tulaj) {
		this.rendszam = rendszam;
		this.tipus = tipus;
		this.szin = szin;
		this.kor = kor;
		this.ar = ar;
		this.tulaj = tulaj;
	}

	
	
	public String getRendszam() {
		return rendszam;
	}



	public String getTipus() {
		return tipus;
	}



	public String getSzin() {
		return szin;
	}



	public int getKor() {
		return kor;
	}



	public int getAr() {
		return ar;
	}



	public int getTulaj() {
		return tulaj;
	}

	public static void create_table() {
		Connection conn = DbMethods.Connect();
		String sqlp = "CREATE TABLE Auto ("
				+ "Rendszam char(7) PRIMARY KEY,"
				+ "Tipus char(25),"
				+ "Szin char(15),"
				+ "Kor number(3),"
				+ "Ar number(10),"
				+ "Tulaj char(3),"
				+ "foreign key(Tulaj) references Tulajdonos(Ekod)"
				+ ")";
		System.out.println(sqlp);
		DbMethods.CommandExec(sqlp);
		DbMethods.DisConnect(conn);
	}
}
