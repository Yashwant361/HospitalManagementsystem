package HospitalManagementSystem;

import com.sun.source.tree.TryTree;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {

    private Connection connection;

    /**
     * :A constructor in Java is a special method that is used to initialize objects. The constructor is called when an object of a class is created.:
     **/
    public Doctor(Connection connection) {
        this.connection = connection;
    }


    public void viewDoctors() {
        String query = "select * from doctors";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("+------------+---------------------------+--------------------------+");
            System.out.println("| Doctor Id  | Name                      | Specialization           |");
            System.out.println("+------------+---------------------------+--------------------------+");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.printf("| %-10s | %-25s | %-24s |\n", id, name, specialization);
                System.out.println("+------------+---------------------------+---------------------------+|");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getDoctorById(int id) {
        String query = " SELECT * FROM doctors WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else
                return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
//
//    /**
//     * BOOK APPOINTMENTS
//     **/
//    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
//        System.out.print("Enter Patient Id: ");
//        int patientId = scanner.nextInt();
//        System.out.print("Enter Doctor Id: ");
//        int doctorId = scanner.nextInt();
//        System.out.print("Enter appointment date (YYYY-MM-DD");
//        String appointmentDate = scanner.next();
//        if (patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)) {
//            if (checkDoctorAvailabiltiy(doctorId, appointmentDate, connection)) {
//                String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_data) VALUES(?,?,?)";
//                try {
//                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
//                    preparedStatement.setInt(1, patientId);
//                    preparedStatement.setInt(2, doctorId);
//                    preparedStatement.setString(3, appointmentDate);
//                    int rowsAffected = preparedStatement.executeUpdate();
//                    if (rowsAffected > 0) {
//                        System.out.printf("Appointment Booked!");
//                    } else
//                        System.out.printf("Failed to Book Appointment");
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//
//            } else
//                System.out.print("Doctor not available on this date");
//
//        } else
//            System.out.print("Enter doctor or patient doesn't exits!!");
//
//    }
//
//    private static boolean checkDoctorAvailabiltiy(int doctorId, String appointmentDate, Connection connection) {
//        String query = " SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setInt(1, doctorId);
//            preparedStatement.setString(2, appointmentDate);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                int count = resultSet.getInt(1);
//                if (count == 0)
//                    return true;
//                else {
//                    return false;
//                }
//
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}


