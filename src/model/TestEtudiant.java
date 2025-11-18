package model;
import java.sql.SQLException;

public class TestEtudiant {

    public static void main(String[] args) {

        System.out.println("--- DÉBUT DU TEST JDBC ---");


        try {

            System.out.println("\n--- 1. Création et sauvegarde de l'étudiant ---");

            Etudiant etudiantTest = new Etudiant("Thomas Dupont");

            etudiantTest.sauvegarder();


            etudiantTest.afficher();

            System.out.println("\n--- 2. Ajout des notes ---");

            Notation noteMaths = new Notation(14.5, 3);
            Notation noteJava = new Notation(18.0, 4);
            Notation noteSQL = new Notation(16.0, 2);

            etudiantTest.ajouterNote(noteMaths);
            etudiantTest.ajouterNote(noteJava);
            etudiantTest.ajouterNote(noteSQL);




            System.out.println("\n--- 3. Calcul et mise à jour BDD (moyenne/avis) ---");

            etudiantTest.moyenne();
            etudiantTest.avis(12.0);


            etudiantTest.mettreAJour();


            // --- OBJECTIF 5c : Affichage complet des infos récupérées ---

            System.out.println("\n--- 4. VÉRIFICATION FINALE (Lecture BDD) ---");
            System.out.println("Pour prouver que tout est bien en base de données,");
            System.out.println("on va charger un NOUVEL objet 'Etudiant' en lisant la BDD...");


            int idCree = etudiantTest.getId();


            Etudiant etudiantCharge = Etudiant.charger(idCree);

            if (etudiantCharge != null) {
                System.out.println("SUCCÈS : L'étudiant (ID " + idCree + ") a été chargé depuis la BDD :");

                etudiantCharge.afficher();
            } else {
                System.out.println("ÉCHEC : l'étudiant " + idCree + " n'a pas pu être chargé.");
            }

        } catch (SQLException e) {
            System.err.println("ERREUR LORS DU TEST PRINCIPAL :");
            System.err.println("L'opération a échoué. Avez-vous bien démarré XAMPP (MySQL) ?");
            System.err.println("Cause : " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n--- FIN DU TEST ---");
    }
}