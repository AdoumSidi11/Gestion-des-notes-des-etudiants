package model;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class Etudiant {


    private int id;

    private String nom;
    private double moyenne;
    private String avis;
    private List<Notation> listNotes;


    public Etudiant(String nom) {
        this.id = 0;
        this.nom = nom;
        this.listNotes = new ArrayList<>();
        this.moyenne = 0.0;
        this.avis = "En attente";
    }
    private Etudiant(int id, String nom, double moyenne, String avis) {
        this.id = id;
        this.nom = nom;
        this.moyenne = moyenne;
        this.avis = avis;
        this.listNotes = new ArrayList<>(); // La liste sera remplie plus tard
    }

    private int getSommeCoefficients() {

        int sommeCoefs = 0;

        for (Notation notation : this.listNotes) {

            sommeCoefs += notation.getCoef();
        }
        return sommeCoefs;
    }

    private double getSommeNotesPonderees() {
        double sommePonderee = 0.0;

        for (Notation notation : this.listNotes) {
            sommePonderee += notation.getNote() * notation.getCoef();
        }

        return sommePonderee;
    }

    public void moyenne() {

        int sommeCoefs = this.getSommeCoefficients();

        double sommePonderee = this.getSommeNotesPonderees();

        if (sommeCoefs > 0) {

            this.moyenne = sommePonderee / sommeCoefs;
        } else {

            this.moyenne = 0.0;
        }
    }


    public void avis(double moyenneSeuil) {
        if (this.moyenne >= moyenneSeuil) {

            this.avis = "Admis";
        } else {
            this.avis = "Non admis";
        }
    }


    public void afficher() {

        System.out.println("Bilan de l'étudiant : " + this.nom + "(ID:" + this.id + ")");

        System.out.println("Détail des notes :");

        if (this.listNotes.isEmpty()) {
            System.out.println("\t(Aucune note pour cet étudiant)");
        } else {
            for (Notation notation : this.listNotes) {
                notation.afficher();
            }
        }

        System.out.println("Moyenne générale : " + String.format("%.2f", this.moyenne) + "/20");

        System.out.println("Avis de passage : " + this.avis);
    }

    public void sauvegarder() throws SQLException {
        String sql = "INSERT INTO etudiants (nom) VALUES (?)";

        if (this.id != 0) {
            System.out.println("Cet étudiant est déjà en base (ID: " + this.id + "). Utilisez mettreAJour().");
            return;
        }

        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, this.nom);


            int rowsAffected = pstmt.executeUpdate();


            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        // On met à jour l'ID de notre objet Java !
                        this.id = generatedKeys.getInt(1);
                        System.out.println("Etudiant " + this.nom + " sauvegardé avec l'ID: " + this.id);
                    }
                }
            }
        }

    }

    public void ajouterNote(Notation note) throws SQLException {

        if (this.id == 0) {
            System.out.println("Erreur : Sauvegardez l'étudiant avant d'ajouter des notes !");
            return;
        }


        String sql = "INSERT INTO notations (note, coef, id_etudiant_fk) VALUES (?, ?, ?)";

        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, note.getNote());
            pstmt.setInt(2, note.getCoef());
            pstmt.setInt(3, this.id); // On lie la note à l'ID de cet étudiant

            pstmt.executeUpdate();
        }


        this.listNotes.add(note);
        System.out.println("Note ajoutée à " + this.nom);
    }


    public void mettreAJour() throws SQLException {
        if (this.id == 0) {
            System.out.println("Erreur : Impossible de mettre à jour un étudiant non sauvegardé.");
            return;
        }

        String sql = "UPDATE etudiants SET moyenne = ?, avis = ? WHERE id_etudiant = ?";

        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, this.moyenne);
            pstmt.setString(2, this.avis);
            pstmt.setInt(3, this.id);

            pstmt.executeUpdate();
            System.out.println("Mise à jour de " + this.nom + " (Moyenne: " + this.moyenne + ", Avis: " + this.avis + ")");
        }
    }


    public static Etudiant charger(int id_a_charger) throws SQLException {
        Etudiant etudiantCharge = null;


        String sqlEtudiant = "SELECT nom, moyenne, avis FROM etudiants WHERE id_etudiant = ?";

        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmtEtudiant = conn.prepareStatement(sqlEtudiant)) {

            pstmtEtudiant.setInt(1, id_a_charger);

            try (ResultSet rsEtudiant = pstmtEtudiant.executeQuery()) {
                if (rsEtudiant.next()) {
                    // On a trouvé l'étudiant, on le crée
                    String nom = rsEtudiant.getString("nom");
                    double moyenne = rsEtudiant.getDouble("moyenne");
                    String avis = rsEtudiant.getString("avis");

                    etudiantCharge = new Etudiant(id_a_charger, nom, moyenne, avis);
                } else {

                    System.out.println("Aucun étudiant trouvé avec l'ID: " + id_a_charger);
                    return null; // On retourne null
                }
            }
        }

        String sqlNotes = "SELECT note, coef FROM notations WHERE id_etudiant_fk = ?";

        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmtNotes = conn.prepareStatement(sqlNotes)) {

            pstmtNotes.setInt(1, id_a_charger);

            try (ResultSet rsNotes = pstmtNotes.executeQuery()) {
                // On boucle sur toutes les notes trouvées
                while (rsNotes.next()) {
                    double note = rsNotes.getDouble("note");
                    int coef = rsNotes.getInt("coef");

                    // On crée l'objet Notation
                    Notation notationChargee = new Notation(note, coef);

                    // On l'ajoute à la liste de l'étudiant
                    etudiantCharge.listNotes.add(notationChargee);
                }
            }
        }

        System.out.println("Chargement de " + etudiantCharge.nom + " et ses " + etudiantCharge.listNotes.size() + " notes terminé.");
        return etudiantCharge;
    }

    public String getNom() {
        return nom;
    }

    public int getId() {
        return id;
    }
}