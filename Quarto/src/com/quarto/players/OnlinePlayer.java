package com.quarto.players;

import com.quarto.engine.GameEngine;
import com.quarto.enums.Players;
import com.quarto.scenes.BoardScene;
import com.quarto.utilities.PieceProperties;

public class OnlinePlayer extends QuartoPlayer {
	
	public OnlinePlayer(Players playerEnum) {
		super(playerEnum);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onTurn() {
		// TODO Auto-generated method stub
		GameEngine.Log(this.getEnum() + ": C'est mon tour!!");
		((BoardScene) getScene()).getTopBar().getTurnImage().setTexturePath("board/topbar_text/" + this.getEnum().toString() + ".png");
		
		if(this.isPieceSelected())
			this.onSelectSocket();
		else
			this.onSelectPiece();
		
		if(isPieceSelected())
			((BoardScene) getScene()).getServerSockets().sendMessage(getSelectedPiece().getProperties());
	}
	
	public void onSelectSocket(int x, int y) {
		((BoardScene) getScene()).getSockets()[x][y].onSelect(false);
	}
	
	public void onSelectPiece(PieceProperties pieceProperties) {
		((BoardScene) getScene()).getPieceByProperties(pieceProperties).onSelect(false);
	}


}
