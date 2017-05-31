/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Kevin
 */
public class Echiquier implements BoardGames{
    private Jeu[] jeux ;
    
    Echiquier() {
        jeux = new Jeu[2]; // tableau de taille 2
        jeux[0]= new Jeu(Couleur.BLANC);
        jeux[1]= new Jeu(Couleur.NOIR);
        boolean isBlancCourant = true ;
        
    }

    @Override
    public boolean move(int xInit, int yInit, int xFinal, int yFinal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isEnd() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getMessage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Couleur getColorCurrentPlayer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Couleur getPieceColor(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
