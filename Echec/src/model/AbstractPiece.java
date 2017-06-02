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
                     boolean isCatching,
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

}
