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
    
    /**
     * Constructeur de AbstractPiece
     * @param couleur
     * @param coord 
     */
    public AbstractPiece(Couleur couleur, Coord coord) {
        this.couleur = couleur;
        x = coord.x;
        y = coord.y;
    }
    
    /**
     * Sort de la grille une pièce capturée et renvoie true
     * @return 
     */
    @Override
    public boolean capture() {
        x = -1;
        y = -1;
        return true;
    }
    
    /**
     * Retourne la couleur de la pièce
     * @return 
     */
    @Override
    public Couleur getCouleur() {
        return couleur;
    }
    
    /**
     * Retourne le nom de la pièce
     * @return 
     */
    @Override
    public String getName() {
        return name;
    }
    
    /**
     * Retourne la coordonnée X d'une pièce
     * @return 
     */
    @Override
    public int getX() {
        return x;
    }
    
    /**
     * Retourne la coordonnée Y d'une pièce
     * @return 
     */
    @Override
    public int getY() {
        return y;
    }
    
    /**
     * Méthode abstraite.Vérifie qu'un déplacement sur la grille est autorisé
     * @param xFinal
     * @param yFinal
     * @param isCatching
     * @param isCastlingPossible
     * @return 
     */
    @Override
    public abstract boolean isMoveOk(int xFinal,
                     int yFinal,
                     boolean isCatching,
                     boolean isCastlingPossible);
    
    /**
     * Déplace la pièce dans les limites de la grille
     * @param xFinal
     * @param yFinal
     * @return 
     */
    @Override
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
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return name + "[" + couleur + "]" + "(" + x + "," + y + ")";
    }

}
