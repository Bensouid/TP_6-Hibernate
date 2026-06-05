package ma.projet.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.io.InputStream;
import java.util.Properties;
import ma.projet.classes.*;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();

            // Chargement du fichier application.properties
            Properties props = new Properties();
            InputStream input = HibernateUtil.class.getClassLoader().getResourceAsStream("application.properties");
            props.load(input);
            configuration.setProperties(props);

            // Enregistrement des entités (obligatoire f Hibernate classique)
            configuration.addAnnotatedClass(Employe.class);
            configuration.addAnnotatedClass(Projet.class);
            configuration.addAnnotatedClass(Tache.class);
            configuration.addAnnotatedClass(EmployeTache.class);

            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Échec de la création de la SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}