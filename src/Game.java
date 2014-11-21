import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Game extends JFrame {
    static JMenuBar menuBar = new JMenuBar();
    static JPanel mainView = new JPanel();
    static JPanel cardView = new JPanel(new CardLayout());
    static JPanel chatView = new JPanel();
    static JPanel lobbyView = new JPanel();
    static JPanel gameView = new JPanel();
    //static JPanel createGameView = new JPanel();
    //static JPanel joinGameView = new JPanel();
    //static JPanel profileView = new JPanel();
    static JMenuItem profile = new JMenuItem("Profile");
    
    final static String LOBBY = "Lobby";
    final static String GAME = "Game";
    final static String CREATEGAME = "Create Game";
    final static String JOINGAME = "Join Game";
    final static String PROFILE = "Profile";

    public Game(){
		super("Poker Game");
        mainView.setLayout(new BoxLayout(mainView, BoxLayout.X_AXIS));
        chatView.setLayout(new BoxLayout(chatView, BoxLayout.Y_AXIS));
		MenuItemListener menuItemListener = new MenuItemListener();
		profile.addActionListener(menuItemListener);
	
		//cardView.add(lobbyView, LOBBY);
		//cardView.add(gameView, GAME);
		//cardView.add(createGameView, CREATEGAME);
		//cardView.add(joinGameView, JOINGAME);

		ButtonListener bl = new ButtonListener();
		
		menuBar.add(profile);
		setJMenuBar(menuBar);
		mainView.add(cardView);
		mainView.add(chatView);
		add(mainView);
        setVisible(true);
	}

    class MenuItemListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			if(ae.getSource() == profile){
				profileView();
			}
		}
	}	
    
    class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
            if(ae.getSource() == createGameButton){
            }
                
            else if(ae.getSource() == joinGameButton){
            }
		}
	}
    
    public static void profileView(){
    	// TODO: retrieve latest statistics from database and display in snazzy JLabels
		JPanel profileView = new JPanel();
        profileView.setLayout((new BoxLayout(profileView, BoxLayout.Y_AXIS)));
        JLabel nameLabel = new JLabel("");
       
        /*
        JPanel avatar = new JPanel();
        avatar.setLayout(new BorderLayout());
        URL imageLink = null; 
        try {
			imageLink = new URL(""); // access player avatar through URL
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
        ImageIcon imageIcon = new ImageIcon(imageLink);
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);  
        ImageIcon newIcon = new ImageIcon(newImage);
        
        JLabel imageLabel = new JLabel("", newIcon, JLabel.CENTER);
        avatar.add(imageLabel, BorderLayout.CENTER); 
        profileView.add(avatar);
        */
        
		cardView.add(profileView, PROFILE);
    	CardLayout cl = (CardLayout)cardView.getLayout();
        cl.show(cardView, PROFILE);
    }

    public static void gameView(){
    	// this view will probably need something like GridBagLayout
		JPanel gameView = new JPanel();
       
        
		cardView.add(gameView, GAME);
    	CardLayout cl = (CardLayout)cardView.getLayout();
        cl.show(cardView, GAME);
    }
    
    public static void createGameView(){
		JPanel createGameView = new JPanel();
       
        
		cardView.add(createGameView, CREATEGAME);
    	CardLayout cl = (CardLayout)cardView.getLayout();
        cl.show(cardView, CREATEGAME);
    }

    public static void joinGameView(){
		JPanel joinGameView = new JPanel();
       
        
		cardView.add(joinGameView, GAME);
    	CardLayout cl = (CardLayout)cardView.getLayout();
        cl.show(cardView, JOINGAME);
    }
    
	public static void main(String[] args) {
		Game game = new Game();
        game.setSize(800, 600);
        game.setLocation(100, 100);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
