package Information_Patient;

public class Ordonnance {
	private int num_O;
	private String contenu; private int SSN;
	public Ordonnance (int num_O, String contenu, int SSN) {
		super();
		this.num_O = num_O;
		this.contenu = contenu;
		this.SSN = SSN;
	}
	
	public int getNum_O() {
		return num_O;
	}
	public void setNum_O(int num_O) {
		this.num_O = num_O;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	public int getSSN() {
		return SSN;
	}
	public void setSSN(int SSN) {
		this.SSN = SSN;
	}
	
	
}
