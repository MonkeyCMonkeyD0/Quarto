package com.quarto.utilities;

import java.io.Serializable;
import java.util.ArrayList;

public class PieceProperties implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5168018439054232933L;
	private boolean dark;
	private boolean square;
	private boolean full;
	private boolean tall;
	
	private static final PieceProperties[] allPieces = allPiece();
	
	
	public PieceProperties() {}
	
	public PieceProperties(PieceProperties piece) {
		this.setDark(piece.isDark());
		this.setFull(piece.isFull());
		this.setSquare(piece.isSquare());
		this.setTall(piece.isTall());
	}
	
	public PieceProperties(boolean d,boolean f,boolean s,boolean t) {
		this.setDark(d);
		this.setFull(f);
		this.setSquare(s);
		this.setTall(t);
	}
	
	public boolean isDark() {
		return dark;
	}
	
	public void setDark(boolean dark) {
		this.dark = dark;
	}
	
	public boolean isSquare() {
		return square;
	}
	
	public void setSquare(boolean square) {
		this.square = square;
	}
	
	public boolean isFull() {
		return full;
	}
	
	public void setFull(boolean full) {
		this.full = full;
	}
	
	public boolean isTall() {
		return tall;
	}
	
	public void setTall(boolean tall) {
		this.tall = tall;
	}

	public static PieceProperties[] getAllpieces() {
		return allPieces;
	}

	private static PieceProperties[] allPiece() {
		PieceProperties[] allPiece = new PieceProperties[16];
		for (byte i = 0; i < 16; i++) {
			boolean[] bits = new boolean[4];
		    for (int j = 0; j < 4; j++) {
		        bits[j] = (i & (1 << j)) != 0;
		    }
			PieceProperties piece = new PieceProperties(bits[0], bits[1], bits[2], bits[3]);
			allPiece[i] = piece;
		}
		return allPiece;
	}
	
	public void log() {		
		System.out.print("(" + (isDark() ? "noir" : "blanche") + ","+ (isSquare() ? "carre" : "rond") + ","+ (isFull() ? "plein" : "troue") + ","+ (isTall() ? "grand" : "petit") + ")");
	}
	
	public static PieceProperties[][] copyBoard(PieceProperties[][] board) {
		PieceProperties[][] newBoard = new PieceProperties[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				if (board[i][j] != null)
					newBoard[i][j] = board[i][j];
				else 
					newBoard[i][j] = null;
			}
		return newBoard;
	}
	
	public boolean equals(PieceProperties piece) {
		return isDark() == piece.isDark() && isSquare() == piece.isSquare() && isFull() == piece.isFull() && isTall() == piece.isTall();
	}
	
	public ArrayList<PieceProperties> remove(ArrayList<PieceProperties> pieces) {
		for (int i = 0; i < pieces.size(); i++)
			if(pieces.get(i).equals(this))
				pieces.remove(i);
		return pieces;
	}
	
	public static boolean equals(PieceProperties[][] b1, PieceProperties[][] b2) {
		boolean result = true;
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				if (b1[i][j] != null ^ b2[i][j] != null)
					result = false;
				if (b1[i][j] != null && b2[i][j] != null && !b1[i][j].equals(b2[i][j]))
					result = false;
			}
		return result;
	}
	
	public static int indexOf(ArrayList<PieceProperties[][]> array, PieceProperties[][] board) {
		for (int i = 0; i < array.size(); i++) 
			if (PieceProperties.equals(array.get(i),(board)))
				return i;
		return -1;
	}
	
	public static void log(PieceProperties[][] board) {
		for (PieceProperties[] row : board) {
    		for (PieceProperties cell : row) {
    			if(cell != null)
    				cell.log();
    			else
    				System.out.print("null");
    	    	System.out.print(" - ");
    		}
        	System.out.print("\n");
    	}
	}

}
