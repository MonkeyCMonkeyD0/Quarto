package com.quarto.ai;

import java.util.Deque;
import java.util.LinkedList;

import com.quarto.ai.minmax.AlphaBeta_Minmax;
import com.quarto.ai.minmax.MinMax;
import com.quarto.ai.tree.Arbre;
import com.quarto.ai.tree.Noeud;
import com.quarto.engine.GameEngine;
import com.quarto.enums.Difficulties;
import com.quarto.enums.Players;
import com.quarto.objects.board.Socket;
import com.quarto.players.QuartoPlayer;
import com.quarto.scenes.BoardScene;
import com.quarto.utilities.PieceProperties;

public class AIPlayer extends QuartoPlayer {


	private static Difficulties difficulty;
	private byte [] nextPosition;
	private PieceProperties nextPiece;
	private static PieceProperties[][] currentBoard;
	private static PieceProperties currentPiece;
	private Arbre arbre;
	
	public AIPlayer(Players playerEnum, Difficulties difficulty) {
		super(playerEnum);
		setDifficulty(difficulty);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void onInit() {
		this.setArbre(null);
		System.gc();
	}
	
	@Override
	public void onSelectSocket() {
		GameEngine.Log(this.getEnum() + " Il faut selectionner une place dans la grille");

		((BoardScene) getScene()).getTopBar().getTurnImage().setTexturePath("board/topbar_text/" + this.getEnum().toString() + ".png");

		this.setCurrentBoard(CurrentBoard());
		this.setCurrentPiece(getSelectedPiece().getProperties());
		this.setArbre(new Arbre((byte) 4, getCurrentBoard(), getCurrentPiece()));
		nextMoves();
		
		((BoardScene) getScene()).getSockets()[nextPosition[0]][nextPosition[1]].onSelect(false);

		this.setArbre(null);
		System.gc();
	}

	@Override
	public void onSelectPiece() {
		GameEngine.Log(this.getEnum() + " Il faut selectionner une piece");
		
		((BoardScene) getScene()).getPieceByProperties(nextPiece).onSelect(false);
	}

	
	public static Difficulties getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(Difficulties difficulty) {
		AIPlayer.difficulty = difficulty;
	}

	public byte [] getNextPosition() {
		return nextPosition;
	}
	public void setNextPosition(byte [] nextPosition) {
		this.nextPosition = nextPosition;
	}

	public static PieceProperties[][] getCurrentBoard() {
		return currentBoard;
	}
	public void setCurrentBoard(PieceProperties[][] currentBoard) {
		AIPlayer.currentBoard = currentBoard;
	}
	
	public static PieceProperties getCurrentPiece() {
		return currentPiece;
	}
	public void setCurrentPiece(PieceProperties currentPiece) {
		AIPlayer.currentPiece = currentPiece;
	}
	
	public PieceProperties getNextPiece() {
		return nextPiece;
	}
	public void setNextPiece(PieceProperties piece) {
		this.nextPiece = piece;
	}

	public Arbre getArbre() {
		return arbre;
	}
	public void setArbre(Arbre arbre) {
		this.arbre = arbre;
	}
	
	public PieceProperties[][] CurrentBoard() {
		PieceProperties[][] Pboard = new PieceProperties[4][4];
		Socket[][] sockets = ((BoardScene) getScene()).getSockets();
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				if(sockets[i][j].isSelected())
					Pboard[i][j] = sockets[i][j].getPiece().getProperties();
				else
					Pboard[i][j] = null;
		return Pboard;
	}
	
	private Noeud minMax() {
		//return MinMax.minMax(getArbre().getRacine());
		return AlphaBeta_Minmax.minMaxAB(getArbre().getRacine(), (int) Integer.MIN_VALUE, (int) Integer.MAX_VALUE);
	}
	
	protected void nextMoves() {
		Deque<Short> path = new LinkedList<Short>(minMax().getPath());
		getArbre().logBranche(path);
		path = MinMax.minMaxPath(getArbre().pathToNoeud(path));
		this.setNextPiece(getArbre().pathToNoeud(path).getGivenPiece());
		this.setNextPosition(getArbre().pathToNoeud(path).getPosition());
	}
}
