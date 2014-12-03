package client;
import game.GamePanel;

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
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;


public class LobbyPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = -8069773572372219648L;
	private String un;
	ImageIcon loggedin = new ImageIcon("logged-in.png");
	ImageIcon loggedout = new ImageIcon("logged-out.png");
	JTextArea friendList = new JTextArea(10,10);
	private JButton join = new JButton("Join Game");
	private JLabel title;
	private JButton logout = new JButton("Logout");
	private JButton addFriend = new JButton("Add Friend");
	private JLabel balanceLabel = new JLabel("");
	private double balance = -1;
	public LobbyPanel(String un) {
		this.un = un;
		//setupAccountInfo();
		setupGUI();
		addEventHandler();
		
	}
	public void append(String s) {
		   try {
		      Document doc = friendList.getDocument();
		      doc.insertString(doc.getLength(), s, null);
		   } catch(BadLocationException exc) {
		      exc.printStackTrace();
		   }
		}
	private void setupGUI(){
		this.setLayout(new BorderLayout());
		this.add(join, BorderLayout.WEST);
		this.title = new JLabel("Welcome, " + this.un, JLabel.CENTER);
		this.title.setVerticalAlignment(SwingConstants.TOP);
		this.title.setFont(new Font("Serif", Font.BOLD, 20));
		JPanel north = new JPanel();
		JPanel northContainer = new JPanel();
		northContainer.setLayout(new BoxLayout(northContainer, BoxLayout.Y_AXIS));
		northContainer.add(title);
		getBalance();
		this.balanceLabel.setText("Your current balance: $" + this.balance);
		this.balanceLabel.setFont(new Font("Serif", Font.BOLD, 20));
		northContainer.add(balanceLabel);
		north.add(northContainer);
		this.add(north, BorderLayout.NORTH);
		JPanel east = new JPanel(new BorderLayout());
		east.setBorder(new EmptyBorder(10,10,10,10));
		JScrollPane scrollPane = new JScrollPane(friendList);
		friendList.setEditable(false);
		east.add(scrollPane, BorderLayout.CENTER);
		east.add(logout, BorderLayout.NORTH);
		east.add(addFriend, BorderLayout.SOUTH);
		this.add(east, BorderLayout.EAST);
		JPanel center = new JPanel();
		ImageIcon cow = new ImageIcon("./data/Cow.png");
		JLabel cowLabel = new JLabel();
		cowLabel.setIcon(cow);
		center.add(cowLabel);
		this.add(center, BorderLayout.CENTER);
		populateFriendList();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
	        public void run() {
	        	populateFriendList();
	        }
	    }, 3000, 3000);
		
	}
	
	private void addEventHandler(){
		this.logout.setActionCommand("logout");
		this.logout.addActionListener(this);
		this.addFriend.setActionCommand("addFriend");
		this.addFriend.addActionListener(this);
		this.join.setActionCommand("join");
		this.join.addActionListener(this);
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
		else if (ae.getActionCommand().equals("join")){
			joinGame();
		}
	}
	private void populateFriendList(){
		try {
			Socket s = new Socket("localhost", 3001);
			PrintWriter pwr = new PrintWriter(s.getOutputStream());
			pwr.println("populate");
			pwr.println(this.un);
			pwr.flush();
			friendList.setText("");
			int counter  = 0;
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			while(true){
				final String line = br.readLine();
			    if (line.equals("break-list") ) break;
			    else{
			    	if(counter %2 == 1)	// checks to see if line being read is a name or a boolean
			    		append(" " + line);
			    	else{
			    		if(line.equals("true")){	
			    			//JLabel login =  new JLabel (loggedin);
			    			//friendList.insertComponent(login);
			    			append ("ON:   ");
			    		}
			    		else{
			    			//JLabel logout =  new JLabel (loggedout);
			    			//friendList.insertComponent(logout);
			    			append("OFF:  ");
			    		}
			    	}
			    }
			    if(counter %2 == 1)
			    	append("\n");
			    counter++;
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
	
	private void getBalance(){
		Socket s;
		try {
			s = new Socket("localhost", 3001);
			PrintWriter pwr = new PrintWriter(s.getOutputStream());
			pwr.println("Balance");
			pwr.println(this.un);
			pwr.flush();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			double balance = Double.parseDouble(br.readLine());
			this.balance = balance;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		
	}
	
	private void joinGame(){
		Container parent = this.getParent();
		parent.remove(this);
		parent.add(new GamePanel(un, balance));
		parent.validate();
		parent.repaint();
	}
}