package HospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {

    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient() {
        System.out.println("Enter the Patient Name : ");
        String name = scanner.next();
        System.out.println("Enter Patient's Gender : ");
        String gender = scanner.next();

        try {
            String query = "INSERT INTO patients(name, gender) VALUES(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, gender);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patient added successfully");
            } else {
                System.out.println("Registration Failed -- Try again later -- ");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewPatient() {
        String query = "SELECT * FROM patients;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Patients: ");
            System.out.println("+------------+---------------------+-----------+------------++");
            System.out.println("| Patient id |  Name               |  Gender   |  Location  |");
            System.out.println("+------------+---------------------+-----------+------------++");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String gender = resultSet.getString("gender");
                System.out.printf("%-12s|%-16s|%-11s|\n", id, name, gender);
                System.out.println("+------------+---------------------+-----------+------------++");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int id) {
        String query = "SELECT * FROM patients WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
