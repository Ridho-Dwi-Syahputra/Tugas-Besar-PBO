import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReportManager implements ReportOperations {
    
    // Metode untuk menambahkan laporan baru
    public static void addCustomDateReport(int id, String title, String content) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String sql = "INSERT INTO reports (id, judul, isi) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, title);
            stmt.setString(3, content);
            stmt.executeUpdate();
            System.out.println("Laporan berhasil ditambahkan.");
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }

    // Metode untuk melihat semua laporan
    public void viewAllReports() {
        viewReports(); // Memanggil fungsi viewAllReports yang sudah ada
    }
    // Implementasi metode viewReports dari interface
    //@Override
    public void viewReports() {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPilih pengurutan laporan:");
        System.out.println("1. Urutkan berdasarkan ID");
        System.out.println("2. Urutkan berdasarkan Judul");
        System.out.print("Pilih opsi: ");
        int sortOption = scanner.nextInt();
        scanner.nextLine(); // Membersihkan buffer
    
        String sql = "SELECT * FROM reports";
    
        // Menambahkan klausa ORDER BY berdasarkan pilihan pengguna
        switch (sortOption) {
            case 1:
                sql += " ORDER BY id";  // Urutkan berdasarkan ID
                break;
            case 2:
                sql += " ORDER BY judul";  // Urutkan berdasarkan Judul
                break;
            default:
                System.out.println("Pilihan tidak valid. Menggunakan urutan default (ID).");
                sql += " ORDER BY id";  // Urutkan berdasarkan ID secara default
                break;
        }
    
        // Mengambil data laporan dari database
        try (Connection conn = DatabaseConfig.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
    
            List<Report> reportList = new ArrayList<>();
            int approvedCount = 0, rejectedCount = 0, pendingCount = 0;
    
            while (rs.next()) {
                // Ambil data dari database
                int id = rs.getInt("id");
                String title = rs.getString("judul");
                String content = rs.getString("isi");
                String status = rs.getString("status");
                Date uploadDate = rs.getDate("tanggal_unggah");
    
                // Hitung statistik berdasarkan status
                switch (status) {
                    case "Approved":
                        approvedCount++;
                        break;
                    case "Rejected":
                        rejectedCount++;
                        break;
                    case "Pending":
                        pendingCount++;
                        break;
                }
    
                // Manipulasi String
                String formattedTitle = title.toUpperCase();
                String shortenedContent = content.length() > 50 ? content.substring(0, 50) + "..." : content;
    
                // Format tanggal
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
                String formattedDate = uploadDate.toLocalDate().format(formatter);
    
                // Simpan laporan ke koleksi
                reportList.add(new Report(id, formattedTitle, shortenedContent));
                System.out.println("ID: " + id + ", Judul: " + formattedTitle + ", Tanggal: " + formattedDate + ", Isi: " + shortenedContent);
            }
    
            // Tampilkan statistik
            System.out.println("\nStatistik Laporan:");
            System.out.println("Total laporan: " + reportList.size());
            System.out.println("Approved: " + approvedCount);
            System.out.println("Rejected: " + rejectedCount);
            System.out.println("Pending: " + pendingCount);
    
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }

    // Metode untuk update status khusus
    public static void updateReportStatus(int id, String status) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String sql = "UPDATE reports SET status = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setInt(2, id);

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
}