import java.sql.*;
import java.util.*;


public class TestOracle {

	private static Scanner sc;
	private static Connection con;
	private static String email;
	public static void main(String[] args) {
		String url = "jdbc:oracle:thin:@uml.cs.ucsb.edu:1521:xe";
		//String url = "jdbc:oracle:thin:@localhost:1521:xe";
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
		System.out.println("Welcome to BuzMo!");
		while(true){
			// Log in
			boolean authorized = false;
			email=null;
			String pw=null;
			while(!authorized){
				System.out.println("Log in - Please enter your email:");
				if(!sc.hasNext()){ // exit program on ctrl+D
					System.exit(0);
				}
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
			
			// check manager
			boolean manager = false;
			String querry = "SELECT * FROM Manager M WHERE M.email=" + email;
			ResultSet rs = executeSQL(querry);
			try{
				manager = rs.next();
			} catch (Exception e) { System.out.println(e);}
			
			// select initial operation
			boolean back = false;
			while(!back){
				int operation = initialUI(manager);
				switch(operation){
				case 1: view_post_message(); break;
				case 2: create_ChatGrounp(); break;
				case 3: browsing_mode(); break;
				case 0: back = true; break;
				case 4:
					if(manager){
						manager_options();
						break;
					}
				default: System.out.println("Invalid input"); break;
				}
			}
    	}
	}
	
	public static int initialUI(boolean manager){
    	System.out.println("Please enter the number to select one of the following actions");
    	System.out.println("1.View/Post messages to a friend/MyCircle/ChatGroup");
    	System.out.println("2.Create a new ChatGroup");
    	System.out.println("3.Browse messages in BuzzMo system");
    	if(manager){
    		System.out.println("4.Manager options");
    	}
    	System.out.println("0.Log out");
    	System.out.println("Ctrl+D: Exit BuzMo");
    	if(!sc.hasNext()){
    		System.exit(0);
    	}
    	return sc.nextInt();
    }
	
	private static void view_post_message() {
		boolean back=false;
		while(!back){
			System.out.println("Please select the option you want to post message to");
			System.out.println("1.friend");
			System.out.println("2.MyCircle");
			System.out.println("3.ChatGroup");
			System.out.println("0.Go back to last menu");
			int option = sc.nextInt();
			switch(option){
			case 1: friend_msg(); break;
			case 2: MyCircle_msg(); break;
			case 3: ChatGroup_msg(); break;
			case 0: back=true; break;
			default: System.out.println("Invalid input"); break;
			}
		}
	}
	
	private static void create_ChatGrounp() {
		System.out.println("Please enter ChatGroup name:");
		String CG_name=sc.nextLine();
		System.out.println("Please set ChatGroup duration:");
		int du = sc.nextInt();
		// create ChatGroup
		
	}
	
    private static void browsing_mode() {
    	boolean back = false;
    	while(!back){
    		System.out.println("Please select the operation:");
    		System.out.println("1.Search messages by topic words");
    		System.out.println("2.Search user by email");
    		System.out.println("3.Search user by topic words");
    		System.out.println("4.Search user by the most recent with last n days");
    		System.out.println("5.Search user by n or more of messages in last 7 days");
    		System.out.println("6.Send friend request");
    		System.out.println("0.Go back to last menu");
    		int op = sc.nextInt();
    		switch(op){
    		case 1:
    			ArrayList<String> words_1 = getWordsFromInput();
    			break;
    		case 2:
    			break;
    		case 3:
    			ArrayList<String> words_3 = getWordsFromInput();
    			break;
    		case 4:
    			break;
    		case 5:
    			break;
    		case 6:
    			break;
    		case 0:
    			back=true;
    			break;
    		default:
    			System.out.println("Invalid input");
    			break;
    		}
    	}
	}

	private static ArrayList<String> getWordsFromInput() {
		ArrayList<String> words = new ArrayList<String>();
		System.out.println("Enter topic words on each line, ended by Ctrl+D");
		while(sc.hasNext()){
			words.add(sc.nextLine());
		}
		return words;
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
    
	private static void friend_msg(){
		
		System.out.println("Please enter the email or name of your friend: ");
		String friend = sc.nextLine();
		String querry = null;
		// check for this friend 
		if(friend.contains("@")){
			querry = "SELECT U.email FROM Users U ";
	    	querry += "WHERE U.email=" + friend;
		}
		else{
			querry = "SELECT U.email FROM Users U ";
	    	querry += "WHERE U.screenname=" + friend;
		}
		ResultSet rs = executeSQL(querry);
		String friend_email = null;
		try{
			if(rs.next()){
				friend_email = rs.getString(1);
			}
		} catch(Exception e) { System.out.println(e); }
		// select 7 most recent msg
		if(friend_email!=null){
			querry = "SELECT TOP 7 * FROM Message M, receives R ";
			querry += "WHERE M.sender=" + email + " AND R.email=" + friend_email;
			querry += " ORDER BY max(M.timestamp)";
		}
		rs = executeSQL(querry);
		try{
			while(rs.next()){
				// read messages
			}
		} catch(Exception e) { System.out.println(e); }
		
		boolean back=false;
		while(!back){
			int op = msg_options(false, false);
			switch(op){
			case 1:
				System.out.println("Please enter your message");
				String msg = sc.nextLine();
				break;
			case 2:
				System.out.println("Please enter the message id you want to delete");
				String msg_id = sc.nextLine();
				break;
			case 3:
				break;
			case 4:
				break;
			case 0:
				back=true;
				break;
			default:
				System.out.println("Invalid input");
				break;
			}
		}

	}

	private static void MyCircle_msg() {
		String querry = "SELECT TOP 7 * FROM MyCircle_msg M ";
		querry += "WHERE M.sender=" + email;
		querry += " ORDER BY max(M.timestamp)";
		ResultSet rs = executeSQL(querry);
		try{
			while(rs.next()){
				// read messages
			}
		} catch(Exception e) { System.out.println(e); }
		
		boolean back=false;
		while(!back){
			int op = msg_options(false, false);
			switch(op){
			case 1:
				System.out.println("Please enter your message");
				String msg = sc.nextLine();
				break;
			case 2:
				System.out.println("Please enter the message id you want to delete");
				String msg_id = sc.nextLine();
				break;
			case 3:
				break;
			case 4:
				break;
			case 0:
				back=true;
				break;
			default:
				System.out.println("Invalid input");
				break;
			}
		}
	}

	private static void ChatGroup_msg() {
		String querry = "SELECT DISTINCT I.name FROM in I ";
		querry += "WHERE I.email=" + email;
		ResultSet rs = executeSQL(querry);
		try{
			while(rs.next()){
				// read and output ChatGroups
			}
		} catch(Exception e) {System.out.println(e);}
		System.out.println("Please enter the name of ChatGroup you want to search:");
		String CG_name = sc.nextLine();
		querry = "SELECT TOP 7 * FROM ChatGroup_msg C ";
		querry = "WHERE C.name=" + CG_name + " AND EXISTS ";
		querry = "SELECT * FROM in I WHERE I.email=" + email + "AND C.name=" + CG_name;
		rs = executeSQL(querry);
		try{
			while(rs.next()){
				// read and output msg
			}
		} catch(Exception e) { System.out.println(e); }
		// check ownership of CG
		boolean owner = false;
		querry = "SELECT * FROM ChatGroup C WHERE C.owner" + email;
		rs = executeSQL(querry);
		try{
			owner = rs.next();
		} catch(Exception e) { System.out.println(e); }
		
		boolean back=false;
		while(!back){
			int op = msg_options(true, owner);
			switch(op){
			case 1:
				System.out.println("Please enter your message");
				String msg = sc.nextLine();
				break;
			case 2:
				System.out.println("Please enter the message id you want to delete");
				String msg_id = sc.nextLine();
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:	
				break;
			case 6:
				break;
			case 0:
				back=true;
				break;
			case 7:
				if(owner){
					//something
					break;
				}
			default:
				System.out.println("Invalid input");
				break;
			}
		}
	}
	
	private static int msg_options(boolean CG_mode, boolean owner) {
		System.out.println("Please select the operation for message");
		System.out.println("1.Post a message");
		System.out.println("2.Delete a message");
		System.out.println("3.Display more");
		System.out.println("4.Check friend request message");
		if(CG_mode){
			System.out.println("5.Invite a friend to join ChatGroup");
			System.out.println("6.Send friend request");
		}
		if(owner){
			System.out.println("7.Change ChatGroup Properties");
		}
		System.out.println("0.Go back to last menu");
		return sc.nextInt();
	}
	
    private static void manager_options() {
    	boolean back = false;
    	while(!back){
    		System.out.println("Please select the operation:");
    		System.out.println("1.Find active users");
    		System.out.println("2.Find top messages");
    		System.out.println("3.Show number of inactive users");
    		System.out.println("4.Display complete report");
    		System.out.println("0.Go back to last menu");
    		int op = sc.nextInt();
    		switch(op){
    		case 1:
    			break;
    		case 2:
    			break;
    		case 3:
    			break;
    		case 4:
    			break;
    		case 0:
    			back = true;
    			break;
    		}
    	}
	}
}

