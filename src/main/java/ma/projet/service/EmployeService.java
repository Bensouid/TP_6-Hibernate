package ma.projet.service;

import ma.projet.classes.Employe;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class EmployeService implements IDao<Employe> {

    @Override
    public boolean create(Employe o) {
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
    public boolean update(Employe o) {
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
    public boolean delete(Employe o) {
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
    public Employe getById(int id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            // CHANGER ICI: La classe en premier, l'id en deuxième
            return session.get(Employe.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public List<Employe> getAll() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            return session.createQuery("FROM Employe", Employe.class).list();
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

    // 1. Afficher la liste des tâches réalisées par un employé
    public void afficherTachesRealisees(Employe e) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            // HQL pour récupérer les tâches via la table d'association
            String hql = "SELECT et.tache.nom FROM EmployeTache et WHERE et.employe.id = :empId";
            List<String> taches = session.createQuery(hql, String.class)
                    .setParameter("empId", e.getId())
                    .list();

            System.out.println("Tâches réalisées par l'employé " + e.getNom() + " " + e.getPrenom() + " :");
            for (String nomTache : taches) {
                System.out.println("- " + nomTache);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

    // 2. Afficher la liste des projets gérés par un employé (Chef de projet)
    public void afficherProjetsGeres(Employe e) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "SELECT p.nom FROM Projet p WHERE p.chefDeProjet.id = :empId";
            List<String> projets = session.createQuery(hql, String.class)
                    .setParameter("empId", e.getId())
                    .list();

            System.out.println("Projets gérés par l'employé " + e.getNom() + " " + e.getPrenom() + " :");
            for (String nomProjet : projets) {
                System.out.println("- " + nomProjet);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }
}