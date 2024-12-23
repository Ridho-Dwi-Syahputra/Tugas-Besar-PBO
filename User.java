//Kelas abstrak untuk mendefinisikan atribut dan metode dasar pengguna dalam sistem.
 
public abstract class User {
    protected String username; // Atribut untuk username pengguna
    protected String password; // Atribut untuk password pengguna


     //  Konstruktor untuk menginisialisasi username dan password pengguna.
     //  username Username pengguna.
     //  password Password pengguna.
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
  
    //Metode abstrak untuk melihat laporan. Diimplementasikan oleh kelas turunan.
   
    public abstract void viewReports();
}
