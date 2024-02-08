package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "#yashwant123";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url,username,password);
            Patient patient = new Patient(connection,scanner);
            Doctor doctor = new Doctor(connection);
            while (true){
                System.out.print("HOSPITAL MANAGEMENT SYSTEM\n");
                System.out.print("1. Add Patients\n");
                System.out.print("2. View Patients\n");
                System.out.print("3. View Doctors\n");
                System.out.print("4. Book Appointment\n");
                System.out.print("5. EXIT\n");
                System.out.print("Enter your choice\n");
                int choice = scanner.nextInt();

                switch (choice){
                    case 1:
                        // add patients
                        patient.addPatient();
                        System.out.println(); break;
                    case 2:
                        // view patients
                        patient.viewPatients();
                        System.out.println(); break;
                    case 3:
                        // view doctors
                        doctor.viewDoctors();
                        System.out.println(); break;
                    case 4:
                        // Book Appointments
                        bookAppointment(patient, doctor, connection, scanner);
                        System.out.println(); break;
                    case 5:
                        return;
                    default:
                        System.out.print("Enter Valid choice"); break;


                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    /**
     * BOOK APPOINTMENTS
     **/
    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner){
        System.out.print("Enter Patient Id: ");
        int patientId = scanner.nextInt();
        System.out.print("Enter Doctor Id: ");
        int doctorId = scanner.nextInt();
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String appointmentDate = scanner.next();
        if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)){
            if(checkDoctorAvailability(doctorId, appointmentDate, connection)){
                String appointmentQuery = "INSERT INTO appointment(patient_id, doctor_id, appointment_date) VALUES(?, ?, ?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1, patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3, appointmentDate);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if(rowsAffected>0){
                        System.out.println("Appointment Booked!");
                    }else{
                        System.out.println("Failed to Book Appointment!");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }else{
                System.out.println("Doctor not available on this date!!");
            }
        }else{
            System.out.println("Either doctor or patient doesn't exist!!!");
        }
    }
    public static boolean checkDoctorAvailability(int doctor_Id, String appointmentDate, Connection connection){
        String query = "SELECT COUNT(*) FROM  appointment WHERE doctor_id = ? AND appointment_date = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctor_Id);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt(1);
                if(count==0){
                    return true;
                }else{
                    return false;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
