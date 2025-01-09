package Information_Patient;

import java.sql.Date;

public class Paiement {
    private int ssn;
    private String nom, prenom;
    private double montant;
    private Date datePaiement;
    private String typePaiement;
    private boolean paiementFait;

    public boolean isPaiementFait() {
		return paiementFait;
	}

	public void setPaiementFait(boolean paiementFait) {
		this.paiementFait = paiementFait;
	}



    public Paiement(int ssn, String nom, String prenom, double montant, Date datePaiement, String typePaiement,
			boolean paiementFait) {
		super();
		this.ssn = ssn;
		this.nom = nom;
		this.prenom = prenom;
		this.montant = montant;
		this.datePaiement = datePaiement;
		this.typePaiement = typePaiement;
		this.paiementFait = paiementFait;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	// Getters et Setters
    public int getSSN() {
        return ssn;
    }

    public void setSSN(int id) {
        this.ssn = id;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Date getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Date datePaiement) {
        this.datePaiement = datePaiement;
    }

    public String getTypePaiement() {
        return typePaiement;
    }

    public void setTypePaiement(String typePaiement) {
        this.typePaiement = typePaiement;
    }

    // Méthode pour afficher les détails d'une facture
    public void afficherFacture() {
        System.out.println("Facture ID: " + ssn);
        System.out.println("Au nom de: " + nom + prenom);
        System.out.println("Montant: " + montant + " €");
        System.out.println("Date de paiement: " + datePaiement);
        System.out.println("Type de paiement: " + typePaiement);
    }
}
