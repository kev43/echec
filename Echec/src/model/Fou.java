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
public class Fou extends AbstractPiece {
    
    public Fou(Couleur couleur, Coord coord) {
        super(couleur, coord);
        name = "Fou";
    }
    
    @Override
    public boolean isMoveOk(int xFinal, int yFinal, boolean isCatchOk, boolean isCastlingPossible) {
        
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
        // TEST FOU
        
        boolean res;
        
        Fou fou = new Fou(Couleur.BLANC, new Coord(4,5));
        System.out.println(fou.toString());
        
        // test des déplacements autorisés du cavalier
        System.out.println("Déplacements autorisés");
        
        res = fou.isMoveOk(3, 4, false, false);
        System.out.println(res);
        
        res = fou.isMoveOk(2, 3, false, false);
        System.out.println(res);
        
        res = fou.isMoveOk(6, 3, false, false);
        System.out.println(res);
        
        res = fou.isMoveOk(5, 6, false, false);
        System.out.println(res);
        
        res = fou.isMoveOk(6, 7, false, false);
        System.out.println(res);
        
        res = fou.isMoveOk(3, 6, false, false);
        System.out.println(res);
        
        res = fou.isMoveOk(2, 7, false, false);
        System.out.println(res);
        
        System.out.println("Déplacements illégaux");
        
        res = fou.isMoveOk(5, 5, false, false);
        System.out.println(res);
        
        res = fou.isMoveOk(4, 4, false, false);
        System.out.println(res);
        
        res = fou.isMoveOk(3, 5, false, false);
        System.out.println(res);
        
        res = fou.isMoveOk(4, 6, false, false);
        System.out.println(res);
        
        res = fou.isMoveOk(6, 5, false, false);
        System.out.println(res);
        
        res = fou.isMoveOk(3, 3, false, false);
        System.out.println(res);
        
        res = fou.isMoveOk(6, 2, false, false);
        System.out.println(res);
    }
        
}
