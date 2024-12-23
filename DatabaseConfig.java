import java.sql.Connection; // Untuk koneksi JDBC
import java.sql.DriverManager; // Untuk mengelola koneksi database
import java.sql.SQLException; // Untuk menangani kesalahan SQL

public class DatabaseConfig {
    // URL untuk menghubungkan ke database Monitoring_HMSI
    private static final String URL = "jdbc:postgresql://localhost:5432/Monitoring_HMSI";

    // Username PostgreSQL Anda
    private static final String USER = "postgres"; 

    // Password PostgreSQL Anda
    private static final String PASSWORD = "09122004Aa"; 

    /**
     * Metode untuk mendapatkan koneksi ke database.
     * @return Connection objek koneksi
     * @throws SQLException jika ada kesalahan koneksi
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Memuat driver PostgreSQL
            Class.forName("org.postgresql.Driver");
            // Mengembalikan objek koneksi
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            // Jika driver PostgreSQL tidak ditemukan
            throw new SQLException("PostgreSQL Driver tidak ditemukan.", e);
        }
    }

    /**
     * Metode utama untuk menguji koneksi database.
     */
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            // Jika koneksi berhasil
            if (conn != null) {
                System.out.println("Koneksi berhasil!");
            } else {
                System.out.println("Koneksi gagal.");
            }
        } catch (SQLException e) {
            // Menangani kesalahan koneksi
            e.printStackTrace();
        }
    }
}