package jdbc;


public class Program {
	public static void main(String[] args) {
		ConsoleMethods cm  = new ConsoleMethods();
		
		DbMethods.Register();
		String rendszam = cm.ReadData("Kérem a rendszámot:");
		String ar = cm.ReadData("Kérem az új árat: ");
		DbMethods.UpdateData(rendszam, ar);
		
		DbMethods.ReadAllData();
	}
}
