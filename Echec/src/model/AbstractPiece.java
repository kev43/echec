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
public abstract class AbstractPiece implements Pieces {
    protected int x;
    protected int y;
    
    protected Couleur couleur;
    
    protected String name;
    
    public AbstractPiece(Couleur couleur, Coord coord) {
        this.couleur = couleur;
        x = coord.x;
        y = coord.y;
    }
    
    public boolean capture() {
        x = -1;
        y = -1;
        return true;
    }
    
    public Couleur getCouleur() {
        return couleur;
    }
    
    public String getName() {
        return name;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public abstract boolean isMoveOk(int xFinal,
                     int yFinal,
                     boolean isCatchOk,
                     boolean isCastlingPossible);
    
    public boolean move(int xFinal, int yFinal) {
        // on évite les positions illégales
        if (xFinal < 0 || xFinal > 7) {
            return false;
        }
        if (yFinal < 0 || yFinal > 7) {
            return false;
        }
        x = xFinal;
        y = yFinal;
        return true;
    }
    
    public String toString() {
        return name + "[" + couleur + "]" + "(" + x + "," + y + ")";
    }
    
    public static void main(String args[]) {
        
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
