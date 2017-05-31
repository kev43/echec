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
    Couleur couleur;
    boolean possibleCastling = false;
    boolean possibleCapture = false;

    public Jeu(Couleur couleur) {
        this.couleur = couleur;
        this.listePieces = newPieces(couleur);
    }

    @Override
    public boolean isPieceHere(int x, int y) {
        for (Pieces p : listePieces) {
            if (p.getX() == x && p.getY() == y) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isMoveOk(int xInit, int yInit, int xFinal, int yFinal, boolean isCatchOk, boolean isCastlingPossible) {
         for (Pieces p : listePieces) {
            if (p.getX() == xInit && p.getY() == yInit) {
                return p.isMoveOk(xFinal, yFinal, isCatchOk, isCastlingPossible);
            }
        }
        return false;
    }

    @Override
    public boolean move(int xInit, int yInit, int xFinal, int yFinal) {

        for (Pieces p : listePieces) {
            if (p.getX() == xInit && p.getY() == yInit) {
                p.move(xFinal, yFinal);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean capture(int xCatch, int yCatch) {
        /*for (Pieces p : listePieces) {
            if (p.getX() == xCatch && p.getY() == yCatch) {
                return true;
            }
        }

        return false;*/
        return false;
    }

    public void setPossibleCapture() {

    }

    public Couleur getPieceColor(int x, int y) {
        for (Pieces p : listePieces) {
            if (p.getX() == x && p.getY() == y) {
                return p.getCouleur();
            }
        }
        return null;
    }

    public String getPieceName(int x, int y) {
        for (Pieces p : listePieces) {
            if (p.getX() == x && p.getY() == y) {
                return p.getClass().getSimpleName();
            }
        }
        return null;

    }

    public Couleur getCouleur() {
        return this.couleur;

    }

    public void setCastling() {
        this.possibleCastling = true ;
    }

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

    @Override
    public String toString() {
        String str = "";
        for (Pieces p : listePieces) {
            str += p.toString();
            str += "\n";
        }
        return str;
    }

    private Pieces findPiece(int x, int y) {
        for (Pieces p : listePieces) {
            if (p.getX() == x && p.getY() == y) {
                return p;
            }
        }

        return null;

    }

}
