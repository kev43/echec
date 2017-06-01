/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Kevin
 */
public class Echiquier implements BoardGames {
    private Jeu[] jeux ;
    private boolean isBlancCourant;
    
    // indice 0 : BLANC
    // indice 1 : NOIR
    private int indiceJeuCourant;
    
    private String message;
    
    private boolean isCatchOk;
    private boolean isCastlingPossible;
    
    public Echiquier() {
        jeux = new Jeu[2]; // tableau de taille 2
        jeux[0]= new Jeu(Couleur.BLANC);
        jeux[1]= new Jeu(Couleur.NOIR);
        
        // Blanc commence
        isBlancCourant = true;
        indiceJeuCourant = 0;
    }
    
    public boolean isMoveOk(int xInit,
                            int yInit,
                            int xFinal,
                            int yFinal) {
        //s'il n'existe pas de piece du jeu courant aux coordonnées initiales
        if (jeux[indiceJeuCourant].getPieceName(xInit, yInit) == null){
            return false;
        }
        
        //si les coordonnées finales ne sont pas valides
        if (!Coord.coordonnees_valides(xFinal, yFinal)) {
            return false;
        }
        
        //si les coordonnées finales sont égales aux initiales
        if ( (xInit == xFinal) && (yInit == yFinal)) {
            return false;
        }
        
        // si la position finale ne correspond pas à un déplacement valide de la pièce
        if (!jeux[indiceJeuCourant].isMoveOk(xInit, yInit, xFinal, yFinal, isCatchOk, isCastlingPossible)) {
            return false;
        }
        
        // s'il existe une pièce intermédiaire sur la trajectoire
        
        
        // s'il existe une pièce positionnée aux coordonnées finales
        String finalPieceName = jeux[indiceJeuCourant].getPieceName(xFinal, yFinal);
        if (finalPieceName != null) {
            Couleur couleur = jeux[indiceJeuCourant].getPieceColor(xFinal, yFinal);
            if ((couleur == jeux[indiceJeuCourant].getCouleur())) {
                // pièce de la mâme couleur
                if (jeux[indiceJeuCourant].getPieceName(xInit, yInit).equals("Roi") && finalPieceName.equals("Tour")) {
                    // tentative de roque
                    // jeuCourant.setCastling();
                    
                } else {
                    // coup illégal
                    return false;
                }
            } else {
                // pièce de l'adversaire
                
                // cas du pion
                if (finalPieceName.equals("Pion")) {
                    
                }
                jeux[indiceJeuCourant].capture(xFinal, yFinal);
            }
        }
        
        
        
        return true;
    }
    
    @Override
    public boolean move(int xInit, int yInit, int xFinal, int yFinal) {
        if (isMoveOk(xInit, yInit, xFinal, yFinal)) {
            return jeux[indiceJeuCourant].move(xInit, yInit, xFinal, yFinal);
        }
        return false;
    }
    
    public List<PieceIHMs> getPiecesIHM() {
        List list = new LinkedList();
        for (Jeu jeu : jeux) {
            list.addAll(jeu.getPiecesIHM());
        }
        return list;
    }
    
    @Override
    public boolean isEnd() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getMessage() {
        return message;
    }
    
    private void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Couleur getColorCurrentPlayer() {
        Couleur couleur = null;
        if (isBlancCourant) {
            couleur = Couleur.BLANC;
        } else {
            couleur = Couleur.NOIR;
        }
        return couleur;
    }

    @Override
    public Couleur getPieceColor(int x, int y) {
        return jeux[indiceJeuCourant].getPieceColor(x, y);
    }
    
    public void switchJoueur() {
        if (isBlancCourant) {
            // à Noir de jouer
            isBlancCourant = false;
            indiceJeuCourant = 1;
        } else {
            // à Blanc de jouer
            isBlancCourant = true;
            indiceJeuCourant = 0;
        }
    }
    
    @Override
    public String toString() {
        return "Jeu 1 :" + jeux[0] + " - Jeu 2 : " + jeux[1] + " - BlancCourant : " + isBlancCourant;
    }
    
    public static void main(String args[]) {
        
        Echiquier echiquier = new Echiquier();
        System.out.println(echiquier);
        
        // test changement de joueur
        echiquier.switchJoueur();
        System.out.println(echiquier);
        
        // nouveau changement de joueur 
        echiquier.switchJoueur();
        System.out.println(echiquier);
        
    }
}
