package server;
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class RegisterServer extends JFrame {
	private static final long serialVersionUID = 873564287L;
	private JPanel main;
	private JTextArea serverView;
	private Connection c;
	
	public RegisterServer() {
		super ("Register Server");
		setSize(600,600);
		setupGUI();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try{
			@SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket(60501);
			c = DriverManager.getConnection("jdbc:mysql://localhost/cardshark", "root", "root");
		
			while(true){
				Socket s = ss.accept();
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				String firstName = br.readLine();
				String lastName = br.readLine();
				String un = br.readLine();
				String password = br.readLine();
				String email = br.readLine();
				PrintWriter pwr = new PrintWriter(s.getOutputStream());
				pwr.println(addUser(firstName, lastName, un, password, email));
				pwr.flush();
			}
		} catch(IOException ioe){
			System.out.println("IO Error: " + ioe.getMessage());
			System.exit(1);
		} catch (SQLException sqle){
			System.out.println("SQL Error: " + sqle.getMessage());
			System.exit(1);
		}
	}
	
	//If the user already exists inside the DB, we return false.
	boolean addUser(String firstName, String lastName, String un, String password, String email){
		try{
			PreparedStatement query = c.prepareStatement("INSERT INTO user (first_name, last_name, username, password, email, currency, wins, losses, avatar_path, ready_to_play) "
					+ "VALUES(?,?,?,?,?,?,?,?,?,?);" );
			query.setString(1, firstName); query.setString(2,  lastName); query.setString(3, un); query.setString(4, password); query.setString(5,  email);
			query.setDouble(6, 0.00); query.setInt(7, 0); query.setInt(8, 0); query.setString(9, "PATH_GOES_HERE"); query.setBoolean(10, false);
			query.execute();
			return true;
		} catch(SQLException sqle){
			System.out.println("User already exists in the database");
			return false;
		}
	}

	private void setupGUI(){
		main = new JPanel(new BorderLayout());
		serverView = new JTextArea();
		main.add(serverView, BorderLayout.CENTER);
		this.add(main);
	}
	
	public static void main(String[] argv){
		try{
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException cnfe){
			cnfe.printStackTrace();
			System.exit(1);
		}
		new RegisterServer();
	}
}
