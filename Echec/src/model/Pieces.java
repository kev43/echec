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
    /**
     * 
     * @return 
     */
    public boolean capture();
    
    /**
     * 
     * @return 
     */
    public Couleur getCouleur();
    
    /**
     * 
     * @return 
     */
    public String getName();
    
    /**
     * 
     * @return 
     */
    public int getX();
    
    /**
     * 
     * @return 
     */
    public int getY();
    
    /**
     * 
     * @param xFinal
     * @param yFinal
     * @param isCatching
     * @param isCastlingPossible
     * @return 
     */
    boolean isMoveOk(int xFinal,
                     int yFinal,
                     boolean isCatching,
                     boolean isCastlingPossible);
    
    /**
     * 
     * @param xFinal
     * @param yFinal
     * @return 
     */
    boolean move(int xFinal, int yFinal);
}
