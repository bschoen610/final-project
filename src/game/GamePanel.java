package game;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GamePanel extends JPanel{
	Socket s;
	ObjectOutputStream oos = null;
	private static final long serialVersionUID = 239847298347L;
	public GamePanel(String un) {
		setupGUI();
		try {
			s = new Socket("localhost", 60502);
			oos = new ObjectOutputStream(s.getOutputStream());
			Message checkin = new CheckIn(un);
			oos.writeObject(checkin);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
			System.exit(1);
		}
	}
	
	private void setupGUI(){
		repaint();
	}
	
	public void paintComponent(Graphics page)
	{
	    super.paintComponent(page);
	    Image img = new ImageIcon("./data/cardmat.jpg").getImage();
	    page.drawImage( img, 0, 0, null );
	}

}
