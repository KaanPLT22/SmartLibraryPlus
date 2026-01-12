
package app;

import dao.BookDao;
import dao.LoanDao;
import dao.StudentDao;
import entity.Book;
import entity.Loan;
import entity.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static BookDao bookDao = new BookDao();
    static StudentDao studentDao = new StudentDao();
    static LoanDao loanDao = new LoanDao();

    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.OFF);
        while (true) {
            System.out.println("\n=== SMART LIBRARY PLUS ===");
            System.out.println("1 - Kitap Ekle");
            System.out.println("2 - Kitapları Listele");
            System.out.println("3 - Öğrenci Ekle");
            System.out.println("4 - Öğrencileri Listele");
            System.out.println("5 - Kitap Ödünç Ver");
            System.out.println("6 - Ödünç Listesini Görüntüle");
            System.out.println("7 - Kitap Geri Teslim Al");
            System.out.println("0 - Çıkış");
            System.out.print("Seçiminiz: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Dummy read

            switch (choice) {
                case 1: addBook(); break;
                case 2: listBooks(); break;
                case 3: addStudent(); break;
                case 4: listStudents(); break;
                case 5: borrowBook(); break;
                case 6: listLoans(); break;
                case 7: returnBook(); break;
                case 0:
                    System.out.println("Çıkış yapılıyor...");
                    System.exit(0);
                default:
                    System.out.println("Geçersiz seçim.");
            }
        }
    }

    private static void addBook() {
        System.out.print("Kitap Başlığı: ");
        String title = scanner.nextLine();
        System.out.print("Yazar: ");
        String author = scanner.nextLine();
        System.out.print("Yıl: ");
        int year = scanner.nextInt();

        Book book = new Book(title, author, year);
        bookDao.save(book);
        System.out.println("Kitap kaydedildi.");
    }

    private static void listBooks() {
        List<Book> books = bookDao.getAll();
        if (books.isEmpty()) System.out.println("Kayıtlı kitap yok.");
        else books.forEach(System.out::println);
    }

    private static void addStudent() {
        System.out.print("Öğrenci Adı: ");
        String name = scanner.nextLine();
        System.out.print("Bölüm: ");
        String dept = scanner.nextLine();

        Student student = new Student(name, dept);
        studentDao.save(student);
        System.out.println("Öğrenci kaydedildi.");
    }

    private static void listStudents() {
        List<Student> students = studentDao.getAll();
        if (students.isEmpty()) System.out.println("Kayıtlı öğrenci yok.");
        else students.forEach(System.out::println);
    }

    private static void borrowBook() {
        System.out.println("\n--- Mevcut Öğrenciler ---");
        List<Student> students = studentDao.getAll();
        if (students.isEmpty()) {
            System.out.println("Sistemde kayıtlı öğrenci yok! Önce öğrenci eklemelisiniz.");
            return;
        }
        for (Student s : students) {
            System.out.println("ID: " + s.getId() + " | İsim: " + s.getName());
        }
        System.out.println("---------------------------\n");

        System.out.print("Öğrenci ID: ");
        Long studentId = scanner.nextLong();

        System.out.println("\n--- Mevcut Kitaplar ---");
        List<Book> books = bookDao.getAll();
        if (books.isEmpty()) {
            System.out.println("Sistemde kayıtlı kitap yok! Önce kitap eklemelisiniz.");
            return;
        }
        for (Book b : books) {
            System.out.println("ID: " + b.getId() + " | Kitap: " + b.getTitle() + " | Durum: " + b.getStatus());
        }
        System.out.println("---------------------------\n");

        System.out.print("Kitap ID: ");
        Long bookId = scanner.nextLong();

        Book book = bookDao.getById(bookId);
        Student student = studentDao.getById(studentId);

        if (book != null && student != null) {
            if (book.getStatus() == Book.Status.AVAILABLE) {

                // --- SADECE BURAYA TRY-CATCH EKLEDİK, GERİSİ AYNI ---
                try {
                    Loan loan = new Loan(student, book);
                    loanDao.save(loan);

                    book.setStatus(Book.Status.BORROWED);
                    bookDao.update(book);

                    System.out.println("Kitap başarıyla ödünç verildi.");
                } catch (Exception e) {
                    System.out.println("\n************************************************");
                    System.out.println("⚠️  UYARI: OneToOne Kısıtlaması Devreye Girdi!");
                    System.out.println("Bu kitap daha önce bir ödünç işlemine girmiş.");
                    System.out.println("bir kez ödünç kaydına sahip olabilir.");
                    System.out.println("************************************************");
                }
                // ----------------------------------------------------

            } else {
                System.out.println("HATA: Kitap şu an başkasında (BORROWED).");
            }
        } else {
            System.out.println("HATA: Öğrenci veya Kitap bulunamadı.");
        }
    }

    private static void listLoans() {
        List<Loan> loans = loanDao.getAll();
        System.out.println("--- ÖDÜNÇ LİSTESİ ---");
        for (Loan l : loans) {
            String returnDateStr = (l.getReturnDate() == null) ? "Teslim Edilmedi" : l.getReturnDate().toString();
            System.out.println("Öğrenci: " + l.getStudent().getName() +
                    " | Kitap: " + l.getBook().getTitle() +
                    " | Alış: " + l.getBorrowDate() +
                    " | İade: " + returnDateStr);
        }
    }

    private static void returnBook() {


        System.out.println("\n--- Şu An Ödünçte Olan Kitaplar ---");
        List<Book> books = bookDao.getAll();
        boolean foundBorrowed = false;

        for (Book b : books) {
            if (b.getStatus() == Book.Status.BORROWED) {
                System.out.println("ID: " + b.getId() + " | Kitap: " + b.getTitle() + " | Durum: ÖDÜNÇTE");
                foundBorrowed = true;
            }
        }

        if (!foundBorrowed) {
            System.out.println("Şu an ödünçte hiç kitap yok!");
            return;
        }
        System.out.println("-----------------------------------\n");


        System.out.print("İade edilecek Kitap ID: ");
        Long bookId = scanner.nextLong();

        // Aktif ödünç kaydını bul (Henüz iade edilmemiş olan)
        Loan loan = loanDao.getActiveLoanByBookId(bookId);

        if (loan != null) {
            // 1. İade tarihini güncelle
            loan.setReturnDate(LocalDate.now());
            loanDao.update(loan);

            // 2. Kitabın durumunu AVAILABLE yap
            Book book = loan.getBook();
            book.setStatus(Book.Status.AVAILABLE);
            bookDao.update(book);

            System.out.println("Kitap iade alındı.");
        } else {
            System.out.println("Bu kitap için aktif bir ödünç kaydı bulunamadı.");
        }
    }
}