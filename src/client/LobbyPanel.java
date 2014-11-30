package client;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import server.LobbyServer;


public class LobbyPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = -8069773572372219648L;
	private String un;
	JTextArea textArea;
	private JButton join = new JButton("Join Game");
	private JLabel title;
	private JButton logout = new JButton("Logout");
	private JButton addFriend = new JButton("Add Friend");
	public LobbyPanel(String un) {
		this.un = un;
		setupGUI();
		addEventHandler();
	}
	
	private void setupGUI(){
		this.setLayout(new BorderLayout());
		this.add(join, BorderLayout.WEST);
		this.title = new JLabel("Welcome, " + this.un);
		this.title.setFont(new Font("Serif", Font.BOLD, 20));
		JPanel north = new JPanel();
		north.add(title);
		this.add(north, BorderLayout.NORTH);
		JPanel east = new JPanel(new BorderLayout());
		east.setBorder(new EmptyBorder(10,10,10,10));
		textArea = new JTextArea(10, 10);
		JScrollPane scrollPane = new JScrollPane(textArea);
		populateFriendList();
		//System.out.println ("HI");
		east.add(scrollPane, BorderLayout.CENTER);
		east.add(logout, BorderLayout.NORTH);
		east.add(addFriend, BorderLayout.SOUTH);
		this.add(east, BorderLayout.EAST);
	}
	
	private void addEventHandler(){
		this.logout.setActionCommand("logout");
		this.logout.addActionListener(this);
		this.addFriend.setActionCommand("addFriend");
		this.addFriend.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("logout")){
			logout();
		}
		else if (ae.getActionCommand().equals("addFriend")){
			String friend = JOptionPane.showInputDialog("Enter a friend's username.");
			if (friend == null) return;
			addFriend(friend);
			populateFriendList();
		}
	}
	private void populateFriendList(){
		try {
			Socket s = new Socket("localhost", 3001);
			PrintWriter pwr = new PrintWriter(s.getOutputStream());
			pwr.println("populate");
			pwr.println(this.un);
			pwr.flush();
			textArea.setText("");
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			while(true){
				final String line = br.readLine();
			    if (line.equals("break-list")) break;
			    else
			    	textArea.append(line);
				textArea.append("\n");
			}
		
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	private void logout(){
		try {
			Socket s = new Socket("localhost", 3001);
			PrintWriter pwr = new PrintWriter(s.getOutputStream());
			pwr.println("Logout");
			pwr.println(this.un);
			pwr.flush();
			//Need some help redirecting to login.
			Container parent = this.getParent();
			parent.remove(this);
			parent.add(new LoginPanel());
			parent.validate();
			parent.repaint();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private void addFriend(String friend){
		try{
			Socket s = new Socket("localhost", 3001);
			PrintWriter pwr = new PrintWriter(s.getOutputStream());
			pwr.println("AddFriend");
			pwr.println(this.un);
			pwr.println(friend);
			pwr.flush();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String message = br.readLine();
			JOptionPane.showMessageDialog(this, message);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}