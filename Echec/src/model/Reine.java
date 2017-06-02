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
public class Reine extends AbstractPiece {

    public Reine(Couleur couleur, Coord coord) {
        super(couleur, coord);
        name = "Reine";
    }

    @Override
    public boolean isMoveOk(int xFinal, int yFinal, boolean isCatching, boolean isCastlingPossible) {
    //deplacements reine = tour + fou    
    //test déplacements de la tour
        if (xFinal == this.x && yFinal != this.y) {
            // déplacement vertical
            return true;
        }
        
        if (yFinal == this.y && xFinal != this.x) {
            // déplacement horizontal
            return true;
        }
        
        //test déplacements du fou
        int deltaX = xFinal - x;
        int deltaY = yFinal - y;
        
        if (Math.abs(deltaX) == Math.abs(deltaY)) {
            // même différence absolue des deux coordonnées
            //  = déplacement sur une diagonale
            return true;
        }
        
        return false;
    }

    public static void main(String args[]) {
        
        boolean res;
        
        // TEST Reine
        
        Reine reine = new Reine(Couleur.NOIR, new Coord(3,4));
        System.out.println(reine.toString());
        
        
        System.out.println("Déplacements autorisés");
        
        res = reine.isMoveOk(3, 1, false, false);
        System.out.println(res);
        
        res = reine.isMoveOk(0, 7, false, false);
        System.out.println(res);
        
        res = reine.isMoveOk(3, 4, false, false);
        System.out.println(res);
        
        res = reine.isMoveOk(2, 3, false, false);
        System.out.println(res);
        
        res = reine.isMoveOk(6, 4, false, false);
        System.out.println(res);
        
        res = reine.isMoveOk(5, 6, false, false);
        System.out.println(res);
        
        res = reine.isMoveOk(6, 7, false, false);
        System.out.println(res);
        

        
        System.out.println("Déplacements illégaux");
        
        res = reine.isMoveOk(5, 5, false, false);
        System.out.println(res);
        
        res = reine.isMoveOk(2, 2, false, false);
        System.out.println(res);
        
        res = reine.isMoveOk(4, 6, false, false);
        System.out.println(res);
        
        res = reine.isMoveOk(4, 6, false, false);
        System.out.println(res);
        
        res = reine.isMoveOk(6, 5, false, false);
        System.out.println(res);
        
        res = reine.isMoveOk(7, 6, false, false);
        System.out.println(res);
        
        
        
    }
}
