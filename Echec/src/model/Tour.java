/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author david.vivier
 */
public class Tour extends AbstractPiece {
    
    /**
     * 
     * @param couleur
     * @param coord 
     */
    public Tour(Couleur couleur, Coord coord) {
        super(couleur, coord);
        name = "Tour";
    }
    
    /**
     * Renvoi un true si le mouvement de la pièce est autorisé
     * @param xFinal
     * @param yFinal
     * @param isCatching , Mettre à True dans le cas d'une capture avec un pion
     * @param isCastlingPossible , Mettre à True dans le cas d'un Roque du roi
     * @return 
     */
    @Override
    public boolean isMoveOk(int xFinal, 
                            int yFinal, 
                            boolean isCatching, 
                            boolean isCastlingPossible) {
        // déplacement de la Tour

        if (xFinal == this.x && yFinal != this.y) {
            // déplacement vertical
            return true;
        }
        
        if (yFinal == this.y && xFinal != this.x) {
            // déplacement horizontal
            return true;
        }
        
        return false;
    }
        
    public static void main(String args[]) {
        
        // TEST TOUR
        
        boolean res;
            
        Tour tour = new Tour(Couleur.NOIR, new Coord(0,0));
        System.out.println(tour.toString());
        
        // mouvement autorisé
        res = tour.move(0, 5);
        System.out.println(res);
        System.out.println(tour.toString());
        
        // mouvement hors du plateau
        res = tour.move(-2, 5);
        System.out.println(res);
        System.out.println(tour.toString());
        
        // mouvement  illégal depuis (0,5)
        res = tour.isMoveOk(3, 2, false, false);
        System.out.println(res);
        
        // mouvement autorisé
        res = tour.isMoveOk(4, 5, false, false);
        System.out.println(res);
        
    }
}
