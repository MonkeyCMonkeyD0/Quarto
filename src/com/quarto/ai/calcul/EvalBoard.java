package com.quarto.ai.calcul;

import com.quarto.utilities.PieceProperties;

public class EvalBoard {

	private static boolean[] stat = new boolean[4];
	private static boolean[] statTmp = new boolean[4];
	private static byte nbPiece;
	private static short somme;
	private static PieceProperties[] tab1 = new PieceProperties[4];
	private static PieceProperties[] tab2 = new PieceProperties[4];
	private static byte i,j,k,n;

	
	
	public static short evalBoard(PieceProperties[][] plateau) {
		somme = 0;
		for (i = 0; i < 4; i++) {
			tab1[i] = plateau[i][i];
			tab2[i] = plateau[i][3-i];
		}
		somme += evalCol(tab1);
		somme += evalCol(tab2);
		for (i = 0; i < 4; i++) {
			for (j = 0; j< 4; j++) {
				tab1[j] = plateau[i][j];
				tab2[j] = plateau[j][i];
			}
			somme += evalCol(tab1);
			somme += evalCol(tab2);
		}
		for (i = 0; i < 3; i++) 
			for (j = 0; j < 3; j++) {
				tab1[0] = plateau[i][j];
				tab1[1] = plateau[i + 1][j];
				tab1[2] = plateau[i][j + 1];
				tab1[3] = plateau[i + 1][j + 1];
				somme += evalCol(tab1);
			}
		return somme;
	}
	
	private static void nbPieceCol(PieceProperties[] colPiece) {
		nbPiece = 0;
		for (PieceProperties cell : colPiece)
			if (cell != null)
				nbPiece++;
	}
	
	
	private static short evalCol(PieceProperties[] colPiece) {
		nbPieceCol(colPiece);
		if (nbPiece > 1) {
			k = n = 0;
			stat[0] = stat[1] = stat[2] = stat[3] = true;
			
			while(colPiece[n] == null)
				n++;

			statTmp[0] = colPiece[n].isDark();
			statTmp[1] = colPiece[n].isTall();
			statTmp[2] = colPiece[n].isSquare();
			statTmp[3] = colPiece[n].isFull();
			
			for(n++; n < 4; n++) {
				if(colPiece[n] != null) {
					stat[0] = stat[0] && colPiece[n].isDark() == statTmp[0];
					stat[1] = stat[1] && colPiece[n].isTall() == statTmp[1];
					stat[2] = stat[2] && colPiece[n].isSquare() == statTmp[2];
					stat[3] = stat[3] && colPiece[n].isFull() == statTmp[3];
				}
			}

			for	(boolean caract : stat)
				if(caract)
					k++;
			switch (nbPiece) {
				case 2: return (short) (k * 3); //coeffs
				case 3: return (short) (k * 10);
				case 4: return (short) (k * 2000);
			}
		} else if (nbPiece == 1)
			return 1;
		return 0;
	}
	
}
