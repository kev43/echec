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
public class Cavalier extends AbstractPiece {
    
    /**
     * 
     * @param couleur
     * @param coord 
     */
    public Cavalier(Couleur couleur, Coord coord) {
        super(couleur, coord);
        name = "Cavalier";
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
        
//        int cx[8], cy[8];
//            cx[0] = 1; cx[1] = 2;cx[2] = 2;cx[3] = 1;cx[4] = -1;cx[5] = -2;cx[6] = -2;cx[7] = -1;
//            cy[0] = -2; cy[1] = -1;cy[2] = 1;cy[3] = 2;cy[4] = 2;cy[5] = 1;cy[6] = -1;cy[7] = -2;
        
        // deltas x et y correspondant aux déplacements possibles du cavalier
        int cx[] = {1, 2, 2, 1, -1, -2, -2, -1};
        int cy[] = {-2, -1, 1, 2, 2, 1, -1, -2};
        int i = 0;
        
        // on teste chaque déplacement possible du cavalier,
        //   et on regarde si l'une correspond à la position ciblée
        for (i = 0; i <= 7; i++)
        {
            int xTest = x + cx[i];
            int yTest = y + cy[i];
            
            if ((xFinal == xTest) && (yFinal == yTest))
            {
                return true;
            }
        }
        return false;
    }
    
    public static void main(String args[]) {
        // TEST CAVALIER
        
        boolean res;
        
        Cavalier cav = new Cavalier(Couleur.NOIR, new Coord(3,4));
        System.out.println(cav.toString());
        
        // test des déplacements autorisés du cavalier
        System.out.println("Déplacements autorisés");
        
        res = cav.isMoveOk(1, 3, false, false);
        System.out.println(res);
        
        res = cav.isMoveOk(2, 2, false, false);
        System.out.println(res);
        
        res = cav.isMoveOk(4, 2, false, false);
        System.out.println(res);
        
        res = cav.isMoveOk(5, 3, false, false);
        System.out.println(res);
        
        res = cav.isMoveOk(5, 5, false, false);
        System.out.println(res);
        
        res = cav.isMoveOk(4, 6, false, false);
        System.out.println(res);
        
        res = cav.isMoveOk(2, 6, false, false);
        System.out.println(res);
        
        res = cav.isMoveOk(1, 5, false, false);
        System.out.println(res);
        
        // tests de déplacements illégaux
        System.out.println("Déplacements illégaux");
        
        res = cav.isMoveOk(3, 3, false, false);
        System.out.println(res);
        
        res = cav.isMoveOk(3, 7, false, false);
        System.out.println(res);
        
        res = cav.isMoveOk(3, 6, false, false);
        System.out.println(res);
        
        res = cav.isMoveOk(1, 2, false, false);
        System.out.println(res);
        
        res = cav.isMoveOk(4, 3, false, false);
        System.out.println(res);
    }
}
