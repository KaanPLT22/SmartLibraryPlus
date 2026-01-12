
package entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate borrowDate;
    private LocalDate returnDate;


    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;


    @OneToOne
    @JoinColumn(name = "book_id", unique = true)
    private Book book;

    public Loan() {}

    public Loan(Student student, Book book) {
        this.student = student;
        this.book = book;
        this.borrowDate = LocalDate.now();
        this.returnDate = null;
    }

    // Getter & Setter
    public Long getId() { return id; }
    public Book getBook() { return book; }
    public Student getStudent() { return student; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
}