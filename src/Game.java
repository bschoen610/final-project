import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.GamePanel;

public class Game extends JFrame {
	// TODO: my tendency is to make these variables static, but they may not need to be
    static JMenuBar menuBar = new JMenuBar();
    static JPanel mainView = new JPanel();
    static JPanel cardView = new JPanel(new CardLayout());
    static JPanel chatView = new JPanel();
    static JPanel lobbyView = new JPanel();
    static JPanel gameView = new JPanel();
    static JMenuItem profile = new JMenuItem("Profile");
    
    static JButton createGameButton;
    static JButton joinGameButton;

    static int chipCount;
	static JLabel chipCountLabel;
    static JButton exitButton;
    static JButton doubleDownButton;
    static JButton hitButton;
    static JButton stayButton;
    
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
        this.setSize(800, 600);
        this.setLocation(100, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		MenuItemListener menuItemListener = new MenuItemListener();
		profile.addActionListener(menuItemListener);

		// below is rudimentary login. Still needs option to register, etc.
        JTextField username = new JTextField(20);
        JPasswordField password = new JPasswordField(20);

        JPanel loginPanel = new JPanel();
        loginPanel.add(new JLabel("Username: "));
        loginPanel.add(username);
        loginPanel.add(new JLabel("Password: "));
        loginPanel.add(password);
        do{
	        int result = JOptionPane.showConfirmDialog(null, loginPanel, "Please Enter Username and Password", JOptionPane.OK_CANCEL_OPTION);
	        if(result == JOptionPane.OK_OPTION){
	        	if(username.getText().equals ("") || password.getPassword().length == 0){
	        		JOptionPane.showMessageDialog(this, "Please make sure the fields aren't empty!", "Error", JOptionPane.ERROR_MESSAGE);
	        		//System.exit(0);
	        	}
	        	// attempt login
	        }
	        else{
	        	System.exit(0);
	        }
        }while (username.getText().equals("") && password.getPassword().length == 0);
	
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
    
    public static void lobbyView(){
        class LobbyButtonListener implements ActionListener{
            public void actionPerformed(ActionEvent ae) {
                if(ae.getSource() == createGameButton){
                    gameView(); // this is just to test game view so eventually change back to:
                    //createGameView();
                }
                    
                else if(ae.getSource() == joinGameButton){
                    joinGameView();
                }
            }
        }
		JPanel lobbyView = new JPanel();
        lobbyView.setLayout(new BoxLayout(lobbyView, BoxLayout.Y_AXIS));

		LobbyButtonListener buttonListener = new LobbyButtonListener();
		createGameButton = new JButton("Create Game");
		joinGameButton = new JButton("Join Game");
		createGameButton.addActionListener(buttonListener);
		joinGameButton.addActionListener(buttonListener);
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
       
        /*
        JPanel avatar = new JPanel();
        avatar.setLayout(new BorderLayout());
        URL imageLink = null; 
        try {
			imageLink = new URL(""); // access player avatar through URL if possible
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
        
        JLabel nameLabel = new JLabel(username);
        profileView.add(nameLabel);

        JLabel winsLabel = new JLabel(numWins);
        profileView.add(winsLabel);

        JLabel lossesLabel = new JLabel(numLosses);
        profileView.add(lossesLabel);

        JLabel gamesPlayedLabel = new JLabel(numGamesPlayed);
        profileView.add(gamesPlayedLabel);

        */
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
               lobbyView(); 
            }});
        profileView.add(exitButton);
        
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
        class GameButtonListener implements ActionListener{
            public void actionPerformed(ActionEvent ae) {
                if(ae.getSource() == exitButton){
                    //exitButton();
                }
                else if(ae.getSource() == doubleDownButton){
                    //doubleDown();
                }
                else if(ae.getSource() == hitButton){
                    //hit();
                }
                else if(ae.getSource() == stayButton){
                    //stay();
                }
                // TODO: there is a fourth action button that I can't decipher from our whiteboard drawing
            }
        }
        
		GamePanel gameView = new GamePanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints(); 
		
		chipCount = 0;
		chipCountLabel = new JLabel(String.valueOf(chipCount));
		gbc.gridx = 550;
		gbc.gridy = 550;
		gbc.gridwidth = 50;
		gbc.gridheight = 50;
		gameView.add(chipCountLabel, gbc);
		
		exitButton = new JButton("Exit"); 
		gbc.gridx = 0;
		gbc.gridy = 500;
		gbc.gridwidth = 50;
		gbc.gridheight = 100;
		gameView.add(exitButton, gbc);
		
		doubleDownButton = new JButton("Double Down"); 
		gbc.gridx = 200;
		gbc.gridy = 550;
		gbc.gridwidth = 50;
		gbc.gridheight = 50;
		gameView.add(doubleDownButton, gbc);
		
		hitButton = new JButton("Hit"); 
		gbc.gridx = 250;
		gbc.gridy = 550;
		gbc.gridwidth = 50;
		gbc.gridheight = 50;
		gameView.add(hitButton, gbc);

		stayButton = new JButton("Stay"); 
		gbc.gridx = 300;
		gbc.gridy = 550;
		gbc.gridwidth = 50;
		gbc.gridheight = 50;
		gameView.add(stayButton, gbc);
		
		GameButtonListener gameButtonListener = new GameButtonListener();
		exitButton.addActionListener(gameButtonListener);
		doubleDownButton.addActionListener(gameButtonListener);
		
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
	}
}
