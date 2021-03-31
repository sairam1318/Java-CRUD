//jdbc connection to mysql
import java.sql.*;
class mysqlJdbcTrial{
	public static void main(String[] args) {
		try{
			// System.out.println("Sai ram");
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection mycon = DriverManager.getConnection("jdbc:mysql://165.22.14.77/dbSairam", "Tharun", "pwdTharun");
			Statement myStmnt = mycon.createStatement();
			ResultSet myrs = myStmnt.executeQuery("Select * from ITEM");
			while(myrs.next()){
				System.out.println(myrs.getString(1));
			}
			mycon.close();

		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}