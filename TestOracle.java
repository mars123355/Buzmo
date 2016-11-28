import java.sql.*;
import java.util.*;


public class TestOracle {

	private static Scanner sc;
	private static Connection con;
	public static void main(String[] args) {
		//String url = "jdbc:oracle:thin:@uml.cs.ucsb.edu:1521:xe";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String username = "bo_luan";
		String password = "021";
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection(url,username, password);
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		sc = new Scanner(System.in);
    	System.out.println("Welcome to BuzzMo!");
    	// Log in
    	boolean authorized = false;
    	String email=null;
    	String pw=null;
    	while(!authorized){
    		System.out.println("Log in - Please enter your email:");
    		email=sc.nextLine();
    		System.out.println("Log in - Please enter your password:");
    		pw=sc.nextLine();
    		String querry = "SELECT * FROM Users U ";
    		querry += "WHERE U.email=" + email + " AND U.password=" + pw;
    		ResultSet rs = executeSQL(querry);
    		try {
    			authorized = rs.next();
    		} catch (Exception e) {
    			System.out.println(e);
    		}
    	}
    	
    	// select initial operation
		int operation = initialUI();
		switch(operation){
		case 1:
			view_post_message();
		case 2:
			create_ChatGrounp();
		case 3:
			browsing_mode();
		}
		
		
	}
	
	private static void view_post_message() {
    	System.out.println("Please enter the number to select one of the following actions");
    	
	}
	
	private static void create_ChatGrounp() {
		
		
	}
	
    private static void browsing_mode() {
    	
		
	}





	private static ResultSet executeSQL(String sql){
		//String sql = "SELECT * FROM rmr.Customers";
    	ResultSet rs = null;
    	try{
    		Statement st = con.createStatement();
    		rs = st.executeQuery(sql);
    		//		while(rs.next())
			//MODIFY PRINT TO FIT YOUR QUERY AND ATTRIBUTE TYPES
			//System.out.println(rs.getInt(1)+" "+rs.getString(2));
    		//con.close();
    	} catch(Exception e) {
    		System.out.println(e);
    	}
        return rs;
    }
    
    public static int initialUI(){
    	System.out.println("Please enter the number to select one of the following actions");
    	System.out.println("1.View/Post messages to a friend/MyCircle/ChatGroup");
    	System.out.println("2.Create a new ChatGroup");
    	System.out.println("3.Browse messages in BuzzMo system");
    	return sc.nextInt();
    }
}
