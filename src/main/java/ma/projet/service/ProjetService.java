package ma.projet.service;

import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.classes.EmployeTache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.text.SimpleDateFormat;
import java.util.List;

public class ProjetService implements IDao<Projet> {

    @Override
    public boolean create(Projet o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public boolean update(Projet o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public boolean delete(Projet o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public Projet getById(int id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            return session.get(Projet.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public List<Projet> getAll() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            return session.createQuery("FROM Projet", Projet.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    // =========================================================================
    // LES METHODES DEMANDEES DANS L'EXERCICE
    // =========================================================================

    // 1. Afficher la liste des tâches planifiées pour un projet
    public void afficherTachesPlanifiees(Projet p) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM Tache t WHERE t.projet.id = :projetId";
            List<Tache> taches = session.createQuery(hql, Tache.class)
                    .setParameter("projetId", p.getId())
                    .list();

            System.out.println("Tâches planifiées pour le projet : " + p.getNom());
            for (Tache t : taches) {
                System.out.println("- " + t.getNom() + " (Prix: " + t.getPrix() + " DH)");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

    // 2. Afficher la liste des tâches réalisées avec les dates réelles (L'affichage exact de l'image)
    public void afficherTachesRealiseesAvecDates(Projet p) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            // HQL pour récupérer les affectations (EmployeTache) liées aux tâches de ce projet
            String hql = "FROM EmployeTache et WHERE et.tache.projet.id = :projetId";
            List<EmployeTache> list = session.createQuery(hql, EmployeTache.class)
                    .setParameter("projetId", p.getId())
                    .list();

            // Formatage des dates b7al f l'exemple (Ex: 14 Janvier 2013 ou simple dd/MM/yyyy)
            SimpleDateFormat dfSms = new SimpleDateFormat("dd MMMM yyyy");
            SimpleDateFormat dfTab = new SimpleDateFormat("dd/MM/yyyy");

            // L'affichage exact de l'exercice :
            System.out.println("Projet : " + p.getId() + "\tNom : " + p.getNom() + "\tDate début : " + (p.getDateDebut() != null ? dfSms.format(p.getDateDebut()) : ""));
            System.out.println("Liste des tâches:");
            System.out.println("Num\tNom\t\tDate Début Réelle\tDate Fin Réelle");

            for (EmployeTache et : list) {
                System.out.println(et.getTache().getId() + "\t" +
                        et.getTache().getNom() + "\t\t" +
                        (et.getDateDebutReelle() != null ? dfTab.format(et.getDateDebutReelle()) : "-") + "\t\t" +
                        (et.getDateFinReelle() != null ? dfTab.format(et.getDateFinReelle()) : "-"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }
}