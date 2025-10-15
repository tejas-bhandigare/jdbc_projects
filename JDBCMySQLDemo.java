import java.sql.*;
import java.util.Scanner;

public class JDBCMySQLDemo {

    // Database credentials
    static final String URL = "jdbc:mysql://localhost:3306/studentdb"; // Replace with your DB name
    static final String USER = "root"; // Replace with your MySQL username
    static final String PASSWORD = "Tejas@123"; // Replace with your MySQL password

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to MySQL
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected to MySQL database successfully!\n");

            while (true) {
                System.out.println("\n===== STUDENT DATABASE MENU =====");
                System.out.println("1. Insert Student");
                System.out.println("2. View All Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        insertStudent(con, sc);
                        break;
                    case 2:
                        viewStudents(con);
                        break;
                    case 3:
                        updateStudent(con, sc);
                        break;
                    case 4:
                        deleteStudent(con, sc);
                        break;
                    case 5:
                        System.out.println("👋 Exiting... Goodbye!");
                        con.close();
                        sc.close();
                        System.exit(0);
                    default:
                        System.out.println("❌ Invalid choice! Try again.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Insert new student
    public static void insertStudent(Connection con, Scanner sc) {
        try {
            System.out.print("Enter name: ");
            sc.nextLine(); // consume leftover newline
            String name = sc.nextLine();
            System.out.print("Enter age: ");
            int age = sc.nextInt();
            System.out.print("Enter city: ");
            sc.nextLine();
            String city = sc.nextLine();

            String query = "INSERT INTO students (name, age, city) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, city);
            ps.executeUpdate();

            System.out.println("✅ Student inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // View all students
    public static void viewStudents(Connection con) {
        try {
            String query = "SELECT * FROM students";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            System.out.println("\n Student Records:");
            System.out.println("----------------------------------------------------");
            System.out.printf("%-5s %-15s %-10s %-15s\n", "ID", "Name", "Age", "City");
            System.out.println("----------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-5d %-15s %-10d %-15s\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("city"));
            }
            System.out.println("----------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update student details
    public static void updateStudent(Connection con, Scanner sc) {
        try {
            System.out.print("Enter student ID to update: ");
            int id = sc.nextInt();
            System.out.print("Enter new city: ");
            sc.nextLine();
            String city = sc.nextLine();

            String query = "UPDATE students SET city = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, city);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("✅ Student updated successfully!");
            else
                System.out.println("⚠️ Student ID not found!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete student
    public static void deleteStudent(Connection con, Scanner sc) {
        try {
            System.out.print("Enter student ID to delete: ");
            int id = sc.nextInt();

            String query = "DELETE FROM students WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("🗑️ Student deleted successfully!");
            else
                System.out.println("⚠️ Student ID not found!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
