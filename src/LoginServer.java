import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
	private JPanel main; private JTextArea serverView;
	private Connection c;
	public LoginServer() {
		super ("Login Server");
		setSize(600,600);
		setupGUI();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try{
			c = DriverManager.getConnection("jdbc:mysql://localhost/cardshark", "root", "3Rdplacespel");
			ServerSocket ss = new ServerSocket(5555);
			System.out.println("Server is running at port 5555.");
			
			while (true){
				//Make the server wait while clients are being accepted.
				serverView.append("Waiting for Connections...");
				Socket s = ss.accept();
				serverView.append("\nConnection from " + s.getInetAddress() + "\n");
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				String un = br.readLine();
				String pw = br.readLine();
				br.close();
				dos.writeBoolean(authenticated(un, pw));
				dos.flush();
				dos.close();
				ss.close();
			}
		} catch(IOException ioe){
			System.out.println("Server failed to start: " + ioe.getLocalizedMessage());
		} catch (SQLException sqle) {
			System.out.println("SQL error- failed to connect to DB: " + sqle.getMessage());
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
			//Return true if password is correct.
			if (pass.equals(rs.getString("password"))) return true;
			//Wrong password.
			else return false;
		} catch (SQLException sqle) {
			// TODO Auto-generated catch block
			System.out.println("Authentication Error: " + sqle.getMessage());
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
			System.out.println("Error with class " + cnfe.getMessage());
		}
		new LoginServer();
	}

}
