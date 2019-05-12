package com.quarto.scenes;

import java.util.HashMap;

import com.quarto.Quarto;
import com.quarto.ai.AIPlayer;
import com.quarto.engine.core.Player;
import com.quarto.engine.core.Scene;
import com.quarto.engine.managers.DataManager;
import com.quarto.engine.managers.SoundManager;
import com.quarto.engine.utilities.Color;
import com.quarto.engine.utilities.Vector2D;
import com.quarto.enums.Difficulties;
import com.quarto.enums.Players;
import com.quarto.objects.board.GameBoard;
import com.quarto.objects.board.Piece;
import com.quarto.objects.board.Socket;
import com.quarto.objects.board.menu.TopBar;
import com.quarto.players.OnlinePlayer;
import com.quarto.players.QuartoPlayer;
import com.quarto.utilities.PieceProperties;
import com.quarto.utilities.ServerSockets;

public class BoardScene extends Scene {
	
	Difficulties difficulty;
	private float boardPadding = .43f;
	private float boardSpacing = .01f;
	private float socketSize = 0;

	private TopBar topBar;
	private GameBoard gameBoard;

	private QuartoPlayer currentPlayer;
	private OnlinePlayer onlinePlayer;
	private Players currentPlayerEnum;
	private Socket[][] sockets;
	private Piece[] pieces;
	private boolean finished;

	private ServerSockets serverSockets;
	private String hostIP = null;
	
	private boolean destroyed = false;
	
	public BoardScene(Difficulties d) {
		// TODO Auto-generated constructor stub
		difficulty = d;
	}
	
	public BoardScene(Difficulties d, String h) {
		// TODO Auto-generated constructor stub
		difficulty = d;
		hostIP = h;
	}

	@Override
	public void onFinishedLoading(LoadingScene loadingScene) {
		// TODO Auto-generated method stub
		if(difficulty != Difficulties.ONLINE) {
			loadingScene.makeTransition();
		}else {
			serverSockets = new ServerSockets();
			serverSockets.setLoadingScene(loadingScene);
			serverSockets.setBoardScene(this);
			serverSockets.setServer(true);
			if(hostIP != null) {
				serverSockets.setServer(false);
				serverSockets.setIP(hostIP);
			}
			serverSockets.onInit();

			serverSockets.setOnlinePlayer(onlinePlayer);
		}
	}
	
	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		this.setFinished(false);
		this.setSockets(new Socket[4][4]);
		
		this.setBackgroundColor(new Color(1f, 1f, 1f));
		this.setBackgroundTexturePath("board/background.png");
		
		topBar = new TopBar();
		this.addSceneObject(topBar);
		topBar.getPosition().set(0, 0);
		
		gameBoard = new GameBoard();
		this.addSceneObject(gameBoard);
		
		socketSize = (4 - 2 * boardPadding - 3 * boardSpacing) / 4;
		float posX, posY, pos;
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				Socket socket = new Socket(socketSize);
				posX = (boardPadding + boardSpacing * i + socketSize * i) + gameBoard.getPosition().getX();
				posY = (boardPadding + boardSpacing * j + socketSize * j) + gameBoard.getPosition().getY();
				this.addSceneObject(socket);
				socket.setGridPosition(new Vector2D(i, j));
				socket.setPosition(new Vector2D(posX, posY));
				sockets[i][j] = socket;
			}
		}
		
		pieces = new Piece[16];
		for (int i = 0; i < 16; i++) {
			Piece piece = new Piece(socketSize * .65f);
			piece.setTall(i > 3 && i < 12);
			piece.setDark(i > 7);
			piece.setFull(i % 2 == 1);
			piece.setSquare(i % 4 == 0 || i % 4 == 1);
			
			pos = (i % 4) * (socketSize + boardSpacing) + boardPadding;
			
			if(i > 7) {
				posX = gameBoard.getPosition().getX() + pos + ((socketSize * .35f) / 2);
				if(i > 11) {
					posY = gameBoard.getPosition().getY() - (socketSize / 2) - (boardPadding / 2);
				}else{
					posY = gameBoard.getPosition().getY() - (socketSize / 4) + (boardPadding / 2) + gameBoard.getSize().getY();
				}
			}else{
				posY = gameBoard.getPosition().getY() + pos + ((socketSize * .35f) / 2);
				if(i > 3) {
					posX = gameBoard.getPosition().getX() - (socketSize / 2) - (boardPadding / 2);
				}else{
					posX = gameBoard.getPosition().getX() - (socketSize / 4) + (boardPadding / 2) + gameBoard.getSize().getX();
				}
			}
			
			pieces[i] = piece;
			this.addSceneObject(piece);
			piece.setPosition(new Vector2D(posX, posY));
		}

		QuartoPlayer player1 = new QuartoPlayer(Players.PLAYER1);
		this.addPlayer(player1);
		
		if(difficulty == Difficulties.PVP) {
			QuartoPlayer player2 = new QuartoPlayer(Players.PLAYER2);
			this.addPlayer(player2);
		}if(difficulty == Difficulties.ONLINE) {
			onlinePlayer = new OnlinePlayer(Players.ONLINE);
			this.addPlayer(onlinePlayer);
		} else {
			AIPlayer playerAI = new AIPlayer(Players.AI, difficulty);
			this.addPlayer(playerAI);
		}

		currentPlayerEnum = Players.PLAYER1;
		this.setCurrentPlayer(currentPlayerEnum);
		this.getCurrentPlayer().onTurn();
		
		SoundManager.setBackgroundMusic("music/menu_atmosphere.wav");
	}

	@Override
	public void onTick(double delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		destroyed = true;
		for (int i = 0; i < this.getSceneObjects().size(); i++)
			this.getSceneObjects().get(i).destroy();
		
		if(difficulty == Difficulties.ONLINE) {
			serverSockets.onDestroy();
			//serverSockets.stop();
		}
	}
	
	public boolean checkFinished() {
		boolean victory = Quarto.checkEnd(sockets);
		this.setFinished(victory);
		if(victory)
			this.onFinished(this.getCurrentPlayer());
		return victory;
	}
	
	public void nextPlayer(Piece pieceObject) {
		if(currentPlayerEnum == Players.PLAYER1)
			if(difficulty == Difficulties.PVP)
				currentPlayerEnum = Players.PLAYER2;
			else if(difficulty == Difficulties.ONLINE)
				currentPlayerEnum = Players.ONLINE;
			else
				currentPlayerEnum = Players.AI;
		else
			currentPlayerEnum = Players.PLAYER1;
		this.setCurrentPlayer(currentPlayerEnum);
		this.getCurrentPlayer().setSelectedPiece(pieceObject);
		this.getCurrentPlayer().setPieceSelected(true);
		this.getCurrentPlayer().setSocketSelected(false);
		this.getCurrentPlayer().onTurn();
	}
	
	public void onPieceSelected(Piece pieceObject, boolean userClicked) {
		if(!this.getCurrentPlayer().isPieceSelected() && !this.isFinished()) {
			if(userClicked && getCurrentPlayerEnum() != Players.PLAYER1 && getCurrentPlayerEnum() != Players.PLAYER2)
				return;
			if(difficulty == Difficulties.ONLINE && getCurrentPlayerEnum() == Players.PLAYER1 && userClicked)
				this.getServerSockets().sendMessage(pieceObject.getProperties());
			this.getCurrentPlayer().onPieceSelected(pieceObject);
		}
	}
	
	public void onSocketSelected(Socket socketObject, boolean userClicked) {
		if(!this.getCurrentPlayer().isSocketSelected() && !this.isFinished()) {
			if(userClicked && getCurrentPlayerEnum() != Players.PLAYER1 && getCurrentPlayerEnum() != Players.PLAYER2)
				return;
			if(difficulty == Difficulties.ONLINE && getCurrentPlayerEnum() == Players.PLAYER1 && userClicked)
				this.getServerSockets().sendMessage(socketObject.getGridPosition());
			this.getCurrentPlayer().onSocketSelected(socketObject);
		}
	}
	
	public void onFinished(Player winningPlayer) {
		SoundManager.onStop(SoundManager.getBackgroundMusic());
		SoundManager.onPlay("effects/victory.wav");
		gameBoard.showFireworks();
		
		DataManager.getDataFile("statistics").def(difficulty.toString() + "_PLAYED", 0);
		DataManager.getDataFile("statistics").add(difficulty.toString() + "_PLAYED", Integer.parseInt(DataManager.getDataFile("statistics").get(difficulty.toString() + "_PLAYED")) + 1);
		
		if(winningPlayer.getEnum() == Players.PLAYER1) {
			DataManager.getDataFile("statistics").def(difficulty.toString() + "_WON", 0);
			DataManager.getDataFile("statistics").add(difficulty.toString() + "_WON", Integer.parseInt(DataManager.getDataFile("statistics").get(difficulty.toString() + "_WON")) + 1);
		}
		
		for (HashMap.Entry<Players, Player> entry: this.getPlayers().entrySet())
			entry.getValue().onFinished(winningPlayer);
	}
	
	public void onServerSocketKilled() {
		if(!destroyed)
			topBar.getHomeButton().onLeftClickReleased();
	}
	
	public Piece getPieceByProperties(PieceProperties properties) {
		int j = 0;
		for (int i = 0; i < pieces.length; i++)
			if(pieces[i].getProperties().isDark() == properties.isDark() && pieces[i].getProperties().isFull() == properties.isFull() && pieces[i].getProperties().isSquare() == properties.isSquare() && pieces[i].getProperties().isTall() == properties.isTall())
				j = i;
		return pieces[j];
	}
	
	public QuartoPlayer getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Players playerEnum) {
		this.currentPlayer = (QuartoPlayer) this.getPlayers().get(playerEnum);
	}

	public Players getCurrentPlayerEnum() {
		return currentPlayerEnum;
	}

	public void setCurrentPlayerEnum(Players currentPlayerEnum) {
		this.currentPlayerEnum = currentPlayerEnum;
	}

	public Socket[][] getSockets() {
		return sockets;
	}

	public void setSockets(Socket[][] sockets) {
		this.sockets = sockets;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	public TopBar getTopBar() {
		return topBar;
	}

	public ServerSockets getServerSockets() {
		return serverSockets;
	}

	public void setServerSockets(ServerSockets serverSockets) {
		this.serverSockets = serverSockets;
	}

}
