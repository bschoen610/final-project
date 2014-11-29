package game;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Vector;

public class GameServer {
	private ServerSocket ss;
	
	public GameServer() {
		HashMap<String, Socket> userSocketMap = new HashMap<String, Socket>();
		
		try {
			this.ss = new ServerSocket(60502);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Socket s = null;
		// get first CheckIn
		try {
			s = this.ss.accept();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		userSocketMap.put(usernameFromSocket(s), s);
		
		try {
			// throw interrupted exception after JOIN_TIME seconds
			this.ss.setSoTimeout(Game.JOIN_TIME);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		
		for (int i = 0; i < Game.MAX_PLAYERS; ++i) {
			s = null;
			
			try {
				s = this.ss.accept();
			} catch (InterruptedIOException e) {
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			userSocketMap.put(usernameFromSocket(s), s);
		}
		
		new Game(userSocketMap).run();
		
		try {
			// don't drop connections
			this.ss.setSoTimeout(0);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
	}
	
	private String usernameFromSocket(Socket s) {
		ObjectInputStream ois = null;
		
		try {
			ois = new ObjectInputStream(s.getInputStream());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		CheckIn ci = null;
		
		try {
			ci = (CheckIn) ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ci.username;
	}
	
	public static void main(String[] args) {
		new GameServer();
	}
}