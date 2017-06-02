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
        return isMoveOk(xInit, yInit, xFinal, yFinal, indiceJeuCourant);
    }
    
    /**
     * Indique si un coup est autorisé ou non.
     * Ne modifie pas l'échiquier.
     * 
     * /!\ Le roque n'est pas implémenté
     * 
     * @param xInit coordonnée x départ
     * @param yInit coordonnée y départ
     * @param xFinal coordonnée x arrivée
     * @param yFinal coordonnée y arrivée
     * @return true si le coup est autorisé,
     *         false si le coup est illégal
     */
    public boolean isMoveOk(int xInit,
                            int yInit,
                            int xFinal,
                            int yFinal,
                            int indiceJeuActif) {
        
        //s'il n'existe pas de piece du jeu courant aux coordonnées initiales
        if (jeux[indiceJeuActif].getPieceName(xInit, yInit) == null){
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
        
        boolean isCatching = false;
        Couleur couleurCourante = jeux[indiceJeuActif].getCouleur();
        Couleur couleurFinale = getPieceColor(xFinal, yFinal);
        // est-ce que le coup correspond à une capture de pièce
        if (couleurFinale != null && !couleurFinale.equals(couleurCourante)) {
            isCatching = true;
        }
        
        // si la position finale ne correspond pas à un déplacement valide de la pièce
        if (!jeux[indiceJeuActif].isMoveOk(xInit, yInit, xFinal, yFinal, isCatching, isCastlingPossible)) {
            return false;
        }
        
        // s'il existe une pièce intermédiaire sur la trajectoire
        if (existPiecesIntermediaires(xInit, yInit, xFinal, yFinal, indiceJeuActif)) {
            return false;
        }
        
        // s'il existe une pièce positionnée aux coordonnées finales
        if (isPieceHere(xFinal, yFinal)) {
            Couleur couleur = jeux[indiceJeuActif].getPieceColor(xFinal, yFinal);
            if ((couleur == couleurCourante)) {
                // pièce de la mâme couleur
                if (jeux[indiceJeuActif].getPieceName(xInit, yInit).equals("Roi") 
                        && jeux[indiceJeuActif].getPieceName(xFinal, yFinal).equals("Tour")) {
                    // tentative de roque
                    // jeuCourant.setCastling();
                    
                } else {
                    // coup illégal
                    this.setMessage("Pas OK : déplacement interdit");
                    return false;
                }
            } else {
                // pièce de l'adversaire
                
            }
        }
        
        this.setMessage("OK : déplacement simple");
        return true;
    }
    
    /**
     * Effectue un déplacement de pièce d'une case de départ à une case d'arrivée.
     * 1) Vérification avec isMoveOk() si le coup est autorisé
     * 2) Effectue le déplacement
     * 3) Le cas échéant, capture la pièce de la case d'arrivée
     * 
     * @param xInit coordonnée x départ
     * @param yInit coordonnée y départ
     * @param xFinal coordonnée x arrivée
     * @param yFinal coordonnée y arrivée
     * @return true si tout s'est bien passé
     *         false si le mouvement n'est pas autorisé
     *               ou si la capture n'a pas fonctionné
     */
    @Override
    public boolean move(int xInit, int yInit, int xFinal, int yFinal) {
        boolean res = false;
        if (isMoveOk(xInit, yInit, xFinal, yFinal)) {
            boolean pieceOnFinal = isPieceHere(xFinal, yFinal);
            res = jeux[indiceJeuCourant].move(xInit, yInit, xFinal, yFinal);
            
            // s'il y a une pièce sur la case d'arrivée
            //  (on sait que c'est une pièce adverse car isMoveOk() a renvoyé true
            if (res == true && pieceOnFinal) {
                // on la capture
                res = jeux[(indiceJeuCourant+1)%2].capture(xFinal, yFinal);
            }
        }
        return res;
    }
    
    /**
     * @return une version réduite de la liste des pièces en cours 
     * ne donnant que des accès en lecture sur des PieceIHMs
     * (type piece + couleur + coordonnées)
     */
    public List<PieceIHMs> getPiecesIHM() {
        List list = new LinkedList();
        for (Jeu jeu : jeux) {
            list.addAll(jeu.getPiecesIHM());
        }
        return list;
    }
    
    /**
     * Retourne la liste des coordonnées que la pièce située sur
     *  la case (xInit, yInit) a le droit d'atteindre. Cela inclut 
     *  les captures de pièces.
     * 
     * @param xInit
     * @param yInit
     * @return liste des coordonnées des cases possibles
     */
    public List<Coord> getPossibleMoves(int xInit, int yInit) {
        List<Coord> possibleMoves = new ArrayList<>();
        
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (isMoveOk(xInit, yInit, x, y)) {
                    possibleMoves.add(new Coord(x, y));
                }
            }
        }
        
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
    
    /**
     * Retourne la Couleur du joueur qui a le trait.
     * 
     * @return Couleur
     */
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

    /**
     * Retourne la Couleur de la pièce aux coordonnées (x,y)
     * @param x
     * @param y
     * @return Couleur la couleur de la pièce
     */
    @Override
    public Couleur getPieceColor(int x, int y) {
        Couleur c = null ;
        c = jeux[0].getPieceColor(x, y);
        if (c == null) {
            c = jeux[1].getPieceColor(x, y);
        }
        return c;
    }
    
    /**
     * Indique s'il y a une pièce sur la case (x,y)
     * 
     * @param x
     * @param y
     * @return boolean true s'il y a une pièce, false si la case est vide
     */
    public boolean isPieceHere(int x, int y) {
        boolean res = false;
        res = jeux[0].isPieceHere(x, y);
        if (res == false) {
            res = jeux[1].isPieceHere(x, y);
        }
        return res;
    }
    
    /**
     * Permute le joueur qui a le trait.
     * Si Blanc a le trait, Noir a alors le trait et vice-versa.
     * 
     */
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
     * @param xInit coordonnée x départ
     * @param yInit coordonnée y départ
     * @param xFinal coordonnées x arrivée
     * @param yFinal coordonnées y arrivée
     * @return boolean true s'il y a des pièces obstacle, false s'il n'y en a pas
     */
    private boolean existPiecesIntermediaires(int xInit, int yInit, int xFinal, int yFinal, int indiceJeuActif) {
        boolean res = false;
        String pieceName = jeux[indiceJeuActif].getPieceName(xInit, yInit);
        int x = 0, y = 0;
        // on n'a pas besoin de vérifier pour Cavalier ni pour Roi
        switch (pieceName) {
            case "Pion":
                // pour le Pion, on considère uniquement le cas
                //  où il bouge de deux cases, car dans les autres 
                //  cas il n'y a pas de case intermédiaire.
                Couleur couleur = getPieceColor(xInit, yInit);
                if (couleur.equals(Couleur.BLANC) && yInit == 6
                        ||
                    couleur.equals(Couleur.NOIR)  && yInit == 1) {
                    if (Math.abs(yFinal-yInit) == 2 ) {
                        // s'il veut se déplacer de 2 cases
                        // on teste s'il y a une pièce devant le Pion
                        if (couleur.equals(Couleur.NOIR) && isPieceHere(xInit, 2)) {
                            res = true;
                            break;
                        } else if (couleur.equals(Couleur.BLANC) && isPieceHere(xInit, 5)) {
                            res = true;
                            break;
                        }
                    }
                }
                break;
            case "Reine": // Reine a les déplacements de Tour + Fou
            case "Tour":
                if (xInit == xFinal && yFinal < yInit) {
                    // déplacement vers le Nord
                    for (y = yInit-1; y > yFinal; y--) {
                        if (isPieceHere(xInit, y)) {
                            res = true;
                            break;
                        }
                    }
                    
                } else if (xInit == xFinal && yFinal > yInit) {
                    // déplacement vers le Sud
                    for (y = yInit+1; y < yFinal; y++) {
                        if (isPieceHere(xInit, y)) {
                            res = true;
                            break;
                        }
                    }
                    
                } else if (yInit == yFinal && xFinal < xInit) {
                    // déplacement vers l'Ouest
                    for (x = xInit-1; x > xFinal; x--) {
                        if (isPieceHere(x, yInit)) {
                            res = true;
                            break;
                        }
                    }
                } else if (yInit == yFinal && xFinal > xInit) {
                    // déplacement vers l'Est
                    for (x = xInit+1; x < xFinal; x++) {
                        
                        if (isPieceHere(x, yInit)) {
                            res = true;
                            break;
                        }
                    }
                }
                
                if (pieceName == "Tour") {
                    // si c'est une Reine on continue la vérification avec les mouvements de Fou
                    break;
                }
            case "Fou":
                
                //System.out.println("("+xInit+","+yInit+") -> ("+xFinal+","+yFinal+")");
                if (xInit < xFinal && yFinal < yInit) {
                    // déplacement Nord-Est
                    x = xInit+1;
                    y = yInit-1;
                    while (x < xFinal && y > yFinal) {
                        System.out.println("x="+x+" y="+y);
                        if (isPieceHere(x, y)) {
                            res = true;
                            break;
                        }
                        x++;
                        y--;
                    }
                } else if (xInit < xFinal && yFinal > yInit) {
                    // déplacement Sud-Est
                    x = xInit+1;
                    y = yInit+1;
                    while (x < xFinal && y < yFinal) {
                        System.out.println("x="+x+" y="+y);
                        if (isPieceHere(x, y)) {
                            res = true;
                            break;
                        }
                        x++;
                        y++;
                    }
                } else if (xFinal < xInit && yFinal > yInit) {
                    // déplacement Sud-Ouest
                    x = xInit-1;
                    y = yInit+1;
                    while (x > xFinal && y < yFinal) {
                        System.out.println("x="+x+" y="+y);
                        if (isPieceHere(x, y)) {
                            res = true;
                            break;
                        }
                        x--;
                        y++;
                    }
                } else if (xFinal < xInit && yFinal < yInit) {
                    // déplacement Nord-Ouest
                    x = xInit-1;
                    y = yInit-1;
                    while (x > xFinal && y > yFinal) {
                        System.out.println("x="+x+" y="+y);
                        if (isPieceHere(x, y)) {
                            res = true;
                            break;
                        }
                        x--;
                        y--;
                    }
                }
                
                
                break;
        }
        
        return res;
    }
    
            
    public boolean estMenacee(int xCible, int yCible) {
        boolean res = false;
        
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (isMoveOk(x, y, xCible, yCible, (indiceJeuCourant+1)%2)) {
                    res = true;
                    System.out.println("Pièce attaquée : ("+xCible+","+yCible+")");
                }
            }
        }
        
        return res;
    }
    
    public List<Coord> getCasesMenacees() {
        List<Coord> list = new ArrayList();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (estMenacee(x, y)) {
                    list.add(new Coord(x, y));
                }
            }
        }
        return list;
    }

}
