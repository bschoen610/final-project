package game;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class GamePanel extends JPanel implements ActionListener{
	Socket s;
	Player p;
	ObjectOutputStream oos = null;
	private JButton split = new JButton("Split");
	private JButton hit = new JButton("Hit");
	private JButton stay = new JButton("Stay");
	private JButton dd = new JButton("Double Down");
	private JButton leave = new JButton("Leave");
	private JTextField betAmount = new JTextField(25);
	private String un = "";
	private static final long serialVersionUID = 239847298347L;
	public GamePanel(String un, double balance) {
		setupGUI();
		this.un = un;
		try {
			s = new Socket("localhost", 60502);
			oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(new CheckIn(this.un));
			p = new Player(s, this.un, balance);
		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
	
	private void setupGUI(){
		this.setLayout(new BorderLayout());
		JPanel south = new JPanel();
		south.add(new JLabel("Bet: "));
		south.add(betAmount);
		hit.setActionCommand("hit"); split.setActionCommand("split"); stay.setActionCommand("stay");
		south.add(hit); south.add(stay); south.add(split); south.add(dd);
		this.add(south, BorderLayout.SOUTH);
		

		JPanel east = new JPanel(new BorderLayout());
		east.setBorder(new EmptyBorder(10,10,10,10));
		leave.setActionCommand("leave");
		east.add(leave, BorderLayout.NORTH);
		this.add(east, BorderLayout.EAST);
		
		
		repaint();
		
	}
	
	private void addEventHandlers(){
		
	}
	
	public void paintComponent(Graphics page)
	{
	    super.paintComponent(page);
	    Image img = new ImageIcon("./data/cardmat.jpg").getImage();
	    page.drawImage( img, 0, 0, null );
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("split")){
			
		}
		else if (ae.getActionCommand().equals("hit")){
			
		}
		else if (ae.getActionCommand().equals("stay")){
			
		}
		else if (ae.getActionCommand().equals("leave")){
			leave();
		}
		
	}
	
	private void leave(){
		try {
			oos.writeObject(new Leave(this.un));
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			System.out.println("Error sending the leave message: " + ioe.getMessage());
		}
		
		
	}
}
