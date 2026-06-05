package org.example;

import ma.projet.classes.*;
import ma.projet.service.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // 1. Instanciation des services
        EmployeService employeService = new EmployeService();
        ProjetService projetService = new ProjetService();
        TacheService tacheService = new TacheService();
        EmployeTacheService employeTacheService = new EmployeTacheService();

        // Format de date pour insérer les données
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            // 2. Création de l'employé (Chef de projet)
            Employe emp1 = new Employe("Alami", "Ahmed", "0661234567");
            employeService.create(emp1);

            // 3. Création du Projet (Id: 4, Gestion de stock, 14 Janvier 2013)
            Date dateDebProjet = sdf.parse("14/01/2013");
            Date dateFinProjet = sdf.parse("31/12/2013");
            Projet proj = new Projet("Gestion de stock", dateDebProjet, dateFinProjet, emp1);
            projetService.create(proj);

            // 4. Création des Tâches
            Tache t1 = new Tache("Analyse", sdf.parse("15/01/2013"), sdf.parse("15/02/2013"), 1200.0, proj);
            Tache t2 = new Tache("Conception", sdf.parse("16/02/2013"), sdf.parse("15/03/2013"), 900.0, proj);
            Tache t3 = new Tache("Développement", sdf.parse("16/03/2013"), sdf.parse("30/04/2013"), 2500.0, proj);

            tacheService.create(t1);
            tacheService.create(t2);
            tacheService.create(t3);

            // 5. Affectation avec les dates réelles du tableau
            EmployeTache et1 = new EmployeTache(emp1, t1, sdf.parse("18/02/2013"), sdf.parse("28/02/2013"));
            EmployeTache et2 = new EmployeTache(emp1, t2, sdf.parse("18/03/2013"), sdf.parse("15/03/2013"));
            EmployeTache et3 = new EmployeTache(emp1, t3, sdf.parse("18/04/2013"), sdf.parse("25/04/2013"));

            employeTacheService.create(et1);
            employeTacheService.create(et2);
            employeTacheService.create(et3);

            System.out.println("==================================================");
            System.out.println("   DONNÉES INSÉRÉES AVEC SUCCÈS DANS LA BD !      ");
            System.out.println("==================================================\n");

            // =========================================================================
            // TEST DES AFFICHAGES DEMANDÉS
            // =========================================================================

            System.out.println("--- TEST AFFICHAGE PROJET AVEC DATES RÉELLES ---");
            projetService.afficherTachesRealiseesAvecDates(proj);
            System.out.println();

            System.out.println("--- TEST NAMED QUERY (PRIX > 1000 DH) ---");
            tacheService.afficherTachesPrixSuperieurA1000();
            System.out.println();

            System.out.println("--- TEST TÂCHES ENTRE DEUX DATES ---");
            Date dDebutRecherche = sdf.parse("01/02/2013");
            Date dFinRecherche = sdf.parse("01/05/2013");
            tacheService.afficherTachesEntreDeuxDates(dDebutRecherche, dFinRecherche);
            System.out.println();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}