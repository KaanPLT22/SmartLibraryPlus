# 🏛️ Smart Library Plus: Katmanlı Mimari ile Kütüphane Yönetimi

![Java](https://img.shields.io/badge/Java-23-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-5.6-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
![SQLite](https://img.shields.io/badge/SQLite-3.42-003B57?style=for-the-badge&logo=sqlite&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)

## 🎯 Projenin Amacı ve Senaryo
Bu proje, kütüphane operasyonlarını dijitalleştirmek için **Katmanlı Mimari (Layered Architecture)** prensipleriyle geliştirilmiştir. 
**Senaryo:** Bir kütüphanecinin, kitap envanterini yönetmesi, yeni üyeleri sisteme dahil etmesi ve kitapların ödünç süreçlerini (loan management) gerçek zamanlı bir veritabanı üzerinde takip etmesi kurgulanmıştır.

---

## ✨ Temel Özellikler
| Fonksiyon | Açıklama |
| :--- | :--- |
| **📦 Kitap Yönetimi** | Kitap ekleme, silme, güncelleme ve anlık stok listeleme. |
| **👥 Üye Takibi** | Öğrenci/Üye kaydı oluşturma ve profil yönetimi. |
| **🔄 Ödünç Sistemi** | Kitapların ödünç verilmesi ve iade alındığında durumunun otomatik güncellenmesi. |
| **💾 Veri Güvenliği** | SQLite ve Hibernate ile fiziksel veritabanı entegrasyonu. |

---

## 🏗️ Proje Yapısı (Modern Katmanlı Mimari)
Proje, `src/main/java` altında 4 ana katmana ayrılmıştır:

### 1️⃣ `app` (Presentation Layer)
* **`Main.java`**: Uygulamanın motorudur. Kullanıcı dostu konsol menüsünü ve uygulama mantığını yönetir.

### 2️⃣ `entity` (Domain Layer)
* **`Book.java`**, **`Student.java`**, **`Loan.java`**: Veritabanı tablolarının Hibernate (ORM) ile eşleştirildiği "Varlık" sınıflarıdır.

### 3️⃣ `dao` (Data Access Layer)
* **`BookDao.java`**, **`StudentDao.java`**, **`LoanDao.java`**: Veritabanı sorgularının (HQL) bulunduğu katmandır. İş mantığı ile veri erişimi burada birbirinden ayrılmıştır.

### 4️⃣ `util` (Infrastructure Layer)
* **`HibernateUtil.java`**: Veritabanı bağlantı konfigürasyonunu ve `SessionFactory` yönetimini sağlar.

---

## 📋 Kurulum ve Çalıştırma Talimatı

1.  **Arşivi Hazırlayın:** Size iletilen `.rar` veya `.zip` dosyasını sağ tıklayarak bir klasöre çıkartın.
2.  **Otomatik Yapılandırma:** Proje açıldığında Maven kütüphanelerinin (Hibernate, SQLite vb.) yüklenmesi için sağ alttaki yükleme çubuğunun bitmesini bekleyin. 

---
