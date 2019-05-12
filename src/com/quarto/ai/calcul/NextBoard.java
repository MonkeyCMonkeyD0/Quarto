package com.quarto.ai.calcul;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import com.quarto.utilities.PieceProperties;

public class NextBoard {
	
	
	private static Queue<byte[]> coordvide = new LinkedList<byte[]>();
	private static PieceProperties[][] fils;
	private static ArrayList<PieceProperties[][]> nextBoards;
	private static PieceProperties[][] rotation = new PieceProperties[4][4];
	private static ArrayList<PieceProperties> pieces;
	private static byte i,j,n;


	public static ArrayList<PieceProperties[][]> getNextBoards() {
		return nextBoards;
	}
	public static void setNextBoards(ArrayList<PieceProperties[][]> nextBoards) {
		NextBoard.nextBoards = nextBoards;
	}
	public static void addNextBoards(PieceProperties[][] nextBoard) {
		NextBoard.nextBoards.add(nextBoard);
	}
	public static void rmNextBoards(int index) {
		NextBoard.nextBoards.remove(index);
	}
	
	
	private static void getFreeSpace(PieceProperties[][] board) {
		coordvide.clear();
		for(i = 0; i < 4; i++)
			for(j = 0; j < 4; j++)
				if (board[i][j] == null)
					coordvide.add(new byte[] {i,j});
	}
	
	private static void createAllSons(PieceProperties[][] board, PieceProperties piece) {
		NextBoard.nextBoards = new ArrayList<PieceProperties[][]>();
		for (byte[] coord : coordvide) {
			fils = PieceProperties.copyBoard(board);
			fils[coord[0]][coord[1]] = piece;
			addNextBoards(fils);
		}
		getNextBoards().trimToSize();
	}
	
	public static void transpoG(PieceProperties[][] board) {
		for (i = 1; i < 4; i++)
			for (j = 0; j < i; j++) {
				rotation[i][j] = board[j][i];
				rotation[j][i] = board[i][j];
			}
		for (i = 0; i < 4; i++)
			rotation[i][i] = board[i][i];
	}
	
	public static void transpoD(PieceProperties[][] board) {
		for (i = 0; i < 3; i++)
			for (j = 0; i + j <= 2; j++) {
				rotation[i][j] = board[3 - j][3 - i];
				rotation[3 - j][3 - i] = board[i][j];
			}
	}
	
	public static void rotatG(PieceProperties[][] board) {
		for (i = 0; i < 4; i++)
			for (j = 0; j < 4; j++)
				rotation[i][j] = board[3 - j][i];
	}
	
	public static void rotat(PieceProperties[][] board) {
		rotatG(board);
		rotatG(rotation);
	}
	
	public static void rotatD(PieceProperties[][] board) {
		for (i = 0; i < 4; i++)
			for (j = 0; j < 4; j++)
				rotation[i][j] = board[j][3 - i];
	}
	
	private static void compression() {
		if (coordvide.size() >= 13)
			for (n = 0; n < getNextBoards().size(); n++)
				if (getNextBoards().get(n) != null) {
					transpoG(getNextBoards().get(n));
					if (PieceProperties.indexOf(getNextBoards(),rotation) != -1 && PieceProperties.indexOf(getNextBoards(),rotation) != n) 
						NextBoard.rmNextBoards(n);

					transpoD(getNextBoards().get(n));
					if (PieceProperties.indexOf(getNextBoards(),rotation) != -1 && PieceProperties.indexOf(getNextBoards(),rotation) != n) 
						NextBoard.rmNextBoards(n);

					rotatG(getNextBoards().get(n));
					if (PieceProperties.indexOf(getNextBoards(),rotation) != -1 && PieceProperties.indexOf(getNextBoards(),rotation) != n) 
						NextBoard.rmNextBoards(n);

					rotat(getNextBoards().get(n));
					if (PieceProperties.indexOf(getNextBoards(),rotation) != -1 && PieceProperties.indexOf(getNextBoards(),rotation) != n) 
						NextBoard.rmNextBoards(n);

					rotatD(getNextBoards().get(n));
					if (PieceProperties.indexOf(getNextBoards(),rotation) != -1 && PieceProperties.indexOf(getNextBoards(),rotation) != n) 
						NextBoard.rmNextBoards(n);
				}
		getNextBoards().trimToSize();
		Collections.shuffle(getNextBoards());

	}

	public static ArrayList<PieceProperties[][]> nextBoards(PieceProperties[][] actualBoard, PieceProperties newpiece) {
		NextBoard.getFreeSpace(actualBoard);
		NextBoard.createAllSons(actualBoard, newpiece);
		NextBoard.compression();
		return getNextBoards();
	}

	public static ArrayList<PieceProperties> nextPieces(PieceProperties[][] actualBoard, PieceProperties newpiece) {
		pieces = new ArrayList<PieceProperties>(Arrays.asList(PieceProperties.getAllpieces()));
		for (PieceProperties[] col : actualBoard)
			for (PieceProperties piece : col)
				if (piece != null)
					pieces = piece.remove(pieces);
		pieces = newpiece.remove(pieces);
		pieces.trimToSize();
		return pieces;
	}

	public static byte[] positionPiece(PieceProperties[][] b1, PieceProperties[][] b2) {
		getFreeSpace(b1);
		for (byte[] coord : coordvide)
			if (b2[coord[0]][coord[1]] != null)
				return coord;
		return null;
	}

}
