import java.sql.*;
import java.util.Scanner;

public class Main {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/mahasiswa1";
    static final String USER = "root";
    static final String PASS = "";

    static  Connection connection;
    static  Statement statement;
    static ResultSet resultSet;
    public static void main(String[] args) {
        String npm, oldnpm, nama_mhs, fakultas, prodi;
        int pilih;
        Scanner input = new Scanner(System.in);

        System.out.println("=== Program Data Mahasiswa ===");
        do {
            System.out.println("1. Masukan data mahasiswa baru\n2. Tampilkan data mahasiswa\n3. Ubah data mahasiswa\n4. Hapus data mahasiswa\n5. Keluar program");
            pilih = input.nextInt();
            switch (pilih){
                case 1 :
                    try {
                        input.nextLine();

                        System.out.print("Masukan NPM mahasiswa baru : ");
                        npm = input.nextLine();
                        System.out.print("Masukan nama mahasiswa baru : ");
                        nama_mhs = input.nextLine();
                        System.out.print("Masukan fakultas mahasiswa baru : ");
                        fakultas = input.nextLine();
                        System.out.print("Masukan program studi mahasiswa baru : ");
                        prodi = input.nextLine();

                        Class.forName(JDBC_DRIVER);
                        connection = DriverManager.getConnection(DB_URL, USER, PASS);
                        statement = connection.createStatement();
                        Statement statement1 = connection.createStatement();

                        String sql = "SELECT * FROM data_mahasiswa WHERE npm='"+npm+"'";
                        resultSet = statement.executeQuery(sql);
                        if (resultSet.next()){
                            if (npm.equals(resultSet.getString("npm"))) {
                                System.out.println("--------------------------------------------------------------------------------------");
                                System.out.println("Mahasiswa dengan NPM "+npm+" sudah ada");
                                System.out.println("--------------------------------------------------------------------------------------");
                            } else {
                                int rowInserted = statement1.executeUpdate("INSERT INTO data_mahasiswa VALUES ('"+npm+"', '"+nama_mhs+"', '"+fakultas+"', '"+prodi+"');");
                                if (rowInserted > 0) {
                                    System.out.println("--------------------------------------------------------------------------------------");
                                    System.out.println("Data mahasiswa baru berhasil ditambahkan!");
                                    System.out.println("--------------------------------------------------------------------------------------");
                                }
                            }
                        }
                        statement.close();
                        connection.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2 :
                    try {
                        System.out.printf("--------------------------------------------------------------------------------------------------------------------------------%n");
                        System.out.printf(" \t\t\t\t\t\t\t\t\t\t\t\t\tDaftar Mahasiswa%n");

                        System.out.printf("+-----------------+------------------------------------------+--------------------------------+--------------------------------+%n");
                        System.out.printf("| %-15s | %-40s | %-30s | %-30s |%n", "NPM", "Nama Mahasiswa", "Fakultas", "Program Studi");
                        System.out.printf("+-----------------+------------------------------------------+--------------------------------+--------------------------------+%n");

                        String sql = "SELECT * FROM data_mahasiswa";
                        connection = DriverManager.getConnection(DB_URL, USER, PASS);
                        Statement statement1 = connection.createStatement();
                        ResultSet result = statement1.executeQuery(sql);

                        while (result.next()) {
                            String npm_mhs = result.getString(1);
                            String nama_mahasiswa = result.getString(2);
                            String fakultas_mhs = result.getString(3);
                            String prodi_mhs = result.getString(4);

//                            System.out.print(String.format(npm_mhs));
//                            System.out.print(String.format(nama_mahasiswa));
//                            System.out.print(String.format(fakultas_mhs));
//                            System.out.print(String.format(prodi_mhs));
                            System.out.printf("| %-15s | %-40s | %-30s | %-30s |%n", String.format(npm_mhs), String.format(nama_mahasiswa),  String.format(fakultas_mhs), String.format(prodi_mhs));
                        }
                        System.out.printf("+-----------------+------------------------------------------+--------------------------------+--------------------------------+%n%n");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 3 :
                    try {
                        input.nextLine();

                        System.out.print("Masukan NPM mahasiswa yang ada : ");
                        oldnpm = input.nextLine();

                        connection = DriverManager.getConnection(DB_URL, USER, PASS);
                        statement = connection.createStatement();
                        Statement statement1 = connection.createStatement();

                        String sql = "SELECT * FROM data_mahasiswa WHERE npm='"+oldnpm+"'";
                        resultSet = statement.executeQuery(sql);
                        if (resultSet.next()){
                            System.out.print("Masukan NPM mahasiswa yang baru : ");
                            npm = input.nextLine();
                            System.out.print("Masukan nama mahasiswa yang baru : ");
                            nama_mhs = input.nextLine();
                            System.out.print("Masukan fakultas mahasiswa yang baru : ");
                            fakultas = input.nextLine();
                            System.out.print("Masukan program studi mahasiswa yang baru : ");
                            prodi = input.nextLine();

                            int rowsUpdated = statement1.executeUpdate("UPDATE data_mahasiswa SET npm='"+npm+"', nama_mahasiswa='"+nama_mhs+"', fakultas='"+fakultas+"', program_studi='"+prodi+"' WHERE npm='"+oldnpm+"'");
                            if (rowsUpdated > 0) {
                                System.out.println("--------------------------------------------------------------------------------------");
                                System.out.println("Data mahasiswa dengan NPM "+oldnpm+" berhasil diubah!");
                                System.out.println("--------------------------------------------------------------------------------------");
                            }
                        } else {
                            System.out.println("--------------------------------------------------------------------------------------");
                            System.out.println("Mahasiswa dengan NPM "+oldnpm+" tidak ditemukan!");
                            System.out.println("--------------------------------------------------------------------------------------");
                        }
                        statement.close();
                        connection.close();


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4 :
                    try {
                        input.nextLine();

                        System.out.print("Masukan NPM yang ingin dihapus : ");
                        npm = input.nextLine();

                        connection = DriverManager.getConnection(DB_URL, USER, PASS);
                        statement = connection.createStatement();
                        Statement statement1 = connection.createStatement();

                        String sql = "SELECT * FROM data_mahasiswa WHERE npm='"+npm+"'";
                        resultSet = statement.executeQuery(sql);

                        if (resultSet.next()) {
                            int rowsDeleted = statement1.executeUpdate("DELETE FROM data_mahasiswa WHERE npm='"+npm+"'");
                            if (rowsDeleted > 0) {
                                System.out.println("--------------------------------------------------------------------------------------");
                                System.out.println("Mahasiswa dengan NPM "+npm+" berhasil dihapus!");
                                System.out.println("--------------------------------------------------------------------------------------");
                            }
                        } else {
                            System.out.println("--------------------------------------------------------------------------------------");
                            System.out.println("Mahasiswa dengan NPM "+npm+" tidak ditemukan!");
                            System.out.println("--------------------------------------------------------------------------------------");
                        }

                        statement.close();
                        connection.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 5 :
                    System.out.println("Program berakhir!");
                    break;
                default :
                    System.out.println("Pilihan tidak tersedia");
                    break;
            }
        } while(pilih == 1 || pilih == 2 || pilih == 3 || pilih == 4);
    }
}