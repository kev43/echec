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
public class Pion extends AbstractPiece {
    
    public Pion(Couleur couleur, Coord coord) {
        super(couleur, coord);
        name = "Pion";
    }
    
    @Override
    public boolean isMoveOk(int xFinal, int yFinal, boolean isCatching, boolean isCastlingPossible) {
        
        
        if (couleur == Couleur.BLANC) {
            // déplacement du pion BLANC : "vers le haut" = y décroissant
            if (isCatching) {
                // prise du pion
                if (yFinal == y-1) {
                    // ligne au-dessus
                    if (Math.abs(xFinal - x) == 1) {
                        // colonne adjaçente
                        
                        // ici la capture est autorisée
                        return true;
                    }
                }
            } else {
                // mouvement normal du pion

                // doit avancer sur la même colonne
                if (xFinal != x) {
                    return false;
                }

                if (y == 6) {
                    // pion blanc sur sa position de départ : peut avancer de deux cases
                    if (yFinal == 6-2) {
                        return true;
                    }
                }

                if (yFinal < y) {
                    // déplacement d'une case "vers le haut" autorisé pour le pion blanc
                    if (yFinal == y - 1) {
                        return true;
                    }
                }
            }
        } else if (couleur == Couleur.NOIR) {
            // déplacement du pion NOIR : "vers le bas" = y croissant
            if (isCatching) {
                // prise du pion
                if (yFinal == y+1) {
                    // ligne au-dessous
                    if (Math.abs(xFinal - x) == 1) {
                        // colonne adjaçente
                        
                        // ici la capture est autorisée
                        return true;
                    }
                }
            } else {
                // mouvement normal du pion

                // doit avancer sur la même colonne
                if (xFinal != x) {
                    return false;
                }
                
                if (y == 1) {
                    // pion noir sur sa position de départ : peut avancer de deux cases
                    if (yFinal == 1+2) {
                        return true;
                    }
                }

                if (yFinal > y) {
                    // déplacement d'une case "vers le bas" autorisé pour le pion noir
                    if (yFinal == y + 1) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    public static void main(String args[]) {
        
        // TEST PION BLANC
        
        boolean res;
        
        System.out.println("Déplacements autorisés pion blanc");
        
        // pion blanc sur sa ligne de départ
        Pion pionBlanc = new Pion(Couleur.BLANC, new Coord(0,6));
        
        // avance de deux cases
        res = pionBlanc.isMoveOk(0, 4, false, false);
        System.out.println(res);
        
        // on remet le pion blanc sur sa ligne de départ
        pionBlanc = new Pion(Couleur.BLANC, new Coord(0,6));
        
        // avance d'une case
        res = pionBlanc.isMoveOk(0, 5, false, false);
        System.out.println(res);
        pionBlanc.move(0, 5);
        
        // avance encore d'une case
        res = pionBlanc.isMoveOk(0, 4, false, false);
        System.out.println(res);
        
        // prise d'un pion adverse autorisé
        pionBlanc = new Pion(Couleur.BLANC, new Coord(0,6));
        res = pionBlanc.isMoveOk(1, 5, true, false);
        System.out.println(res);
        
        System.out.println("Déplacements illégaux pion blanc");
        
        // on remet le pion blanc sur sa ligne de départ
        pionBlanc = new Pion(Couleur.BLANC, new Coord(0,6));
        
        res = pionBlanc.isMoveOk(2, 4, false, false);
        System.out.println(res);
        
        // recul du pion interdit
        res = pionBlanc.isMoveOk(0, 7, false, false);
        System.out.println(res);
        
        // avance de 2 cases alors qu'il n'est pas sur la ligne de départ => interdit
        pionBlanc = new Pion(Couleur.BLANC, new Coord(0,5));
        res = pionBlanc.isMoveOk(0, 3, false, false);
        System.out.println(res);
        
        // mouvement de prise sans que le coup soit une prise
        pionBlanc = new Pion(Couleur.BLANC, new Coord(0,6));
        res = pionBlanc.isMoveOk(1, 5, false, false);
        System.out.println(res);
        
        
        // TEST PION NOIR
        
        System.out.println("Déplacements autorisés pion noir");
        
        // pion blanc sur sa ligne de départ
        Pion pionNoir = new Pion(Couleur.NOIR, new Coord(0,1));
        
        // avance de deux cases
        res = pionNoir.isMoveOk(0, 3, false, false);
        System.out.println(res);
        
        // on remet le pion noir sur sa ligne de départ
        pionNoir = new Pion(Couleur.NOIR, new Coord(0,1));
        
        // avance d'une case
        res = pionNoir.isMoveOk(0, 2, false, false);
        System.out.println(res);
        pionNoir.move(0, 2);
        
        // avance encore d'une case
        res = pionNoir.isMoveOk(0, 3, false, false);
        System.out.println(res);
        
        // prise d'un pion adverse autorisé
        pionBlanc = new Pion(Couleur.NOIR, new Coord(0,1));
        res = pionBlanc.isMoveOk(1, 2, true, false);
        System.out.println(res);
        
        System.out.println("Déplacements illégaux pion noir");
        
        // on remet le pion blanc sur sa ligne de départ
        pionNoir = new Pion(Couleur.NOIR, new Coord(0,1));
        
        res = pionNoir.isMoveOk(2, 3, false, false);
        System.out.println(res);
        
        // recul du pion interdit
        res = pionNoir.isMoveOk(0, 0, false, false);
        System.out.println(res);
        
        // avance de 2 cases alors qu'il n'est pas sur la ligne de départ => interdit
        pionNoir = new Pion(Couleur.NOIR, new Coord(0,2));
        res = pionNoir.isMoveOk(0, 4, false, false);
        System.out.println(res);
        
        // mouvement de prise sans que le coup soit une prise
        pionBlanc = new Pion(Couleur.NOIR, new Coord(0,1));
        res = pionBlanc.isMoveOk(1, 2, false, false);
        System.out.println(res);
    }
}
