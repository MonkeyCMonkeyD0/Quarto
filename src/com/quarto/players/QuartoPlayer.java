package com.quarto.players;

import com.quarto.engine.animations.MovingAnimation;
import com.quarto.engine.core.Player;
import com.quarto.engine.managers.SoundManager;
import com.quarto.engine.utilities.Vector2D;
import com.quarto.enums.Players;
import com.quarto.objects.board.Piece;
import com.quarto.objects.board.Socket;
import com.quarto.scenes.BoardScene;

public class QuartoPlayer extends Player {

	private boolean socketSelected = false;
	private boolean pieceSelected = false;

	private Piece selectedPiece;
	private Socket selectedSocket;
	
	public QuartoPlayer(Players playerEnum) {
		super(playerEnum);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTurn() {
		// TODO Auto-generated method stub
		((BoardScene) getScene()).getTopBar().getTurnImage().setTexturePath("board/topbar_text/" + this.getEnum().toString() + ".png");
		
		if(this.isPieceSelected())
			this.onSelectSocket();
		else
			this.onSelectPiece();
	}

	@Override
	public void onFinished(Player winningPlayer) {
		// TODO Auto-generated method stub
	}
	
	public void onSelectSocket() {
		
	}
	
	public void onSelectPiece() {
		
	}
	
	public void endTurn() {
		((BoardScene) this.getScene()).nextPlayer(this.getSelectedPiece());
	}
	
	public void onPieceSelected(Piece pieceObject) {
		this.setSelectedPiece(pieceObject);
		pieceObject.setSelected(true);
		pieceObject.getAnimation("sizeSelected").setPlaying(true);
		pieceObject.getAnimation("colorSelected").setPlaying(true);
		this.setPieceSelected(true);
		this.endTurn();
	}
	
	public void onSocketSelected(Socket socketObject) {
		this.onSocketSelected(socketObject.getGridPosition());
		this.setSelectedSocket(socketObject);
		this.setSocketSelected(true);
		if(this.isPieceSelected()) {
			this.getSelectedPiece().getAnimation("sizeSelected").reset();
			this.getSelectedPiece().getAnimation("colorSelected").reset();
			socketObject.setPiece(this.getSelectedPiece());
			this.getSelectedPiece().setPlaced(true);
			SoundManager.onPlay("effects/move.wav");
			this.getSelectedPiece().addAnimation("movingAnimation", new MovingAnimation(.3f, this.getSelectedPiece(), socketObject.getPosition().addR(socketObject.getSize() / 2).subR(this.getSelectedPiece().getSize().getX() / 2), true) {
				
				@Override
				public void onFinished() {
					// TODO Auto-generated method stub
					setSelectedPiece(null);
					setPieceSelected(false);
					if(!((BoardScene) getScene()).checkFinished())
						onSelectPiece();
				}
				
			});
		}
	}

	public void onSocketSelected(Vector2D gridPosition) {}

	public boolean isSocketSelected() {
		return socketSelected;
	}

	public void setSocketSelected(boolean socketSelected) {
		this.socketSelected = socketSelected;
	}

	public boolean isPieceSelected() {
		return pieceSelected;
	}

	public void setPieceSelected(boolean pieceSelected) {
		this.pieceSelected = pieceSelected;
	}

	public Piece getSelectedPiece() {
		return selectedPiece;
	}

	public void setSelectedPiece(Piece selectedPiece) {
		this.selectedPiece = selectedPiece;
	}

	public Socket getSelectedSocket() {
		return selectedSocket;
	}

	public void setSelectedSocket(Socket selectedSocket) {
		this.selectedSocket = selectedSocket;
	}

}
