package com.quarto.ai.tree;

import java.util.Deque;
import java.util.LinkedList;

import com.quarto.utilities.PieceProperties;

public class Arbre {

	private Noeud racine;
	private static byte profondeurMax;
	
	
	public Arbre(byte profmax, PieceProperties[][] board, PieceProperties piece) {
		setProfondeurMax(profmax);
		Deque<Short> path = new LinkedList<Short>();
		path.add((short) 0);
		byte[] pos = {0,0};
		setRacine(new Noeud(board, pos, piece,(byte) 0, false, path));
	}


	public Noeud getRacine() {
		return racine;
	}
	public void setRacine(Noeud racine) {
		this.racine = racine;
	}

	public static byte getProfondeurMax() {
		return Arbre.profondeurMax;
	}
	public static void setProfondeurMax(byte profondeurMax) {
		Arbre.profondeurMax = profondeurMax;
	}
	
	
	public void createPaths() {
		Deque<Short> path = new LinkedList<Short>();
		path.add((short) 0);
		getRacine().setPath(path);
		getRacine().createSonsPathR();
	}
	
	public Noeud pathToNoeud(Deque<Short> path) {
		Deque<Short> newpath = new LinkedList<Short>(path);
		if (newpath.pollFirst() == 0)
			return getRacine().getNoeudR(newpath);
		else
			System.out.println("Error bad path");
			return getRacine();
	}
	
	public Deque<Short> noeudToPath(Noeud noeud) {
		return noeud.getPath();
	}
	
	public Noeud getParent(Deque<Short> pathfils) {
		pathfils.pollLast();
		return getRacine().getNoeudR(pathfils);
	}
	
	public void logBranche(Deque<Short> path) {
		Deque<Short> newpath = new LinkedList<Short>(path);
		while (!newpath.isEmpty()) {
			pathToNoeud(newpath).log();
			newpath.removeLast();
		}		
	}
	
	public void log() {
		getRacine().logR();
	}
	
}
