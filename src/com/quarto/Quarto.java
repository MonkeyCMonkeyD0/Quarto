package com.quarto;

import com.quarto.engine.GameEngine;
import com.quarto.engine.engines.AnimationsEngine;
import com.quarto.engine.engines.GraphicsEngine;
import com.quarto.engine.engines.InputsEngine;
import com.quarto.engine.engines.PhysicsEngine;
import com.quarto.engine.interfaces.Callback;
import com.quarto.engine.managers.SoundManager;
import com.quarto.engine.managers.TextureManager;
import com.quarto.engine.utilities.Vector2D;
import com.quarto.enums.Difficulties;
import com.quarto.enums.Scenes;
import com.quarto.objects.board.Piece;
import com.quarto.objects.board.Socket;
import com.quarto.scenes.BoardScene;
import com.quarto.scenes.LoadingScene;
import com.quarto.scenes.MenuScene;
import com.quarto.utilities.PieceProperties;

public class Quarto extends GameEngine {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Quarto gameEngine = new Quarto();
		gameEngine.onInit();
	}
	
	LoadingScene loadingScene;
	MenuScene menuScene;
	BoardScene boardScene;
	
	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		TextureManager.setTheme("bois");
		
		this.setPixelsPerMeter(100);
		this.setWindowSize(new Vector2D(6, (float) 7.1));

		menuScene = new MenuScene();
		this.addScene(Scenes.MENU, menuScene);
		this.setCurrentScene(Scenes.MENU);

		this.addEngine(new PhysicsEngine());
		this.addEngine(new GraphicsEngine());
		this.addEngine(new InputsEngine());
		this.addEngine(new AnimationsEngine());
	}
	
	public void onLaunchGame(Difficulties difficulty, String hostIP) {
		loadingScene = new LoadingScene();
		this.addScene(Scenes.LOADING, loadingScene);
		
		menuScene.fadeOut(new Callback() {
			@Override
			public void onCallback() {
				SoundManager.onStop(SoundManager.getBackgroundMusic());
				onLoadGame(difficulty, hostIP);
				setCurrentScene(Scenes.LOADING);
				menuScene.onBackButtonClicked();
			}
		});
	}

	public void onLoadGame(Difficulties difficulty, String hostIP) {
		boardScene = new BoardScene(difficulty, hostIP);
		loadingScene.setLoadingScene(Scenes.BOARD, boardScene);
	}
	
	private static PieceProperties[] makePiecesArray(PieceProperties[] socketsArray, PieceProperties socketObject1, PieceProperties socketObject2, PieceProperties socketObject3, PieceProperties socketObject4) {
		socketsArray[0] = socketObject1;
		socketsArray[1] = socketObject2;
		socketsArray[2] = socketObject3;
		socketsArray[3] = socketObject4;
		return socketsArray;
	}
	
	private static PieceProperties[] makePiecesArray(PieceProperties[] socketsArray, Piece socketObject1, Piece socketObject2, Piece socketObject3, Piece socketObject4) {
		socketsArray[0] = socketObject1 == null ? null : socketObject1.getProperties();
		socketsArray[1] = socketObject2 == null ? null : socketObject2.getProperties();
		socketsArray[2] = socketObject3 == null ? null : socketObject3.getProperties();
		socketsArray[3] = socketObject4 == null ? null : socketObject4.getProperties();
		return socketsArray;
	}
	
	public static boolean checkEnd(Socket[][] sockets) {
		boolean victory = false;
		boolean oldVictory = false;
		PieceProperties[] piecesArray = new PieceProperties[4];
		Piece[] winningPiecesArray = new Piece[4];
		for (int i = 0; i < sockets.length; i++) {
			piecesArray = makePiecesArray(piecesArray, sockets[i][0].getPiece(), sockets[i][1].getPiece(), sockets[i][2].getPiece(), sockets[i][3].getPiece());
			oldVictory = victory;
			victory = checkCommonProperty(piecesArray) || victory;
			if(oldVictory != victory) {
				winningPiecesArray[0] = sockets[i][0].getPiece();
				winningPiecesArray[1] = sockets[i][1].getPiece();
				winningPiecesArray[2] = sockets[i][2].getPiece();
				winningPiecesArray[3] = sockets[i][3].getPiece();
			}
		}
		for (int j = 0; j < sockets.length; j++) {
			piecesArray = makePiecesArray(piecesArray, sockets[0][j].getPiece(), sockets[1][j].getPiece(), sockets[2][j].getPiece(), sockets[3][j].getPiece());
			oldVictory = victory;
			victory = checkCommonProperty(piecesArray) || victory;
			if(oldVictory != victory) {
				winningPiecesArray[0] = sockets[0][j].getPiece();
				winningPiecesArray[1] = sockets[1][j].getPiece();
				winningPiecesArray[2] = sockets[2][j].getPiece();
				winningPiecesArray[3] = sockets[3][j].getPiece();
			}
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				piecesArray = makePiecesArray(piecesArray, sockets[i][j].getPiece(), sockets[i + 1][j].getPiece(), sockets[i][j + 1].getPiece(), sockets[i + 1][j + 1].getPiece());
				oldVictory = victory;
				victory = checkCommonProperty(piecesArray) || victory;
				if(oldVictory != victory) {
					winningPiecesArray[0] = sockets[i][j].getPiece();
					winningPiecesArray[1] = sockets[i + 1][j].getPiece();
					winningPiecesArray[2] = sockets[i][j + 1].getPiece();
					winningPiecesArray[3] = sockets[i + 1][j + 1].getPiece();
				}
			}
		}
		piecesArray = makePiecesArray(piecesArray, sockets[0][0].getPiece(), sockets[1][1].getPiece(), sockets[2][2].getPiece(), sockets[3][3].getPiece());
		oldVictory = victory;
		victory = checkCommonProperty(piecesArray) || victory;
		if(oldVictory != victory) {
			winningPiecesArray[0] = sockets[0][0].getPiece();
			winningPiecesArray[1] = sockets[1][1].getPiece();
			winningPiecesArray[2] = sockets[2][2].getPiece();
			winningPiecesArray[3] = sockets[3][3].getPiece();
		}
		piecesArray = makePiecesArray(piecesArray, sockets[0][3].getPiece(), sockets[1][2].getPiece(), sockets[2][1].getPiece(), sockets[3][0].getPiece());
		oldVictory = victory;
		victory = checkCommonProperty(piecesArray) || victory;
		if(oldVictory != victory) {
			winningPiecesArray[0] = sockets[0][3].getPiece();
			winningPiecesArray[1] = sockets[1][2].getPiece();
			winningPiecesArray[2] = sockets[2][1].getPiece();
			winningPiecesArray[3] = sockets[3][0].getPiece();
		}
		
		if(victory) {
			for (int i = 0; i < winningPiecesArray.length; i++) {
				winningPiecesArray[i].getAnimation("sizeSelected").setPlaying(true);
				winningPiecesArray[i].getAnimation("colorSelected").setPlaying(true);
			}
		}
		
		return victory;
	}
	
	public static boolean checkEnd(PieceProperties[][] pieceProperties) {
		boolean victory = false;
		PieceProperties[] piecesArray = new PieceProperties[4];
		for (int i = 0; i < pieceProperties.length; i++) {
			piecesArray = makePiecesArray(piecesArray, pieceProperties[i][0], pieceProperties[i][1], pieceProperties[i][2], pieceProperties[i][3]);
			victory = checkCommonProperty(piecesArray) || victory;
		}
		for (int j = 0; j < pieceProperties.length; j++) {
			piecesArray = makePiecesArray(piecesArray, pieceProperties[0][j], pieceProperties[1][j], pieceProperties[2][j], pieceProperties[3][j]);
			victory = checkCommonProperty(piecesArray) || victory;
		}
		piecesArray = makePiecesArray(piecesArray, pieceProperties[0][0], pieceProperties[1][1], pieceProperties[2][2], pieceProperties[3][3]);
		victory = checkCommonProperty(piecesArray) || victory;
		piecesArray = makePiecesArray(piecesArray, pieceProperties[0][3], pieceProperties[1][2], pieceProperties[2][1], pieceProperties[3][0]);
		victory = checkCommonProperty(piecesArray) || victory;
		
		return victory;
	}
	
	public static boolean checkCommonProperty(PieceProperties[] sockets) {
		boolean darkVictory = true;
		boolean squareVictory = true;
		boolean fullVictory = true;
		boolean tallVictory = true;
		if(sockets[0] != null) {
			boolean darkRef = sockets[0].isDark();
			boolean squareRef = sockets[0].isSquare();
			boolean fullRef = sockets[0].isFull();
			boolean tallRef = sockets[0].isTall();
			for (int i = 1; i < sockets.length; i++) {
				if(sockets[i] != null) {
					darkVictory = (sockets[i].isDark() == darkRef) && darkVictory;
					squareVictory = (sockets[i].isSquare() == squareRef) && squareVictory;
					fullVictory = (sockets[i].isFull() == fullRef) && fullVictory;
					tallVictory = (sockets[i].isTall() == tallRef) && tallVictory;
				}else{
					darkVictory = false;
					squareVictory = false;
					fullVictory = false;
					tallVictory = false;
				}
			}
			return darkVictory || squareVictory || fullVictory || tallVictory;
		}
		return false;
	}
	
}
