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
public interface Pieces {
    public boolean capture();
    
    public Couleur getCouleur();
    
    public String getName();
    
    public int getX();
    
    public int getY();
    
    boolean isMoveOk(int xFinal,
                     int yFinal,
                     boolean isCatchOk,
                     boolean isCastlingPossible);
    
    boolean move(int xFinal, int yFinal);
}
