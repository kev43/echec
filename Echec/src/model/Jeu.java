/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static java.util.Collections.list;
import java.util.LinkedList;
import java.util.List;
import static tools.ChessPiecesFactory.newPieces;

/**
 *
 * @author Kevin
 */
public class Jeu implements Game {

    private List<Pieces> listePieces ;
    Couleur couleur;
            
            
    public Jeu(Couleur couleur) {
        this.couleur = couleur ;
        this.listePieces = newPieces(couleur);
    }
    
    @Override
    public boolean isPieceHere(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isMoveOk(int xInit, int yInit, int xFinal, int yFinal, boolean isCatchOk, boolean isCastlingPossible) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean move(int xInit, int yInit, int xFinal, int yFinal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean capture(int xCatch, int yCatch) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public void setPossibleCapture() {
        
    }
    
    public Couleur getPieceColor(int x , int y) {
        return null;
        
    }
    
    public String getPieceName( int x , int y) {
        return null;
        
    }
    
    public Couleur getCouleur() {
        return null;
        
    }
    
    public void setCastling() {
        
    }
    
    public List<PieceIHMs> getPiecesIHM() {
        PieceIHMs newPieceIHM = null;
        List<PieceIHMs> list = new LinkedList<>();
        for (Pieces piece : listePieces){
            //si la piece est toujours en jeu
            if (piece.getX()!= -1) {
                newPieceIHM = new PieceIHM(piece);
                list.add(newPieceIHM);
            }
        }
        return list;
    }
    
    public static void main(java.lang.String[] args){
        Jeu j = new Jeu(Couleur.NOIR);
        j.toString();
    }
    
    @Override
    public String toString() {
        for(Pieces p : listePieces) {
            p.toString();
        }
        return null;
        
    }
    
    private Pieces findPiece(int x , int y) {
        for (Pieces p : listePieces){
            if(p.getX()==x && p.getY()==y){
                return p;
            }
        }
        
        return null;
        
    }
    
}
