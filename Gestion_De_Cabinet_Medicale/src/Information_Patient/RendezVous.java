package Information_Patient;

import java.sql.Date;

public class RendezVous {
    private Date jour;
    private String nom;
    private String prenom;
    private int patient_ssn;

    public RendezVous(Date jour, int patient_ssn, String nom, String prenom) {
        this.jour = jour;
        this.nom = nom;
        this.prenom = prenom;
        this.patient_ssn = patient_ssn;
    }

    public Date getJour() {
        return jour;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getSSN() {
        return patient_ssn;
    }
}

