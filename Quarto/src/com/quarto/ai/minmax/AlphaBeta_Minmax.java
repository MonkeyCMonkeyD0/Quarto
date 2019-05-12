package com.quarto.ai.minmax;

import com.quarto.ai.AIPlayer;
import com.quarto.ai.tree.Noeud;

public class AlphaBeta_Minmax {

	/* fonction alphabeta(nœud, α, β) α est toujours inférieur à β
	si nœud est une feuille alors
		retourner la valeur de nœud
	sinon si nœud est de type Min alors
		v = +∞
		pour tout fils de nœud faire
			v = min(v, alphabeta(fils, α, β))
			si α ≥ v alors		coupure alpha
				retourner v
			β = Min(β, v)
	sinon
		v = -∞
		pour tout fils de nœud faire
			v = max(v, alphabeta(fils, α, β))
			si v ≥ β alors		coupure beta
				retourner v
			α = Max(α, v)
	retourner v */
	
	public static Noeud minMaxAB(Noeud noeud, int alpha, int beta) {
		if (noeud.isFeuille())
			return noeud;
		Noeud neganoeud = new Noeud();
		if (noeud.getProfondeur() % 2 == 0) {
			neganoeud.setValBoard((short) Short.MIN_VALUE);
			for (Noeud child : noeud.getFils()) {
				Noeud newnoeud = minMaxAB(child, alpha, beta);
				if (newnoeud.getValBoard() > neganoeud.getValBoard() && (newnoeud.getValBoard() <= AIPlayer.getDifficulty().getValue() || newnoeud.getValBoard() > 2000 || neganoeud.getValBoard() == (short) Short.MIN_VALUE))
					neganoeud = newnoeud;
				if (newnoeud.getValBoard() >= beta) {
					noeud.rmFils(child.getPath().peekLast());
					break;
				}
				alpha = Math.max(alpha,newnoeud.getValBoard());
			}
		} else {
			neganoeud.setValBoard((short) Short.MAX_VALUE);
			for (Noeud child : noeud.getFils()) {
				Noeud newnoeud = minMaxAB(child, alpha, beta);
				if (newnoeud.getValBoard() < neganoeud.getValBoard() && (newnoeud.getValBoard() >= -AIPlayer.getDifficulty().getValue() || newnoeud.getValBoard() < -2000 || neganoeud.getValBoard() == (short) Short.MAX_VALUE))
					neganoeud = newnoeud;
				if (newnoeud.getValBoard() <= alpha) {
					noeud.rmFils(child.getPath().peekLast());
					break;
				}
				beta = Math.min(beta,newnoeud.getValBoard());
			}
		}
		return neganoeud;
	}
}