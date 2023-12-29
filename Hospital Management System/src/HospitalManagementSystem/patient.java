package HospitalManagementSystem;
import java.sql.*;
import java.util.*;
public class patient {
	
	private Connection connection;
	
	private Scanner scanner;
	public patient(Connection connection ,Scanner scanner) {
		this.connection=connection;
		this.scanner=scanner;
		
		
		
		
	}
	public void addPatient() {
		System.out.println("Enter patient name");
		String  name=scanner.next();
		System.out.println("Enter patient age ");
		int age=scanner.nextInt();
		System.out.println("Enter patient Gender");
		String gender=scanner.next();
		
		
		
		try {
			String query ="insert into  patients (name, age, gender) values(?,?,?)";
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, age);
			preparedStatement.setString(3, gender);
			int affectedRows=preparedStatement.executeUpdate();
			if(affectedRows>0) {
				System.out.println("patient added successfully");
			}else {
				System.out.println("failed to add patients");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void viewPatient() {
		String query ="SELECT  * FROM patients";
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			ResultSet resultSet=preparedStatement.executeQuery();
			 System.out.println("patients ");
			 System.out.println("+---------------+--------------+---------+----------+");
			 System.out.println("| patient Id    | name         |  age    | gender    ");
			 System.out.println("+---------------+--------------+---------+----------+");
			 while(resultSet.next()) {
				 int id=resultSet.getInt("id");
				 String name=resultSet.getString("name");
				 int age=resultSet.getInt("age");
				 String gender=resultSet.getString("gender");
				 System.out.printf("|%-12s|%-14s|%-10s|%-12s|\n", id, name, age, gender);
				 System.out.println("+---------------+--------------+---------+----------+");
				 
				 
			 }
			
			
		
			
		}catch(SQLException e) {
			e.printStackTrace();
	}
	

}
	public boolean getPatientById(int id) {
		String query ="SELECT * FROM patients  WHERE id =?";
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			 preparedStatement.setInt(1, id);
			 ResultSet resultSet= preparedStatement.executeQuery();
			 if(resultSet.next()) {
				 return true;
			 }else {
				 return false;
			 }
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
		
		
	}
}
