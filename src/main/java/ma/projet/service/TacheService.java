package ma.projet.service;

import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.Date;
import java.util.List;

public class TacheService implements IDao<Tache> {

    @Override
    public boolean create(Tache o) {
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
    public boolean update(Tache o) {
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
    public boolean delete(Tache o) {
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
    public Tache getById(int id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            return session.get(Tache.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public List<Tache> getAll() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            return session.createQuery("FROM Tache", Tache.class).list();
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

    // 1. Une méthode pour afficher les tâches dont le prix est supérieur à 1000 DH (requête nommée)
    public void afficherTachesPrixSuperieurA1000() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            // Appel de la NamedQuery d rna f la classe Tache
            Query<Tache> query = session.createNamedQuery("Tache.findByPrixSup", Tache.class);
            query.setParameter("prixSeuil", 1000.0);

            List<Tache> taches = query.list();

            System.out.println("Tâches dont le prix est supérieur à 1000 DH :");
            for (Tache t : taches) {
                System.out.println("- " + t.getNom() + " (" + t.getPrix() + " DH)");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

    // 2. Une méthode pour afficher les tâches réalisées entre deux dates
    public void afficherTachesEntreDeuxDates(Date d1, Date d2) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            // Requête HQL li kat-jib les tâches mn la table intermédiaire fin kaynin les dates réelles
            String hql = "SELECT et.tache FROM EmployeTache et WHERE et.dateDebutReelle BETWEEN :date1 AND :date2";
            List<Tache> taches = session.createQuery(hql, Tache.class)
                    .setParameter("date1", d1)
                    .setParameter("date2", d2)
                    .list();

            System.out.println("Tâches réalisées entre les deux dates indiquées :");
            for (Tache t : taches) {
                System.out.println("- " + t.getNom());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }
}