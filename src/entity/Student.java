
package entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String department;

    // OneToMany: Bir öğrenci birden fazla kitap ödünç alabilir
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Loan> loans = new ArrayList<>();

    public Student() {}

    public Student(String name, String department) {
        this.name = name;
        this.department = department;
    }

    // Getter & Setter
    public Long getId() { return id; }
    public String getName() { return name; }
    public List<Loan> getLoans() { return loans; }

    @Override
    public String toString() {
        return "ID: " + id + " | " + name + " (" + department + ")";
    }
}
