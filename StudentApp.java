
import java.sql.*;
import java.util.Scanner;

public class StudentApp {

    static final String URL = "jdbc:mysql://localhost:3306/studentdb";
    static final String USER = "root";
    static final String PASS = "Your password";

    static Scanner sc = new Scanner(System.in);

    // 1. Add Student (with duplicate check)
    static void addStudent() throws Exception {

        Connection con = DriverManager.getConnection(URL, USER, PASS);

        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        // check duplicate
        String checkQuery = "SELECT * FROM student WHERE id=?";
        PreparedStatement checkPs = con.prepareStatement(checkQuery);
        checkPs.setInt(1, id);

        ResultSet rs = checkPs.executeQuery();
        if (rs.next()) {
            System.out.println("Student already exists ❌");
            con.close();
            return;
        }

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Marks: ");
        double marks = sc.nextDouble();

        // validation
        if (marks < 0 || marks > 100) {
            System.out.println("Invalid marks");
            con.close();
            return;
        }

        String query = "INSERT INTO student VALUES (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setDouble(3, marks);

        ps.executeUpdate();

        System.out.println("Student Added Successfully");

        con.close();
    }

    // 2. View Students
    static void viewStudents() throws Exception {

        Connection con = DriverManager.getConnection(URL, USER, PASS);

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM student");

        System.out.println("\n-----------------------------------");
        System.out.println("ID\tNAME\t\tMARKS");
        System.out.println("-----------------------------------");

        while (rs.next()) {
            System.out.println(rs.getInt("id") + "\t"
                    + rs.getString("name") + "\t\t"
                    + rs.getDouble("marks"));
        }

        con.close();
    }

    // 3. Update Marks
    static void updateMarks() throws Exception {

        Connection con = DriverManager.getConnection(URL, USER, PASS);

        System.out.print("Enter ID: ");
        int id = sc.nextInt();

        System.out.print("Enter New Marks: ");
        double marks = sc.nextDouble();

        if (marks < 0 || marks > 100) {
            System.out.println("Invalid marks");
            con.close();
            return;
        }

        String query = "UPDATE student SET marks=? WHERE id=?";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setDouble(1, marks);
        ps.setInt(2, id);

        int result = ps.executeUpdate();

        if (result > 0) {
            System.out.println("Updated Successfully");
        } else {
            System.out.println("Student Not Found");
        }

        con.close();
    }

    // 4. Delete Student
    static void deleteStudent() throws Exception {

        Connection con = DriverManager.getConnection(URL, USER, PASS);

        System.out.print("Enter ID: ");
        int id = sc.nextInt();

        String query = "DELETE FROM student WHERE id=?";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, id);

        int result = ps.executeUpdate();

        if (result > 0) {
            System.out.println("Deleted Successfully");
        } else {
            System.out.println("Student Not Found");
        }

        con.close();
    }

    // 5. Search Student
    static void searchStudent() throws Exception {

        Connection con = DriverManager.getConnection(URL, USER, PASS);

        System.out.print("Enter ID: ");
        int id = sc.nextInt();

        String query = "SELECT * FROM student WHERE id=?";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            System.out.println("\nStudent Found:");
            System.out.println("ID: " + rs.getInt("id"));
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Marks: " + rs.getDouble("marks"));
        } else {
            System.out.println("Student Not Found");
        }

        con.close();
    }

    // 6. Show Topper
    static void showTopper() throws Exception {

        Connection con = DriverManager.getConnection(URL, USER, PASS);

        String query = "SELECT * FROM student WHERE marks = (SELECT MAX(marks) FROM student)";
        Statement st = con.createStatement();

        ResultSet rs = st.executeQuery(query);

        boolean found = false;

        System.out.println("\nTopper(s):");

        while (rs.next()) {
            found = true;

            System.out.println("ID: " + rs.getInt("id"));
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Marks: " + rs.getDouble("marks"));
            System.out.println("----------------------");
        }

        if (!found) {
            System.out.println("No Data");
        }

        con.close();
    }

    // 7. Total Students
    static void totalStudents() throws Exception {

        Connection con = DriverManager.getConnection(URL, USER, PASS);

        String query = "SELECT COUNT(*) AS total FROM student";
        Statement st = con.createStatement();

        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            int count = rs.getInt("total");
            System.out.println("Total Students: " + count);
        } else {
            System.out.println("Error fetching data");
        }

        con.close();
    }

    // MAIN MENU
    public static void main(String[] args) throws Exception {

        while (true) {

            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Marks");
            System.out.println("4. Delete Student");
            System.out.println("5. Search Student");
            System.out.println("6. Show Topper");
            System.out.println("7. Total Students");
            System.out.println("8. Exit");
            System.out.print("Choose option: ");

            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    updateMarks();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    searchStudent();
                    break;
                case 6:
                    showTopper();
                    break;
                case 7:
                    totalStudents();
                    break;
                case 8:
                    System.out.println("Thank you");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
