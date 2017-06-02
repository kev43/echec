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
public class Roi extends AbstractPiece {

    /**
     * 
     * @param couleur
     * @param coord 
     */
    public Roi(Couleur couleur, Coord coord) {
        super(couleur, coord);
        name="Roi";
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
    public boolean isMoveOk(int xFinal, int yFinal, boolean isCatching, boolean isCastlingPossible) {
        for (int i=-1 ; i<=1 ; i++) {
            for (int j=-1 ; j<=1 ; j++){              
                if (!(i ==0 && j==0)) {
                    if (this.x + i == xFinal && this.y + j == yFinal)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static void main(String args[]) {
        // TEST FOU
        
        boolean res;
        
        Roi roi = new Roi(Couleur.BLANC, new Coord(4,4));
        System.out.println(roi.toString());
        
        // test des déplacements autorisés du cavalier
        System.out.println("Déplacements autorisés");
        
        res = roi.isMoveOk(3, 4, false, false);
        System.out.println(res);
        
        res = roi.isMoveOk(5, 5, false, false);
        System.out.println(res);
        
        res = roi.isMoveOk(5, 3, false, false);
        System.out.println(res);
        
        res = roi.isMoveOk(5, 5, false, false);
        System.out.println(res);
        
        
        System.out.println("Déplacements illégaux");
        
        res = roi.isMoveOk(4, 4, false, false);
        System.out.println(res);
        
        res = roi.isMoveOk(6, 7, false, false);
        System.out.println(res);
        
        res = roi.isMoveOk(4, 6, false, false);
        System.out.println(res);
        
        res = roi.isMoveOk(2, 4, false, false);
        System.out.println(res);
        
    }
    
}
