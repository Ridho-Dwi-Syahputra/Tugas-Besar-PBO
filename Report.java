import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Report {
    private int id;
    private String title;
    private String content;
    private LocalDateTime uploadDate; // Atribut untuk tanggal laporan

    // Konstruktor
    public Report(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.uploadDate = LocalDateTime.now(); // Inisialisasi waktu laporan dibuat
    }

    public int getId() {
        return id;
    }

    public String getFormattedDate() {
        // Format tanggal ke dd-MM-yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return uploadDate.format(formatter);
    }

    @Override
    public String toString() {
        return "ID: " + id + "\nJudul: " + title + "\nTanggal: " + getFormattedDate() + "\nIsi: " + content;
    }
}
