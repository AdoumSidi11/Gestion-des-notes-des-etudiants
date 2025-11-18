public class Coordonnee {

    private String nom;
    private String prenom;
    private String email;
    private int telephone;

    public Coordonnee(String nom, String prenom, String email, int telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public int getTelephone() {
        return telephone;
    }

}
