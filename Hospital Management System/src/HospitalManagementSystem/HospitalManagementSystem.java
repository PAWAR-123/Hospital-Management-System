package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {
	private static final String url="jdbc:mysql://localhost:3306/hospital";
	private static final String username="root";
	private static  final String password="root";
	
	public static void main(String args[]) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
			
		}
		
		Scanner scanner =new Scanner(System.in);
		 try {
			 Connection connection=DriverManager.getConnection(url,username,password);
			 patient Patient=new patient(connection,scanner);
			 Doctor doctor =new Doctor(connection);
			 
			 while(true) {
				 System.out.println("HOSPITAL MANAGEMENTS SYSTEM");
				 System.out.println("1. add patients");
				 System.out.println("2. view patients");
				 System.out.println("3. view doctors");
				 System.out.println("4. Book Appointment");
				 System.out.println("5.Exit");
				 System.out.println("Enter your choice");
				 int choice=scanner.nextInt();
				 
				 switch(choice) {
				 case 1:
					 Patient.addPatient();
					 System.out.println();
					 break;
					 
				 case 2:
					 Patient.viewPatient();
					 System.out.println();
					 break;
					 
				 case 3:
					 doctor.viewDoctor();
					 System.out.println();
					 break;
				 case 4:
					 bookAppointment(Patient,doctor, connection,scanner);
					 System.out.println();
					 
					 
				 case 5:
					 return;
			     default:
						 System.out.println("Enter value choice ");
						 break;
					 
				 }
				 
			 }
			 
			 
			 
		 }catch(SQLException e) {
			 e.printStackTrace();
		 }
		
		
		
	}
	
	public static void bookAppointment( patient Patient ,Doctor doctor ,Connection connection,Scanner scanner) {
		System.out.println("Enter patient Id");
		int patientId=scanner.nextInt();
		System.out.println("Enter Doctor Id");
		int doctorId=scanner.nextInt();
		System.out.println("Enter appointmnt date (yyyy-MM-DD):");
		String appointmentDate=scanner.next();
		
		if(Patient.getPatientById(patientId)&&doctor.getDoctorById(doctorId)) {
			if(checkDoctorAvailability(doctorId, appointmentDate,connection)) {
				String appointmentQuery="insert into appointments(patient_id,doctor_id, appointment_date)Values(?,?,?)";
				try {
					PreparedStatement preparedStatement=connection.prepareStatement(appointmentQuery);
					preparedStatement.setInt(1, patientId);
					preparedStatement.setInt(2, doctorId);
					preparedStatement.setString(3, appointmentDate);
					int rowsAffected=preparedStatement.executeUpdate();
					if(rowsAffected>0) {
						System.out.println("Appointment Booked");
						
					}else {
						System.out.println("Failed to book Appointment");
					}
				}catch(SQLException e) {
					e.printStackTrace();
					
				}
				
			}else {
				System.out.println("Doctor not available on this date");
			}
			
		}else {
			
			System.out.println("Either doctor or patient doest exits ");
			
		}
	}
	



public  static boolean checkDoctorAvailability(int doctorId,String appointmentDate, Connection connection) {
	String query ="select count(*) from appointments where doctor_id=? AND appointment_date=?";
	try {
	PreparedStatement preparedStatement=connection.prepareStatement(query);
	preparedStatement.setInt(1, doctorId);
	preparedStatement.setString(2, appointmentDate);
	ResultSet resultSet=preparedStatement.executeQuery();
	if(resultSet.next()) {
		int count=resultSet.getInt(1);
		if(count==0) {
			return true;
		}else {
			return false;
		}
	}
	
	}catch(SQLException e) {
		e.printStackTrace();
	}
	return false;
	
}
}	

