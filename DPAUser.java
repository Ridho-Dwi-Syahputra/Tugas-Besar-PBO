/**
 * Kelas untuk pengguna dengan peran DPA.
 * Hanya memiliki hak akses untuk melihat laporan.
 */
public class DPAUser extends User {

    public DPAUser(String username, String password) {
        super(username, password); // Memanggil konstruktor superclass
    }

    //Metaode untuk melihat semua laporan.
     
    public void viewReports() {
    //    ReportManager.viewAllReports(); // Memanggil fungsi dari ReportManager
    }
}