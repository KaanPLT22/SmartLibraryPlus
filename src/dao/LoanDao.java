package dao;

import entity.Loan;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import java.util.List;

public class LoanDao {
    public void save(Loan loan) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(loan);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void update(Loan loan) { //
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(loan);
            transaction.commit();
        } catch (Exception e) { if (transaction != null) transaction.rollback(); e.printStackTrace(); }
    }

    public void delete(Long id) { //
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Loan loan = session.get(Loan.class, id);
            if (loan != null) session.delete(loan);
            transaction.commit();
        } catch (Exception e) { if (transaction != null) transaction.rollback(); e.printStackTrace(); }
    }

    public Loan getById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Loan.class, id);
        }
    }

    public List<Loan> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Loan l join fetch l.book join fetch l.student", Loan.class).list();
        }
    }

    public Loan getActiveLoanByBookId(Long bookId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Loan l where l.book.id = :bookId and l.returnDate IS NULL", Loan.class)
                    .setParameter("bookId", bookId)
                    .uniqueResult();
        }
    }
}