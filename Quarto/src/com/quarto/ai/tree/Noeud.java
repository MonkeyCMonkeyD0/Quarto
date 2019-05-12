package com.quarto.ai.tree;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

import com.quarto.Quarto;
import com.quarto.ai.calcul.EvalBoard;
import com.quarto.ai.calcul.NextBoard;
import com.quarto.utilities.PieceProperties;

public class Noeud {

	private Deque<Short> path = new LinkedList<Short>(); //l'adresse du noeud dans l'arbre
	private byte[] position = new byte[2]; //la postion du précédant coup
	private short index;
	private PieceProperties givenPiece; //la pièce donnée par l'adversaire
	private short valBoard; //la valeur attribue au plateau pour le MinMax
	private byte profondeur; //profondeur du noeud par rapport a la racine
	private ArrayList<Noeud> fils; //Tableau dynamique des fils
	private boolean feuille;
	

	public Noeud(PieceProperties[][] board, byte[] position, PieceProperties givenPiece, byte profondeur, boolean feuille, Deque<Short> path) {
		
		this.setFeuille(feuille);
		this.setPath(path);
		this.setPosition(position);
		this.setGivenPiece(givenPiece);
		this.setProfondeur(profondeur);

		if (!isFeuille()) {
			fils = new ArrayList<Noeud>();
			ArrayList<PieceProperties> pieces = NextBoard.nextPieces(board, getGivenPiece());
			ArrayList<PieceProperties[][]> boardfils = NextBoard.nextBoards(board,getGivenPiece());
			index = 0;
			for (byte i = 0; i < boardfils.size(); i++) {
				byte[] nextPos = NextBoard.positionPiece(board , boardfils.get(i));
				if (Quarto.checkEnd(boardfils.get(i)) || getProfondeur() + 1 >= Arbre.getProfondeurMax()) {
					Deque<Short> newpath = new LinkedList<Short>(path);
					newpath.add((short) (index++));
					addFils(new Noeud(boardfils.get(i), nextPos, null, (byte) (getProfondeur() + 1), true, newpath));
					newpath = null;
				} else
					for (byte j = 0; j < pieces.size(); j++) {
						Deque<Short> newpath = new LinkedList<Short>(path);
						newpath.add((short) (index++));
						addFils(new Noeud(boardfils.get(i), nextPos ,pieces.get(j),(byte) (getProfondeur() + 1), false, newpath));
						newpath = null;
						pieces.remove(j);
					}
				boardfils.remove(i);
			}
			board = null;
			pieces = null;
			boardfils = null;
			fils.trimToSize();
		}
		else
			setValBoard((short) ((getProfondeur() % 2 == 0 ? -1 : 1) * EvalBoard.evalBoard(board) * (Arbre.getProfondeurMax() - getProfondeur() + 1)));
	}
	
	public Noeud(Noeud noeud) {
		this.setPath(noeud.getPath());
		this.setPosition(noeud.getPosition());
		this.setFeuille(noeud.isFeuille());
		this.setFils(noeud.getFils());
		this.setGivenPiece(noeud.getGivenPiece());
		this.setPath(noeud.getPath());
		this.setProfondeur(noeud.getProfondeur());
		this.setValBoard(noeud.getValBoard());
	}
	
	public Noeud() {
		
	}
	

	public Deque<Short> getPath() {
		return path;
	}
	public void setPath(Deque<Short> path) {
		this.path = path;
	}
	
	public byte[] getPosition() {
		return position;
	}
	public void setPosition(byte[] position) {
		this.position = position;
	}

	public PieceProperties getGivenPiece() {
		return givenPiece;
	}
	public void setGivenPiece(PieceProperties givenPiece) {
		this.givenPiece = givenPiece;
	}
	
	public short getValBoard() {
		return valBoard;
	}
	public void setValBoard(short valBoard) {
		this.valBoard = valBoard;
	}
	
	public byte getProfondeur() {
		return profondeur;
	}
	public void setProfondeur(byte profondeur) {
		this.profondeur = profondeur;
	}
	
	public boolean isFeuille() {
		return feuille;
	}
	public void setFeuille(boolean feuille) {
		this.feuille = feuille;
	}
	
	public ArrayList<Noeud> getFils() {
        return fils;
    }
    public void setFils(ArrayList<Noeud> fils) {
        this.fils = fils;
    }
    public void addFils(Noeud fils) {
    	this.fils.add(fils);
    }
    public void rmFils(short index) {
    	this.fils.remove(index);
    }
    
    
    public void log() {
    	System.out.println("--------------------Noeud------------------------");
    	System.out.println(this.getPath().toString());
    	System.out.println("Position : " + this.getPosition()[0] + " , " + this.getPosition()[1]);
    	System.out.print("givenPiece: ");
    	try {
    		getGivenPiece().log();
    	} catch	(Exception e) {}
    	System.out.print("\nfeuille? " + isFeuille() + " / valBoard: " + getValBoard() + " / profondeur: " + getProfondeur() + "\n");
    	System.out.println("-------------------------------------------------");
    }
	
    
	public void createSonsPathR() {
		if (!isFeuille()) {
			for (short i = 0; i < getFils().size(); i++) {
				Deque<Short> sonPath = new LinkedList<Short>(getPath());
				sonPath.add(i);
				getFils().get(i).setPath(sonPath);
				getFils().get(i).createSonsPathR();
			}
		}
	}
	
	public Noeud getNoeudR(Deque<Short> newpath) {
		if (!newpath.isEmpty() && !isFeuille() && newpath.peek() < getFils().size())
			return getFils().get(newpath.poll()).getNoeudR(newpath);
		return this;
	}

    public void logR() {
    	log();
    	if (!isFeuille())
    		for (Noeud fils : getFils())
    			fils.logR();
    }

}
