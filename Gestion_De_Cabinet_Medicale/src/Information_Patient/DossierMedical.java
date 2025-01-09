package Information_Patient;

import java.sql.Date;
import java.util.ArrayList;

public class DossierMedical {

    private String prenom;
    private String nom; // Correction de la capitalisation
    private int ssn;

    private ArrayList<String> antecedentsMedicaux;
    private ArrayList<String> antecedentsChirurgicaux;
    private ArrayList<String> maladiesChroniques;
    private ArrayList<String> allergies;
    private ArrayList<String> vaccinations;
    private ArrayList<String> traitementsEnCours;
    private ArrayList<String> historiqueConsultation;

    private int codeDossier;
    private Date dateCreation;
    private String notesGenerales;
    private String contactUrgence;

    // Constructeur
    public DossierMedical(int ssn, String prenom, String nom, int codeDossier, Date dateCreation, String notesGenerales, String contactUrgence) {
        this.ssn = ssn;
        this.prenom = prenom;
        this.nom = nom;
        this.codeDossier = codeDossier;
        this.dateCreation = dateCreation;
        this.notesGenerales = notesGenerales;
        this.contactUrgence = contactUrgence;

        // Initialisation des ArrayLists
        this.antecedentsMedicaux = new ArrayList<>();
        this.antecedentsChirurgicaux = new ArrayList<>();
        this.maladiesChroniques = new ArrayList<>();
        this.allergies = new ArrayList<>();
        this.vaccinations = new ArrayList<>();
        this.traitementsEnCours = new ArrayList<>();
        this.historiqueConsultation = new ArrayList<>();
    }

    // Getters et Setters
    public int getSsn() {
        return ssn;
    }

    public void setSsn(int ssn) {
        this.ssn = ssn;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<String> getAntecedentsMedicaux() {
        return antecedentsMedicaux;
    }

    public void setAntecedentsMedicaux(ArrayList<String> antecedentsMedicaux) {
        this.antecedentsMedicaux = antecedentsMedicaux != null ? antecedentsMedicaux : new ArrayList<>();
    }

    public ArrayList<String> getAntecedentsChirurgicaux() {
        return antecedentsChirurgicaux;
    }

    public void setAntecedentsChirurgicaux(ArrayList<String> antecedentsChirurgicaux) {
        this.antecedentsChirurgicaux = antecedentsChirurgicaux != null ? antecedentsChirurgicaux : new ArrayList<>();
    }

    public ArrayList<String> getMaladiesChroniques() {
        return maladiesChroniques;
    }

    public void setMaladiesChroniques(ArrayList<String> maladiesChroniques) {
        this.maladiesChroniques = maladiesChroniques != null ? maladiesChroniques : new ArrayList<>();
    }

    public ArrayList<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(ArrayList<String> allergies) {
        this.allergies = allergies != null ? allergies : new ArrayList<>();
    }

    public ArrayList<String> getVaccinations() {
        return vaccinations;
    }

    public void setVaccinations(ArrayList<String> vaccinations) {
        this.vaccinations = vaccinations != null ? vaccinations : new ArrayList<>();
    }

    public ArrayList<String> getTraitementsEnCours() {
        return traitementsEnCours;
    }

    public void setTraitementsEnCours(ArrayList<String> traitementsEnCours) {
        this.traitementsEnCours = traitementsEnCours != null ? traitementsEnCours : new ArrayList<>();
    }

    public ArrayList<String> getHistoriqueConsultation() {
        return historiqueConsultation;
    }

    public void setHistoriqueConsultation(ArrayList<String> historiqueConsultation) {
        this.historiqueConsultation = historiqueConsultation != null ? historiqueConsultation : new ArrayList<>();
    }

    public int getCodeDossier() {
        return codeDossier;
    }

    public void setCodeDossier(int codeDossier) {
        this.codeDossier = codeDossier;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getNotesGenerales() {
        return notesGenerales;
    }

    public void setNotesGenerales(String notesGenerales) {
        this.notesGenerales = notesGenerales;
    }

    public String getContactUrgence() {
        return contactUrgence;
    }

    public void setContactUrgence(String contactUrgence) {
        this.contactUrgence = contactUrgence;
    }
}
