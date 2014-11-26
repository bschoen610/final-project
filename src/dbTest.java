import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class dbTest {
	public Connection c;
	
	public dbTest() {
		try{
			//Connect to the db.  Change the root and pass if using different computer.
			c = DriverManager.getConnection("jdbc:mysql://localhost/cardshark", "root", "3Rdplacespel");
			//Instantiate a user with first name, last name, username, password, email, default currency, wins, and losses.
			/*PreparedStatement query = c.prepareStatement("INSERT INTO user (first_name, last_name, username, password, email, currency, wins, losses, avatar_path, ready_to_play)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			query.setString(1, "Sayantan"); query.setString(2, "Choco"); query.setString(3, "schoco"); query.setString(4,  "3Rdplacespel");
			query.setString(5,  "schoco@gmail.com"); query.setDouble(6, 100.50); query.setInt(7, 0); query.setInt(8, 0); 
			query.setString(9, "path"); query.setBoolean(10, false);
			query.execute();*/
			PreparedStatement query = c.prepareStatement("SELECT uid FROM user WHERE username = 'blaksana';");
			ResultSet rs = query.executeQuery();
			while (rs.next()){
				PreparedStatement relationQuery = c.prepareStatement("INSERT INTO user_friend_relational (uid, fid)"
						+ "VALUES (?,?)");
				relationQuery.setInt(1, rs.getInt("uid"));
				relationQuery.setInt(2, 5);
				relationQuery.execute();
				
			}
		}catch(SQLException sqle){
			System.out.println("Database Error: " + sqle.getMessage());
		}
	}
	
	public static void main(String[] argv){
		try{
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException cnfe){
			System.out.println(cnfe.getMessage());
		}
		
		dbTest dt = new dbTest();
	}

}
