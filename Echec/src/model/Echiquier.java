/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Kevin
 */
public class Echiquier implements BoardGames {
    private Jeu[] jeux ;
    private boolean isBlancCourant;
    private Jeu jeuCourant;
    
    private String message;
    
    private boolean isCatchOk;
    private boolean isCastlingPossible;
    
    Echiquier() {
        jeux = new Jeu[2]; // tableau de taille 2
        jeux[0]= new Jeu(Couleur.BLANC);
        jeux[1]= new Jeu(Couleur.NOIR);
        
        // Blanc commence
        isBlancCourant = true;
        jeuCourant = jeux[0];
        
        message = "Echiquier initialisé";
    }
    
    public boolean isMoveOk(int xInit,
                            int yInit,
                            int xFinal,
                            int yFinal) {
        
        //s'il n'existe pas de piece du jeu courant aux coordonnées initiales
        if (jeuCourant.getPieceName(xInit, yInit) == null){
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
        if (!jeuCourant.isMoveOk(xInit, yInit, xFinal, yFinal, isCatchOk, isCastlingPossible)) {
            return false;
        }
        
        // s'il existe une pièce intermédiaire sur la trajectoire
        
        
        // s'il existe une pièce positionnée aux coordonnées finales
        String finalPieceName = jeuCourant.getPieceName(xFinal, yFinal);
        if (finalPieceName != null) {
            Couleur couleur = jeuCourant.getPieceColor(xFinal, yFinal);
            if ((couleur == jeuCourant.getCouleur())) {
                // pièce de la mâme couleur
                if (jeuCourant.getPieceName(xInit, yInit) == "Roi" && finalPieceName == "Tour") {
                    // tentative de roque
                    // jeuCourant.setCastling();
                    
                } else {
                    // coup illégal
                    return false;
                }
            } else {
                // pièce de l'adversaire
                
                // cas du pion
                if (finalPieceName == "Pion") {
                    
                }
                jeuCourant.capture(xFinal, yFinal);
            }
        }
        
        
        
        return true;
    }
    
    @Override
    public boolean move(int xInit, int yInit, int xFinal, int yFinal) {
        if (isMoveOk(xInit, yInit, xFinal, yFinal)) {
            return move(xInit, yInit, xFinal, yFinal);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Couleur getColorCurrentPlayer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Couleur getPieceColor(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void switchJoueur() {
        if (isBlancCourant) {
            // à Noir de jouer
            isBlancCourant = false;
            jeuCourant = jeux[0];
        } else {
            // à Blanc de jouer
            isBlancCourant = true;
            jeuCourant = jeux[1];
        }
    }
    
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
