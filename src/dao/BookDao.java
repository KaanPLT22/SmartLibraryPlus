package dao; // "Main.dao" değil, sadece "dao" yap

import entity.Book; // "Main.entity" değil, sadece "entity.Book" yap
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import java.util.List;

public class BookDao {
    public void save(Book book) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(book);
            transaction.commit();
        } catch (Exception e) { if (transaction != null) transaction.rollback(); e.printStackTrace(); }
    }

    public void update(Book book) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(book);
            transaction.commit();
        } catch (Exception e) { if (transaction != null) transaction.rollback(); e.printStackTrace(); }
    }

    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Book book = session.get(Book.class, id);
            if (book != null) session.delete(book);
            transaction.commit();
        } catch (Exception e) { if (transaction != null) transaction.rollback(); e.printStackTrace(); }
    }

    public Book getById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Book.class, id);
        }
    }

    public List<Book> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Book", Book.class).list();
        }
    }
}