import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class login{
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/mahasiswa";
    static final String USER = "root";
    static final String PASS = "";

    //    Menyiapkan objek yang diperlukan untuk mengelola database
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;

    static int pilih;
    static String user, olduser, pass, npm, oldnpm, nama_mhs, fakultas, prodi;
    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        dataMahasiswa data = new dataMahasiswa();

        System.out.println("Selamat datang");
        System.out.println("Silakan login terlebih dahulu, jika belum punya akun daftar terlebih dahulu");

        do {
            System.out.println("1. Daftar\n2. Masuk\n3. Tampilkan akun\n4. Hapus akun\n5. Ubah akun\n6. Keluar program");
            pilih = input.nextInt();
//            Create
            if(pilih == 1) {
                try {
                    input.nextLine();

                    System.out.print("Masukan username baru : ");
                    user = input.nextLine();
                    System.out.print("Masukan kata sandi baru : ");
                    pass = input.nextLine();

                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    stmt = conn.createStatement();
                    Statement statement1 = conn.createStatement();

                    String sql = "SELECT * FROM login";
                    rs = stmt.executeQuery(sql);
//                    Cek apakah username sudah ada
                    if(rs.next()) {
                        if(user.equals(rs.getString("username"))) {
                            System.out.println("Username sudah ada");
                        } else {
                            int rowsInserted = statement1.executeUpdate("INSERT INTO login VALUES ('"+user+"', '"+pass+"');");
                            if (rowsInserted > 0) {
                                System.out.println("Akun baru berhasil ditambahkan!");
                            }
                        }
                    }
                    stmt.close();
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if(pilih == 2 ) {
                try {
                    input.nextLine();

                    System.out.print("Masukan username : ");
                    user = input.nextLine();
                    System.out.print("Masukan kata sandi : ");
                    pass = input.nextLine();

//                    Register driver yang akan dipakai
                    Class.forName(JDBC_DRIVER);
//                    Buat koneksi ke database
                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
//                    Buat objek statement
                    stmt = conn.createStatement();
//                    Buat query ke database
                    String sql = "SELECT * FROM login WHERE username='"+user+"' AND password='"+pass+"'";
//                    Eksekusi query dan simpan hasilnya di obj ResultSet
                    rs = stmt.executeQuery(sql);
                    if(rs.next()) {
                        if(user.equals(rs.getString("username")) && pass.equals(rs.getString("password"))) {
                            data.dataMhs();
                        }
                    } else {
                        System.out.println("Username atau kata sandi salah!");
                    }
                    stmt.close();
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            Read
            else if(pilih == 3) {
                try {
                    String sql = "SELECT * FROM login";
                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    Statement statement1 = conn.createStatement();
                    ResultSet result = statement1.executeQuery(sql);

                    while (result.next()){
                        String username = result.getString(1);
                        String password = result.getString(2);

                        System.out.println("Username : "+String.format(username));
                        System.out.println("Password : "+String.format(password)+"\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            Delete
            else if(pilih == 4) {
                try {
                    input.nextLine();

                    System.out.print("Masukan username yang ingin dihapus : ");
                    user = input.nextLine();

                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    stmt = conn.createStatement();
                    Statement statement1 = conn.createStatement();

                    String sql = "SELECT * FROM login WHERE username='"+user+"'";
                    rs = stmt.executeQuery(sql);
                    if(rs.next()) {
                        int rowsDeleted = statement1.executeUpdate("DELETE FROM login WHERE username='"+user+"'");
                        if (rowsDeleted > 0) {
                            System.out.println("Akun berhasil dihapus!");
                        }
                    } else {
                        System.out.println("Username tidak ditemukan!");
                    }
                    stmt.close();
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            Update
            else if (pilih == 5) {
                try {
                    input.nextLine();

                    System.out.print("Masukan username yang ingin diubah : ");
                    olduser = input.nextLine();

                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    stmt = conn.createStatement();
                    Statement statement1 = conn.createStatement();

                    String sql = "SELECT * FROM login";
                    rs = stmt.executeQuery(sql);
                    if(rs.next()) {
                        System.out.print("Masukan username baru: ");
                        user = input.nextLine();
                        System.out.print("Masukan kata sandi baru : ");
                        pass = input.nextLine();
                        int rowsUpdated = statement1.executeUpdate("UPDATE login SET username='"+user+"', password='"+pass+"' WHERE username='"+olduser+"'");
                        if (rowsUpdated > 0) {
                            System.out.println("Akun berhasil diubah!");
                        }
                    } else {
                        System.out.println("Username tidak ditemukan");
                    }
                    stmt.close();
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (pilih == 6) {
                System.out.println("Program berakhir!");
                System.exit(0);
            } else {
                System.out.println("Pilihan tidak tersedia");
            }
        } while(pilih == 1 || pilih == 2 || pilih == 3 || pilih == 4 || pilih == 5 || pilih == 6);
    }
}


