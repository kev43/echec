/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.LinkedList;
import java.util.List;
import static tools.ChessPiecesFactory.newPieces;

/**
 *
 * @author Kevin
 */
public class Jeu implements Game {

    private List<Pieces> listePieces;
    private Couleur couleur;
    private boolean possibleCastling = false;
    private boolean possibleCapture = false;

    /**
     * Constructeur d'un jeu
     * @param couleur 
     */
    public Jeu(Couleur couleur) {
        this.couleur = couleur;
        this.listePieces = newPieces(couleur);
    }

    /**
     * Return True ou false sir la case est occupé ou non
     * @param x
     * @param y
     * @return boolean
     */
    @Override
    public boolean isPieceHere(int x, int y) {
        boolean res = false;
        for (Pieces p : listePieces) {
            if (p.getX() == x && p.getY() == y) {
                res = true;
            }
        }
        return res;
    }

    /**
     * Renvoie un boolean en fonction de la validité d'un déplacement
     * @param xInit position de départ X
     * @param yInit position de départ Y
     * @param xFinal position d'arrivée X
     * @param yFinal position d'arrivée Y
     * @param isCatchOk si capture possible
     * @param isCastlingPossible si roque possible
     * @return boolean
     */
    @Override
    public boolean isMoveOk(int xInit, int yInit, int xFinal, int yFinal, boolean isCatchOk, boolean isCastlingPossible) {
        boolean res = false;
         for (Pieces p : listePieces) {
            if (p.getX() == xInit && p.getY() == yInit) {
                res = p.isMoveOk(xFinal, yFinal, isCatchOk, isCastlingPossible);
            }
        }
        return res;
    }
    
    /**
     * Renvoie un boolean en fonction du succès ou de l'échec du déplacement
     * @param xInit position de départ X
     * @param yInit position de départ Y
     * @param xFinal position d'arrivée X
     * @param yFinal position d'arrivée Y
     * @return boolean
     */
    @Override
    public boolean move(int xInit, int yInit, int xFinal, int yFinal) {
        boolean res = false;
        for (Pieces p : listePieces) {
            if (p.getX() == xInit && p.getY() == yInit) {
                p.move(xFinal, yFinal);
                res = true;
            }
        }

        return res;
    }

    /**
     * 
     * @param xCatch
     * @param yCatch
     * @return 
     */
    @Override
    public boolean capture(int xCatch, int yCatch) {
        for (Pieces p : listePieces) {
            if (p.getX() == xCatch && p.getY() == yCatch) {
                return p.capture();
            }
        }
        return false;
    }

    /**
     * 
     */
    public void setPossibleCapture() {

    }

    /**
     * 
     * @param x
     * @param y
     * @return couleur (noir ou blanc)
     */
    public Couleur getPieceColor(int x, int y) {
        Couleur c = null;
        for (Pieces p : listePieces) {
            if (p.getX() == x && p.getY() == y) {
                c = p.getCouleur();
            }
        }
        return c;
    }

    /**
     * Renvoie le nom d'une piece
     * @param x
     * @param y
     * @return 
     */
    public String getPieceName(int x, int y) {
        String str = null;
        for (Pieces p : listePieces) {
            if (p.getX() == x && p.getY() == y) {
                str = p.getClass().getSimpleName();
            }
        }
        return str;

    }

    /**
     * Renvoi la couleur du jeu
     * @return 
     */
    public Couleur getCouleur() {
        return this.couleur;

    }

    /**
     * 
     */
    public void setCastling() {
        this.possibleCastling = true ;
    }

    /**
     * 
     * @return 
     * revoi la liste des PiecesIHM
     */
    public List<PieceIHMs> getPiecesIHM() {
        PieceIHMs newPieceIHM = null;
        List<PieceIHMs> list = new LinkedList<>();
        for (Pieces piece : listePieces) {
            //si la piece est toujours en jeu
            if (piece.getX() != -1) {
                newPieceIHM = new PieceIHM(piece);
                list.add(newPieceIHM);
            }
        }
        return list;
    }

    public static void main(java.lang.String[] args) {
        Jeu j = new Jeu(Couleur.NOIR);
        System.out.println(j.toString());
        System.out.println("Piece en [0,0] :"+ j.isPieceHere(0,0));
        System.out.println("Piece en [5,3] :"+ j.isPieceHere(5,3));
        System.out.println("Test isMoveOK");
        System.out.println("Move d'un pion OK [3,1] vers [3,3] :" + j.isMoveOk(3, 1, 3, 3, false, false));
        System.out.println("Move d'un pion non OK [3,1] vers [3,3] :" + j.isMoveOk(3, 1, 3, 4, false, false));
        System.out.println("Move d'un cavalier OK [1,0] vers [2,2] :" + j.isMoveOk(1, 0, 2, 2, false, false));
        System.out.println("Move d'un cavalier non OK [1,0] vers [2,3] :" + j.isMoveOk(1, 0, 2, 3, false, false));
        System.out.println("Test Move");
        System.out.println("Move d'un pion OK [3,1] vers [3,3]");
        System.out.println("Avant [3,1] " + j.findPiece(3, 1)+" [3,3] "+j.findPiece(3, 3));
        j.move(3, 1, 3, 3);
        System.out.println("Aprés [3,1] " + j.findPiece(3, 1)+" [3,3] "+j.findPiece(3, 3));
        
    }

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        String str = "";
        for (Pieces p : listePieces) {
            str += p.toString();
            str += "\n";
        }
        return str;
    }

    /**
     * 
     * @param x
     * @param y
     * @return pieces
     * renvoi la piece située aux coordonnées x,y
     */
    private Pieces findPiece(int x, int y) {
        Pieces pRes = null ;
        for (Pieces p : listePieces) {
            if (p.getX() == x && p.getY() == y) {
                pRes =  p;
            }
        }

        return pRes;

    }

}
