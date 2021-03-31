//CRUD using java mysqlite
import java.util.Scanner;
import java.sql.*;

class mysqlCrud{
	// static connection myCon;
	static Connection myCon;
	
	mysqlCrud(){
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			myCon = DriverManager.getConnection("jdbc:mysql://165.22.14.77/dbSairam", "Tharun", "pwdTharun");
		}catch(Exception e){
			System.out.println(e);
		}
	}
	public static void create(){
		mysqlCrud myObj = new mysqlCrud();
		try{
			PreparedStatement prepStatement = myCon.prepareStatement("INSERT INTO FRAMEWORK VALUES(?, ?, ?, ?)");
			String accountNumber, accountName, ifscCode, status;
			System.out.println("Enter the account number: ");
			Scanner sc = new Scanner(System.in);
			accountNumber = sc.next();
			System.out.println("Enter the account holder's name: ");
			accountName = sc.next();
			System.out.println("Enter the ifsc code: ");
			ifscCode = sc.next();

			prepStatement.setString(1, accountNumber);
			prepStatement.setString(2, accountName);
			prepStatement.setString(3, ifscCode);
			prepStatement.setInt(4, 1);
			int countOfRows = prepStatement.executeUpdate();
			System.out.println(countOfRows + "row(s) is affected.");
		}catch(Exception e){
			System.out.println(e);
		}
		
	}
	public static void read(){
		mysqlCrud myObj = new mysqlCrud();
		try{
			Statement myStatement = myCon.createStatement();
			ResultSet myrs = myStatement.executeQuery("Select * from FRAMEWORK");
			while(myrs.next()){
				System.out.println(myrs.getString(1) + " " + myrs.getString(2) + " " + myrs.getString(3) + " " + myrs.getString(4));
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}

	public static void update(){
		mysqlCrud myObj = new mysqlCrud();
		try{
			int choice, countOfRows;
			String accountNumber;
			System.out.print("Enter the account number to update the account: ");
			Scanner sc = new Scanner(System.in);
			accountNumber = sc.next();
			System.out.println("1.Account Name \n2. IFSC Code\nChose your choice to update: ");
		choice = sc.nextInt();
			switch(choice){
				case 1: 
					String updatedName;
					System.out.println("Enter the updated Name: ");
					updatedName = sc.next();
					PreparedStatement prepStatement = myCon.prepareStatement("UPDATE FRAMEWORK SET ACC_NAME = ? WHERE ACC_NUM = ?");
					prepStatement.setString(1, updatedName);
					prepStatement.setString(2, accountNumber);
					countOfRows = prepStatement.executeUpdate();
					System.out.println(countOfRows + "row(s) is affected.");
					break;
				case 2:
					String updatedIfsc;
					System.out.println("Enter the updated IFSC code: ");
					updatedIfsc = sc.next();
					prepStatement = myCon.prepareStatement("UPDATE FRAMEWORK SET IFSC_CODE = ? WHERE ACC_NUM = ?");
					prepStatement.setString(1, updatedIfsc);
					prepStatement.setString(2, accountNumber);
					countOfRows = prepStatement.executeUpdate();
					System.out.println(countOfRows + "row(s) is affected.");
					break;
				default:
					System.out.println("Wrong choice");
					break;

			}			

		}catch(Exception e){
			System.out.println(e);
		}

	}
	public static void delete(){
		mysqlCrud myObj = new mysqlCrud();
		try{
			System.out.println("Enter the account number to delete: ");
			Scanner sc = new Scanner(System.in);
			String accountNumber = sc.next();
			PreparedStatement prepStatement = myCon.prepareStatement("UPDATE FRAMEWORK SET STATUS = 0 WHERE ACC_NUM = ?");
			prepStatement.setString(1, accountNumber);
			int countOfRows = prepStatement.executeUpdate();
			System.out.println(countOfRows + "row(s) is affected.");

		}catch(Exception e){
			System.out.println(e);
		}
	}
	public static void exitMenu(){
		mysqlCrud myObj = new mysqlCrud();
		try{
			myCon.close();			
		}catch(Exception e){
			System.out.println(e);
		}
		System.exit(0);
	}

	public static void main(String[] args){

		while(true){
			System.out.println("\n1.Create\n2.Read\n3.update\n4.Delete\n5.Exit \nEnter your choice: ");
			Scanner sc = new Scanner(System.in);
			int choice = sc.nextInt();
			switch(choice){
			case 1: create();
			break;
			case 2: read();
			break;
			case 3: update();
			break;
			case 4: delete();
			break;
			case 5: exitMenu();
			break;
			default: System.out.println("Wrong input!");
			break;
			}
		}
	}
		
}
