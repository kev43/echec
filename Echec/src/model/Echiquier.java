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
        message = "Début de la partie";
        
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
        existPiecesIntermediaires(xInit, yInit, xFinal, yFinal);
        
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
                    this.setMessage("Pas OK : déplacement interdit");
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
        
        
        this.setMessage("OK : déplacement simple");
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
    
    public List<Coord> getPossibleMoves(int xInit, int yInit) {
        List<Coord> possibleMoves = new ArrayList<>();
        
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (isMoveOk(xInit, yInit, x, y)) {
                    possibleMoves.add(new Coord(x, y));
                }
            }
        }
        //System.out.println("Possible moves from ("+xInit+","+yInit+") : "+possibleMoves);
        return possibleMoves;
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
        
        System.out.println(echiquier.getMessage());
        
    }
    
    /**
     * Vérifie s'il y a des pièces entre la case de départ et la case d'arrivée
     * Ne se soucie pas si la case d'arrivée est occupée ou non
     * 
     * @param xInit
     * @param yInit
     * @param xFinal
     * @param yFinal
     * @return 
     */
    private boolean existPiecesIntermediaires(int xInit, int yInit, int xFinal, int yFinal) {
        boolean res = false;
        String pieceName = jeux[indiceJeuCourant].getPieceName(xInit, yInit);
        int x = 0, y = 0;
        
        // on n'apple Cavalier, ni le roi
        switch (pieceName) {
            case "Pion":
                // pour le Pion, on considère uniquement le cas
                //  où il bouge de deux cases, car dans les autres 
                //  cas il n'y a pas de case intermédiaire.
                Couleur couleur = getPieceColor(xInit, yInit);
                if (couleur.equals(Couleur.BLANC) && xInit == 6
                        ||
                    couleur.equals(Couleur.NOIR)  && xInit == 1) {
                    if (Math.abs(xFinal-xInit) == 2 ) {
                        // on teste s'il y a une pièce devant le Pion
                        if (couleur.equals(Couleur.BLANC) && true) {
                            
                        }
                    }
                }
                break;
            case "Reine": // Reine a les déplacements de Tour + Fou
            case "Tour":
                if (yInit == yFinal) {
                    // déplacement horizontal
                    int increment = (xInit < xFinal) ? +1 : -1;
                    for (x = xInit+increment; x < xFinal-increment; x += increment) {
                        if (isPieceHere(yInit, x)) {
                            res = true;
                            break;
                        }
                    }
                } else if (xInit == xFinal) {
                    // déplacement vertical
                    int increment = (yInit < yFinal) ? +1 : -1;
                    for (y = yInit+increment; y < yFinal-increment; y += increment) {
                        if (isPieceHere(x, yInit)) {
                            res = true;
                            break;
                        }
                    }
                }
                break;
            case "Fou":
                
                break;
        }
        
        return res;
    }
}
