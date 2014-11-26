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
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class LoginServer extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel main;
	private JTextArea serverView;
	private Connection c;
	
	public LoginServer() {
		super ("Login Server");
		setSize(600,600);
		setupGUI();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		System.out.println("waiting");
		try{
			ServerSocket ss = new ServerSocket(60500);
			c = DriverManager.getConnection("jdbc:mysql://localhost/cardshark", "root", "3Rdplacespel");
		
			while (true) {
				Socket s = ss.accept();
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));			
				String un = br.readLine();
				String pw = br.readLine();
				boolean auth = authenticated(un, pw);
				PrintWriter pwr = new PrintWriter(s.getOutputStream());
				pwr.println(auth);
				pwr.flush();
			}
			
		} catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(1);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.exit(1);
		}
	}
	
	private boolean authenticated(String un, String pass){
		try {
			PreparedStatement query = c.prepareStatement("SELECT password FROM user WHERE username = ?");
			query.setString(1, un);
			ResultSet rs = query.executeQuery();
			//If username is not in the database, return false.
			if (!rs.next()) return false;
			//Reset the iterator through the resultset.
			rs.beforeFirst();
			rs.next();
			//Return true if password is correct
			if (pass.equals(rs.getString("password"))) return true;
			//Wrong password.
			else return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.exit(1);
		}
		
		return true;
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
		new LoginServer();
	}

}
