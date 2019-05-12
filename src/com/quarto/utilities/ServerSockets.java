package com.quarto.utilities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.quarto.engine.utilities.Vector2D;
import com.quarto.enums.Players;
import com.quarto.players.OnlinePlayer;
import com.quarto.scenes.BoardScene;
import com.quarto.scenes.LoadingScene;

public class ServerSockets extends Thread{

	private LoadingScene loadingScene;
	private BoardScene boardScene;
	private OnlinePlayer onlinePlayer;
	
	private String IP = "127.0.0.1";
	private int port = 8003;

	private boolean isServer;
	private boolean isRunning;
	
	private ServerSocket serverSocket;
	private Socket socket;
	
    private ObjectOutputStream out;
    private ObjectInputStream in;
	
	public void onInit() {
		// TODO Auto-generated method stub
		this.setRunning(true);
		this.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(isServer())
			startServer();
		else
			startConnection();
		
		while(isRunning()) {
			if(socket.isClosed())
				onDestroy();
			else
				onMessageReceived();
		}
		
		onDestroy();
	}

	public void onDestroy() {
		// TODO Auto-generated method stub
		setRunning(false);
		if(isServer())
			stopServer();
		else
			stopConnection();
		boardScene.onServerSocketKilled();
	}
    
    private boolean startConnection() {
		// TODO Auto-generated method stub
		//GameEngine.Log("Trying to connect to " + getIP() + " on port " + getPort());
		try {
			getLoadingScene().getLoadingDetails().setText("Client");
			socket = new Socket(getIP(), getPort());
			out = new ObjectOutputStream(socket.getOutputStream());
	        in = new ObjectInputStream(socket.getInputStream());
	        getBoardScene().setCurrentPlayerEnum(Players.ONLINE);
	        getBoardScene().setCurrentPlayer(Players.ONLINE);
	        getBoardScene().getCurrentPlayer().onTurn();
        	getLoadingScene().getLoadingDetails().setText("Connexion reussie");
        	getLoadingScene().getLoadingDetails().getColor().set(0, 1, 0, 1);
	        getLoadingScene().makeTransition();
			//GameEngine.Log("Connection succeeded to " + socket.getLocalSocketAddress() + " on port " + getPort());
			return true;
        }catch(Exception e) {
        	getLoadingScene().getLoadingDetails().setText("Erreur");
        	getLoadingScene().getLoadingDetails().getColor().set(1, 0, 0, 1);
        	getLoadingScene().backToMenu();
			//GameEngine.Log("Connection failed to " + getIP() + " on port " + getPort());
			e.printStackTrace();
			return false;
        }

	}
	
	private void startServer() {
		// TODO Auto-generated method stub
		//GameEngine.Log("Trying to create a server on " + getIP() + " on port " + getPort());
		try {
			getLoadingScene().getLoadingDetails().setText("Serveur");
			serverSocket = new ServerSocket(getPort());
			//GameEngine.Log("Server creation succeeded on " + serverSocket.getLocalSocketAddress() + " on port " + serverSocket.getLocalPort());
			//GameEngine.Log("Server awaiting connection on " + serverSocket.getLocalSocketAddress() + " on port " + serverSocket.getLocalPort());

			getLoadingScene().getLoadingDetails().setText("En attente d'un joueur (" + InetAddress.getLocalHost().getHostAddress() + ")");
			socket = serverSocket.accept();
			out = new ObjectOutputStream(socket.getOutputStream());
	        in = new ObjectInputStream(socket.getInputStream());
        	getLoadingScene().getLoadingDetails().setText("Un joueur s'est connecte");
        	getLoadingScene().getLoadingDetails().getColor().set(0, 1, 0, 1);
	        getLoadingScene().makeTransition();
			//GameEngine.Log("Server got connection on " + serverSocket.getLocalSocketAddress() + " on port " + serverSocket.getLocalPort());
        }catch(Exception e) {
        	getLoadingScene().getLoadingDetails().setText("Erreur");
        	getLoadingScene().getLoadingDetails().getColor().set(1, 0, 0, 1);
        	getLoadingScene().backToMenu();
			//GameEngine.Log("Server creation failed on " + getIP() + " on port " + getPort());
        }
	}
	
	public void sendMessage(Object msg) {
        try {
			out.writeObject(msg);
			//GameEngine.Log("Message Sent: " + msg);
		} catch (IOException e) {
			//GameEngine.Log("Message Not Sent: " + msg);
		}
    }
	
	public void onMessageReceived() {
		try {
			Object message = in.readObject();
			if(message.getClass() == PieceProperties.class)
				getOnlinePlayer().onSelectPiece((PieceProperties) message); 
			if(message.getClass() == Vector2D.class)
				getOnlinePlayer().onSelectSocket((int) ((Vector2D) message).getX(), (int) ((Vector2D) message).getY()); 
		} catch (ClassNotFoundException | IOException e1) {e1.printStackTrace(); onDestroy(); }
    }
	
	private void stopConnection() {
        try {
        	if(in != null)
        		in.close();
        	if(out != null)
        		out.close();
			//GameEngine.Log("Stopping connection on " + getIP() + " on port " + getPort());
        	if(socket != null)
        		socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	public void stopServer() {
        try {
        	if(in != null)
        		in.close();
        	if(out != null)
        		out.close();
			//GameEngine.Log("Stopping connection on " + getIP() + " on port " + getPort());
        	if(socket != null)
        		socket.close();
			//GameEngine.Log("Stopping server on " + getIP() + " on port " + getPort());
        	if(serverSocket != null)
        		serverSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isServer() {
		return isServer;
	}

	public void setServer(boolean isServer) {
		this.isServer = isServer;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public BoardScene getBoardScene() {
		return boardScene;
	}

	public void setBoardScene(BoardScene boardScene) {
		this.boardScene = boardScene;
	}

	public OnlinePlayer getOnlinePlayer() {
		return onlinePlayer;
	}

	public void setOnlinePlayer(OnlinePlayer onlinePlayer) {
		this.onlinePlayer = onlinePlayer;
	}

	public LoadingScene getLoadingScene() {
		return loadingScene;
	}

	public void setLoadingScene(LoadingScene loadingScene) {
		this.loadingScene = loadingScene;
	}
	
}
