//framework code of MySQL 
import java.util.*;
import java.sql.*;

class crud{
	static ArrayList<String> fieldNames = new ArrayList<String>();
	static Connection myCon;
	static String tableName;
	static int columnCount;
	crud(){
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			 myCon = DriverManager.getConnection("jdbc:mysql://165.22.14.77/dbSairam", "Tharun", "pwdTharun");
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public static void storeFieldNamesIntoAnArrayList(){
		
		System.out.print("Enter the table name to perform the desired operation: ");
		
		int countOfFields = 0;
		Scanner sc = new Scanner(System.in);
		tableName = sc.next();
		try{
			Statement myStatement = myCon.createStatement();
			ResultSet myRs = myStatement.executeQuery("SELECT * FROM " + tableName);
			ResultSetMetaData metadata = myRs.getMetaData();

			columnCount = metadata.getColumnCount();
			System.out.println(columnCount);
			
			for(int counter = 1; counter <= columnCount; counter ++){
				fieldNames.add(metadata.getColumnName(counter));
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	public static void create(){
		ArrayList<String> fieldValues = new ArrayList<String>();
		String fieldValue;
		for(int counter = 0; counter < fieldNames.size(); counter ++) {
			System.out.print("Enter " + fieldNames.get(counter) + " : ");
			Scanner sc = new Scanner(System.in);
			fieldValue = sc.next();
			fieldValues.add(fieldValue);
		}
		try{
			Statement myStatement = myCon.createStatement();
			String values = "(";
			for(int counter = 0; counter < columnCount; counter ++){
				
				if(counter == columnCount  - 1){
					values = values + "\'" + fieldValues.get(counter) + "\'" + ")";
				}else{
					values = values + "\'" + fieldValues.get(counter) + "\'" + ", ";
				}
			}
			int countOfRows = myStatement.executeUpdate("INSERT INTO " + tableName + " VALUES" + values);
			if(countOfRows > 0){
				System.out.println("Insertion successful.");
				System.out.println(countOfRows + " row affected.");
			}
			else{
				System.out.println("Insertion unsuccessful.");
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	public static void read(){

		for(int counter = 0; counter < columnCount; counter ++ ){
			System.out.print(fieldNames.get(counter) + " ");
		}
		System.out.println("");
		System.out.println("------------------------------------------------------");
		try{
			Statement myStatement = myCon.createStatement();
			ResultSet myrs = myStatement.executeQuery("Select * from " + tableName);
			while(myrs.next()){
				for(int counter = 0; counter < columnCount; counter ++){
					System.out.print(myrs.getString(counter + 1) + " ");
				}
				System.out.println("");
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	public static void update(){
		System.out.print("Enter the " + fieldNames.get(0) + " to update: ");
		Scanner sc = new Scanner(System.in);
		String tempId = sc.next();
		System.out.println("What do you want to update: ");
		for(int counter = 1; counter < columnCount - 1; counter ++ ){
			System.out.println(counter + " " + fieldNames.get(counter));
		}
		System.out.print("Chose choice: ");
		int choice = sc.nextInt();
		
		System.out.println("Enter " + fieldNames.get(choice) + ": ");
		String fieldValue = sc.next();
		try{
			Statement myStatement = myCon.createStatement();
			int resultRows = myStatement.executeUpdate("UPDATE " + tableName + " SET " + fieldNames.get(choice) + " = \'" + fieldValue + "\' WHERE " + fieldNames.get(0) + " = \'" + tempId + "\'");
			if(resultRows > 0){
				System.out.println("Updation successful.");
				System.out.println(resultRows + " row affected.");
			}
			else{
				System.out.println("Updation failed.");
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	public static void delete(){
		System.out.print("Enter the " + fieldNames.get(0) + " to delete: ");
		Scanner sc = new Scanner(System.in);
		String tempId = sc.next();
		try{
			Statement myStatement = myCon.createStatement();
			int resultRows = myStatement.executeUpdate("UPDATE " + tableName + " SET " + fieldNames.get(columnCount - 1) +" = '0' WHERE " + fieldNames.get(0) + " = \'" + tempId + "\'");
			if(resultRows > 0){
				System.out.println("Deletion successful.");
				System.out.println(resultRows + " row affected.");
			}
			else{
				System.out.println("Deletion failed.");
			}
		}catch(Exception e){
			System.out.println(e);
		}

	}
	public static void exitMenu(){
		System.exit(1);
	}

}

class mySqlFramework{

	public static void main(String[] args){
		
		crud obj = new crud();
		obj.storeFieldNamesIntoAnArrayList();
		while(true){
			System.out.print("\n___Menu___\n1.Create\n2.Read\n3.Update\n4.Delete\n5.Exit\nEnter your choice: ");
			Scanner sc = new Scanner(System.in);
			int choice = sc.nextInt();
			switch(choice){
				case 1: obj.create();
				break;
				case 2: obj.read();
				break;
				case 3: obj.update();
				break;
				case 4: obj.delete();
				break;
				case 5: obj.exitMenu();
				break;
				default: System.out.println("Wrong input!");
				break;
			}
		}
	}
}