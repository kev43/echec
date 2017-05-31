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
    
    public Tour(Couleur couleur, Coord coord) {
        super(couleur, coord);
        name = "Tour";
    }
    
    @Override
    public boolean isMoveOk(int xFinal, 
                            int yFinal, 
                            boolean isCatchOk, 
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
    
}
