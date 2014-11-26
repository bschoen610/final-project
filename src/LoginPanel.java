import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 763422554098991814L;
	
	private JTextField username = new JTextField();
	private JPasswordField password = new JPasswordField();
	private JButton submit = new JButton("Submit");
	private JButton register = new JButton("Register");
	private DataInputStream dis;
	private PrintWriter pw;
	
	public LoginPanel() {
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.0;
		this.add(new JLabel("Username", SwingConstants.RIGHT), c);
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(this.username, c);
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.0;
		this.add(new JLabel("Password", SwingConstants.RIGHT), c);
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(this.password, c);
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		this.add(this.submit, c);
		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		this.add(this.register, c);
		this.submit.setActionCommand("submit");
		this.submit.addActionListener(this);
		this.register.setActionCommand("register");
		this.register.addActionListener(this);
		setupClient();
	}

	private void setupClient(){
		try{
			Socket s = new Socket("localhost", 8000);
			this.dis = new DataInputStream(s.getInputStream());
			this.pw = new PrintWriter(s.getOutputStream());
		} catch (IOException ioe){
			System.out.println ("Problem establishing connection from client: " + ioe.getMessage());
		}
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("submit")) {
			this.submit();
		} else if (e.getActionCommand().equals("register")) {
			this.showRegister();
		}
	}
	
	private void submit() {
		String un = this.username.getText();
		String pw = new String(this.password.getPassword());
		
		if (this.checkLogin(un, pw)) {
			Container parent = this.getParent();
			parent.remove(this);
			parent.add(new LobbyPanel());
			parent.validate();
			parent.repaint();
		}
		else{
			System.out.println("Incorrect username or password.");
		}
	}
	
	private boolean checkLogin(String uid, String pw) {
		try {
			this.pw.println(uid);
			this.pw.println(pw);
			this.pw.flush();
			//Freeze the client until we can lookup the un/pw.
			while(dis.available() == 0){}
			return this.dis.readBoolean();
		} catch (IOException ioe) {
			System.out.println("Error writing un/pw to Server: " + ioe.getMessage());
			return false;
		}
	}
	
	private void showRegister() {
		Container parent = this.getParent();
		parent.remove(this);
		parent.add(new RegisterPanel());
		parent.validate();
		parent.repaint();
	}
}
