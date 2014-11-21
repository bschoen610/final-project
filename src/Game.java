import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
    static JMenuItem profile = new JMenuItem("Profile");
    
    static JButton createGameButton;
    static JButton joinGameButton;
    
    final static String LOBBY = "Lobby";
    final static String GAME = "Game";
    final static String CREATEGAME = "Create Game";
    final static String JOINGAME = "Join Game";
    final static String PROFILE = "Profile";

    public Game(){
		super("Poker Game");
        mainView.setLayout(new BoxLayout(mainView, BoxLayout.X_AXIS));
        chatView.setLayout(new BoxLayout(chatView, BoxLayout.Y_AXIS));
        
        cardView.setPreferredSize(new Dimension(600, 600));
        cardView.setMaximumSize(new Dimension(600, 600));
        chatView.setPreferredSize(new Dimension(200, 600));
        chatView.setMaximumSize(new Dimension(200, 600));
        
		MenuItemListener menuItemListener = new MenuItemListener();
		profile.addActionListener(menuItemListener);

		// have a popup over the main window for login 
		// upon login the main window will be enabled
	
		ImageIcon logo = new ImageIcon("CardShark.png");
		JMenuItem cardShark = new JMenuItem(logo);
		
		menuBar.add(cardShark);
		menuBar.add(profile);
		setJMenuBar(menuBar);
		mainView.add(cardView);
		mainView.add(chatView);
		add(mainView);
		lobbyView();
		chatView();
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
            	createGameView();
            }
                
            else if(ae.getSource() == joinGameButton){
            	joinGameView();
            }
		}
	}

    public static void lobbyView(){
		JPanel lobbyView = new JPanel();
        lobbyView.setLayout(new BoxLayout(lobbyView, BoxLayout.Y_AXIS));
		createGameButton = new JButton("Create Game");
		joinGameButton = new JButton("Join Game");
		// if(user is not logged in){
		// 		createGameButton.setEnabled(false);
		// 		joinGameButton.setEnabled(false);
		lobbyView.add(createGameButton);
		lobbyView.add(joinGameButton);
		
		cardView.add(lobbyView, LOBBY);
    	CardLayout cl = (CardLayout)cardView.getLayout();
        cl.show(cardView, LOBBY);
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
    
    public static void chatView(){
    	JLabel chatLabel = new JLabel("Chat");
    	// if(user is logged in){
    	// have a tabbed pane to toggle general chat and in-game chat
    	// get list of friends
    	// make a JButton for each friend that when clicked will call a function that refreshes the chat area 
    	// those JButton's should be contained in a JScrollPane
    	// below that should be the chat area
    	// chat area would consist of JLabels of two different colors to differentiate sender
    	// below that should be a text area to compose the message
    	// we could just have a mnemonic for the Enter key to send message
    	
    	chatView.add(chatLabel);
    }
    
    public static void gameView(){
    	// this view will probably need something like GridBagLayout
    	// probably best to make a Game JPanel 
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
