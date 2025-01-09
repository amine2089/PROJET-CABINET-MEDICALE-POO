package Information_Patient;

import java.sql.Date;

public class Consultation {
	public int ssn;
	private int numPatient;
	private Date date;
	private String type, observation;
	public Consultation(int id, int numPatient, Date date, String type, String observation) {
		super();
		this.ssn = id;
		this.numPatient = numPatient;
		this.date = date;
		this.type = type;
		this.observation = observation;
	}
	
	public int getSsn() {
		return ssn;
	}
	public void setSsn(int id) {
		this.ssn = id;
	}
	public int getNumPatient() {
		return numPatient;
	}
	public void setNumPatient(int numPatient) {
		this.numPatient = numPatient;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getObservation() {
		return observation;
	}
	public void setObservation(String observation) {
		this.observation = observation;
	}

	
}
