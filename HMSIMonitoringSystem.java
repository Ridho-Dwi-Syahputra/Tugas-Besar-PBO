import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;


// Kelas utama untuk menjalankan sistem monitoring HMSI.
public class HMSIMonitoringSystem {

    private User currentUser; // Variabel untuk menyimpan pengguna yang sedang login

    public static void main(String[] args) {
        HMSIMonitoringSystem system = new HMSIMonitoringSystem(); // Membuat instance sistem
        system.run(); // Menjalankan sistem
        
    }

    // Metode utama untuk menjalankan sistem.
    public void run() {
        Scanner scanner = new Scanner(System.in); // Scanner untuk membaca input pengguna
        boolean running = true; // Variabel kontrol untuk loop utama
        

        while (running) {
            if (currentUser == null) { // Jika belum login, hanya tampilkan opsi Login dan Keluar
                System.out.println("\n--- Sistem Monitoring HMSI ---");
                System.out.println("1. Login");
                System.out.println("2. Keluar");
                System.out.print("Pilih opsi: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Membersihkan buffer

                switch (choice) {
                    case 1 -> login(scanner); // Memanggil metode login
                    case 2 -> {
                        System.out.println("Keluar dari sistem. Sampai jumpa!");
                        running = false; // Menghentikan loop utama
                    }
                    default -> System.out.println("Pilihan tidak valid. Coba lagi.");
                }
            } else { // Jika sudah login, hanya tampilkan opsi Logout
                System.out.println("\n--- Sistem Monitoring HMSI ---");
                System.out.println("1. Logout");
                System.out.print("Pilih opsi: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Membersihkan buffer

                if (choice == 1) {
                    logout(); // Memanggil metode logout
                } else {
                    System.out.println("Pilihan tidak valid. Coba lagi.");
                }
            }
        }

        scanner.close(); // Menutup scanner
    }

    // Metode untuk login pengguna.
    private void login(Scanner scanner) {
        if (currentUser != null) { // Jika sudah login, tidak perlu login lagi
            System.out.println("Anda sudah login sebagai " + currentUser.username);
            return;
        }

        System.out.print("Masukkan username: ");
        String username = scanner.nextLine(); // Membaca username dari pengguna

        System.out.print("Masukkan password: ");
        String password = scanner.nextLine(); // Membaca password dari pengguna

        // Logika login untuk username dan password khusus HMSI dan DPA
        if (username.equals("HMSI") && password.equals("123")) {
            currentUser = new HMSIUser(username, password); // Membuat objek HMSIUser
            System.out.println("Login berhasil sebagai HMSI.");
            showHMSIMenu(scanner); // Menampilkan menu untuk HMSI
        } else if (username.equals("DPA") && password.equals("abc")) {
            currentUser = new DPAUser(username, password); // Membuat objek DPAUser
            System.out.println("Login berhasil sebagai DPA.");
            showDPAMenu(scanner); // Menampilkan menu untuk DPA
        } else {
            System.out.println("Login gagal! Username atau password salah.");
        }
    }

    // Metode untuk logout pengguna.
    private void logout() {
        if (currentUser == null) { // Jika belum login, tidak bisa logout
            System.out.println("Tidak ada pengguna yang sedang login.");
            return;
        }

        System.out.println("Logout berhasil. Sampai jumpa, " + currentUser.username + "!");
        currentUser = null; // Menghapus informasi pengguna yang sedang login
    }

    // Menampilkan menu untuk pengguna HMSI.
    private void showHMSIMenu(Scanner scanner) {
        //HMSIUser HMSIUser = (HMSIUser) currentUser; // Casting pengguna ke HMSIUser
        boolean inMenu = true;
        ReportManager view = new ReportManager();

        

        while (inMenu) {
            System.out.println("\n--- Menu HMSI ---");
            System.out.println("1. Lihat laporan");
            System.out.println("2. Tambah laporan");
            System.out.println("3. Hapus laporan");
            System.out.println("4. Kembali");
            System.out.print("Pilih opsi: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Membersihkan buffer

            switch (choice) {
                case 1 -> view.viewAllReports();
                case 2 -> addReport(scanner);
                case 3 -> deleteReport(scanner);
                case 4 -> inMenu = false; // Kembali ke menu sebelumnya
                default -> System.out.println("Pilihan tidak valid. Coba lagi.");
            }
        }
    }

    // Menampilkan menu untuk pengguna DPA
    private void showDPAMenu(Scanner scanner) {
    boolean inMenu = true;
    ReportManager view = new ReportManager();

        while (inMenu) {
            System.out.println("\n--- Menu DPA ---");
            System.out.println("1. Lihat laporan");
            System.out.println("2. Update status laporan");
            System.out.println("3. Kembali");
            System.out.print("Pilih opsi: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Membersihkan buffer

            switch (choice) {
                case 1:
                    view.viewAllReports();
                    break;
                case 2:
                    // Menambahkan input ID dan status
                    System.out.print("Masukkan ID laporan yang ingin diupdate: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Membersihkan buffer

                    System.out.print("Masukkan status baru (Approved, Rejected, Pending): ");
                    String status = scanner.nextLine();

                    updateReportStatus(id, status);
                    break;
                case 3:
                    inMenu = false; // Kembali ke menu utama
                    break;
                default:
                System.out.println("Pilihan tidak valid. Coba lagi.");
            }
        }
    }
    //Metode untuk update status
    public static void updateReportStatus(int id, String status) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            // Query SQL untuk memperbarui status laporan
            String sql = "UPDATE reports SET status = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, status); // Mengisi status baru
            stmt.setInt(2, id);       // Mengisi ID laporan
    
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Status laporan dengan ID " + id + " berhasil diperbarui menjadi: " + status);
            } else {
                System.out.println("Laporan dengan ID " + id + " tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan saat memperbarui status laporan: " + e.getMessage());
        }
    }
    // Metode untuk melihat laporan.
    public static void viewAllReports() {
    
}
    // Metode untuk menambahkan laporan.
    private void addReport(Scanner scanner) {
        System.out.print("Masukkan ID laporan: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Membersihkan buffer
        System.out.print("Masukkan judul laporan: ");
        String title = scanner.nextLine();
        System.out.print("Masukkan isi laporan: ");
        String content = scanner.nextLine();
        ReportManager.addCustomDateReport(id, title, content );
    }

    // Metode untuk menghapus laporan.
    private void deleteReport(Scanner scanner) {
        System.out.print("Masukkan ID laporan yang ingin dihapus: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Membersihkan buffer
    
        try (Connection conn = DatabaseConfig.getConnection()) {
            // Query SQL untuk menghapus laporan berdasarkan ID
            String sql = "DELETE FROM reports WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id); // Mengisi parameter ID
            
            int rowsAffected = stmt.executeUpdate(); // Menjalankan query
            if (rowsAffected > 0) {
                System.out.println("Laporan dengan ID " + id + " berhasil dihapus.");
            } else {
                System.out.println("Laporan dengan ID tersebut tidak ditemukan di database.");
            }
        } catch (SQLException e) {
            // Menangani kesalahan SQL
            System.out.println("Terjadi kesalahan saat menghapus laporan: " + e.getMessage());
        }
    }
}
// Kelas untuk merepresentasikan laporan.
class Report {
    private int id;
    private String title;
    private String content;
    private LocalDate uploadDate; // Atribut untuk menyimpan tanggal laporan

    // Konstruktor
    public Report(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.uploadDate = LocalDate.now(); // Inisialisasi tanggal laporan dimasukkan
    }

    public int getId() {
        return id;
    }

    public String getFormattedDate() {
        // Format tanggal menjadi dd-MM-yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return uploadDate.format(formatter);
    }

    @Override
    public String toString() {
        return "ID: " + id + "\nJudul: " + title + "\nTanggal: " + getFormattedDate() + "\nIsi: " + content;
    }
}

// Kelas User.
abstract class User {
    protected String username;
    protected String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

// Kelas HMSIUser.
class HMSIUser extends User {
    public HMSIUser(String username, String password) {
        super(username, password);
    }
}

// Kelas DPAUser.
class DPAUser extends User {
    public DPAUser(String username, String password) {
        super(username, password);
    }
}