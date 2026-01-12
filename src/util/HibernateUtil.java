package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Dosya en dış dizinde olduğu için Java'ya tam yerini gösteriyoruz
            return new Configuration()
                    .configure(new java.io.File("hibernate.cfg.xml"))
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("SessionFactory oluşturulamadı: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Uygulama kapanırken bağlantıyı kesmek için
        getSessionFactory().close();
    }
}