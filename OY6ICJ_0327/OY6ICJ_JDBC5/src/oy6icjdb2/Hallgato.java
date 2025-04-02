package oy6icjdb2;

public class Hallgato {
	private int id;
	private String vnev;
	private String knev;
	private String szuli;
	private String lakcim;
	
	
	public Hallgato(int id, String vnev, String knev, String szuli, String lakcim) {
		this.id = id;
		this.vnev = vnev;
		this.knev = knev;
		this.szuli = szuli;
		this.lakcim = lakcim;
	}


	public int getId() {
		return id;
	}


	public String getVnev() {
		return vnev;
	}


	public String getKnev() {
		return knev;
	}


	public String getSzuli() {
		return szuli;
	}


	public String getLakcim() {
		return lakcim;
	}
	
	
	
	
}
