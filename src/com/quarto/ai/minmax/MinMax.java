package com.quarto.ai.minmax;

import java.util.Deque;
import java.util.LinkedList;

import com.quarto.ai.AIPlayer;
import com.quarto.ai.tree.Noeud;

public class MinMax {
	

	/*function minimax(node, depth, maximizingPlayer) is
    if depth = 0 or node is a terminal node then
        return the heuristic value of node
    if maximizingPlayer then
        value := -∞
        for each child of node do
            value := max(value, minimax(child, depth - 1, FALSE))
        return value
    else (* minimizing player *)
        value := +∞
        for each child of node do
            value := min(value, minimax(child, depth - 1, TRUE))
        return value
    */
	
	public static Noeud minMax(Noeud noeud) {
		if (noeud.isFeuille())
			return noeud;
		Noeud neganoeud = new Noeud();
		if (noeud.getProfondeur() % 2 == 0) {
			neganoeud.setValBoard((short) Short.MIN_VALUE);
			for (Noeud child : noeud.getFils()) {
				Noeud newnoeud = minMax(child);
				if (newnoeud.getValBoard() > neganoeud.getValBoard() && (newnoeud.getValBoard() <= AIPlayer.getDifficulty().getValue() || newnoeud.getValBoard() > 1000 || neganoeud.getValBoard() == (int) Integer.MIN_VALUE))
					neganoeud = newnoeud;
			}
		} else {
			neganoeud.setValBoard((short) Short.MAX_VALUE);
			for (Noeud child : noeud.getFils()) {
				Noeud newnoeud = minMax(child);
				if (newnoeud.getValBoard() < neganoeud.getValBoard() && (newnoeud.getValBoard() >= -AIPlayer.getDifficulty().getValue() || newnoeud.getValBoard() < -1000 || neganoeud.getValBoard() == (int) Integer.MAX_VALUE))
					neganoeud = newnoeud;
			}
		}
		return neganoeud;
	}
	
	public static Deque<Short> minMaxPath(Noeud noeud){
		Deque<Short> list = new LinkedList<Short>(noeud.getPath());
		while (list.size() > 2)
			list.removeLast();
		return list;
	}
	
}
