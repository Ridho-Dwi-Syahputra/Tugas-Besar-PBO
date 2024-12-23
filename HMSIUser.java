import java.util.ArrayList;
import java.util.List;

/**
 * Kelas yang mewakili pengguna HMSI.
 */
public class HMSIUser extends User {
    private List<Report> reports; // Daftar laporan yang dibuat oleh pengguna HMSI

    /**
     * Konstruktor untuk membuat pengguna HMSI.
     * @param username Username pengguna.
     * @param password Password pengguna.
     */
    public HMSIUser(String username, String password) {
        super(username, password); // Memanggil konstruktor kelas induk
        this.reports = new ArrayList<>(); // Inisialisasi daftar laporan
    }

    /**
     * Menambahkan laporan baru ke dalam sistem.
     * @param id ID laporan.
     * @param title Judul laporan.
     * @param content Isi laporan.
     */
    public void createReport(int id, String title, String content) {
        // Memastikan ID laporan tidak duplikat
        for (Report report : reports) {
            if (report.getId() == id) {
                System.out.println("Laporan dengan ID tersebut sudah ada.");
                return;
            }
        }

        // Membuat laporan baru dan menambahkannya ke daftar
        reports.add(new Report(id, title, content));
        System.out.println("Laporan berhasil ditambahkan.");
    }

    /**
     * Menghapus laporan berdasarkan ID.
     * @param id ID laporan yang ingin dihapus.
     * @return true jika laporan berhasil dihapus, false jika tidak ditemukan.
     */
    public boolean deleteReport(int id) {
        for (Report report : reports) { // Iterasi melalui daftar laporan
            if (report.getId() == id) {
                reports.remove(report); // Menghapus laporan jika ditemukan
                return true; // Mengembalikan true jika laporan berhasil dihapus
            }
        }
        return false; // Mengembalikan false jika laporan tidak ditemukan
    }

    /**
     * Menampilkan semua laporan yang telah dibuat oleh pengguna HMSI.
     */
    public void viewReports() {
        if (reports.isEmpty()) { // Jika tidak ada laporan
            System.out.println("Belum ada laporan yang dimasukkan.");
            return;
        }

        // Menampilkan semua laporan
        System.out.println("\n--- Daftar Laporan ---");
        for (Report report : reports) {
            System.out.println("ID: " + report.getId());
            System.out.println("Judul: " + report.getTitle());
            System.out.println("Isi: " + report.getContent());
            System.out.println("----------------------");
        }
    }
}

/**
 * Kelas untuk merepresentasikan laporan.
 */
class Report {
    private int id; // ID laporan
    private String title; // Judul laporan
    private String content; // Isi laporan

    /**
     * Konstruktor untuk membuat laporan.
     * @param id ID laporan.
     * @param title Judul laporan.
     * @param content Isi laporan.
     */
    public Report(int id, String title, String content) {
        this.id = id; // Menginisialisasi ID laporan
        this.title = title; // Menginisialisasi judul laporan
        this.content = content; // Menginisialisasi isi laporan
    }

    /**
     * Mengembalikan ID laporan.
     * @return ID laporan.
     */
    public int getId() {
        return id;
    }

    /**
     * Mengembalikan judul laporan.
     * @return Judul laporan.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Mengembalikan isi laporan.
     * @return Isi laporan.
     */
    public String getContent() {
        return content;
    }
}