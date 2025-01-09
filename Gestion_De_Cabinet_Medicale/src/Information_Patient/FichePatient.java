package Information_Patient;

import java.sql.Date;

public class FichePatient {

	private int SSN;
    private String Prenom, Nom, Address, Ville, Email, Job, Sexe, Grp_Sanguin;
    private String Post; // Changed to String for alignment with DB schema
    private String Num;  // Changed to String for alignment with DB schema
    private Date date_naissance;

    public FichePatient(int sSN, String prenom, String nom, String address, String post, String ville, String email,
                        String num, String job, String sexe, String grp_Sanguin, Date date_naissance) {
        this.SSN = sSN;
        this.Prenom = prenom;
        this.Nom = nom;
        this.Address = address;
        this.Post = post;
        this.Ville = ville;
        this.Email = email;
        this.Num = num;
        this.Job = job;
        this.Sexe = sexe;
        this.Grp_Sanguin = grp_Sanguin;
        this.date_naissance = date_naissance;
    }

    // Getters and Setters
    public int getSSN() {
        return SSN;
    }

    public void setSSN(int sSN) {
        SSN = sSN;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPost() {
        return Post;
    }

    public void setPost(String post) {
        Post = post;
    }

    public String getVille() {
        return Ville;
    }

    public void setVille(String ville) {
        Ville = ville;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public String getJob() {
        return Job;
    }

    public void setJob(String job) {
        Job = job;
    }

    public String getSexe() {
        return Sexe;
    }

    public void setSexe(String sexe) {
        Sexe = sexe;
    }

    public String getGrp_Sanguin() {
        return Grp_Sanguin;
    }

    public void setGrp_Sanguin(String grp_Sanguin) {
        Grp_Sanguin = grp_Sanguin;
    }

    public Date getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(Date date_naissance) {
        this.date_naissance = date_naissance;
    }
}
