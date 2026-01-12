package entity;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    // Enum tanımı
    public enum Status { AVAILABLE, BORROWED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private int year;

    @Enumerated(EnumType.STRING)
    private Status status;


    @OneToOne(mappedBy = "book")
    private Loan loan;

    // Parametresiz Constructor (Hibernate için zorunlu)
    public Book() {}

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.status = Status.AVAILABLE; // Varsayılan durum
    }

    // Getter & Setterlar
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    @Override
    public String toString() {
        return "ID: " + id + " | " + title + " (" + author + ") - " + status;
    }
}