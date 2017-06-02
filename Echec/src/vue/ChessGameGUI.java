/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import controler.ChessGameControlers;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import model.Coord;
import model.Couleur;
import model.PieceIHM;
import tools.ChessImageProvider;

/**
 *
 * @author david.vivier
 */
public class ChessGameGUI extends JFrame implements MouseListener, MouseMotionListener, Observer {

    private ChessGameControlers chessGameControler;
    private JLayeredPane layeredPane;
    private JPanel chessBoard;
    private JLabel chessPiece;
    private int xAdjustment;
    private int yAdjustment;

    // Coordonnées de la position initiale de la pièce déplacée
    private Coord coordInit;

    /**
     * Constrcuteur de la classe ChessGameGUI. 
     * Méthode crée les composants de l'interface graphique et les affichent.
     * @param title
     * @param chessGameControler
     * @param dim 
     */
    public ChessGameGUI(String title, ChessGameControlers chessGameControler, Dimension dim) {
        super(title);
        this.chessGameControler = chessGameControler;

        Dimension boardSize = new Dimension(800, 800);

        //  Use a Layered Pane for this this application
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize(boardSize);
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);

        //Add a chess board to the Layered Pane 
        chessBoard = new JPanel();
        layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
        chessBoard.setLayout(new GridLayout(8, 8));
        chessBoard.setPreferredSize(boardSize);
        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel(new BorderLayout());
            chessBoard.add(square);

            int row = (i / 8) % 2;
            if (row == 0) {
                square.setBackground(i % 2 == 0 ? Color.DARK_GRAY : Color.white);
            } else {
                square.setBackground(i % 2 == 0 ? Color.white : Color.DARK_GRAY);
            }
        }

    }

    /**
     * Méthode mouseClicked non utilisée.
     * @param e 
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Vérifie que le joueur actuel a le droit de déplacer la pièce choisie , ajoute la pièce à un DRAG_LAYER et montre les différents déplacements possibles.
     * @param e 
     */
    @Override
    public void mousePressed(MouseEvent e) {

        int x = e.getX() / (this.chessBoard.getWidth() / 8);
        int y = e.getY() / (this.chessBoard.getHeight() / 8);
        coordInit = new Coord(x, y);
        //parentInit = c.getParent();
        chessPiece = null;
        if (chessGameControler.isPlayerOK(coordInit)) {

            
            Component c = chessBoard.findComponentAt(e.getX(), e.getY());

            if (c instanceof JPanel) {
                return;
            }

            Point parentLocation = c.getParent().getLocation();
            xAdjustment = parentLocation.x - e.getX();
            yAdjustment = parentLocation.y - e.getY();
            chessPiece = (JLabel) c;
            chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
            chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
            layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
            
            //ajout pour le show possible move
            showPossibleMoves(x,y);
            
            
            
        } else {
            System.out.println("Ce n'est pas le bon joueur");
        }
    }

    /**
     * Pose la pièce déplacée sur la case choisie si le déplacement est autorisé, sinon la pièce est remise à sa position initiale
     * @param e 
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (chessPiece == null) {
            return;
        }

        chessPiece.setVisible(false);
        Component c = chessBoard.findComponentAt(e.getX(), e.getY());

        int x = e.getX() / (this.chessBoard.getWidth() / 8);
        int y = e.getY() / (this.chessBoard.getHeight() / 8);
        //System.out.println("MouseReleased() - (" + x + "," + y + ")");
        Coord coordFinal = new Coord(x, y);

        boolean moveAllowed = chessGameControler.move(coordInit, coordFinal);
        //System.out.println("moveAllowed=" + moveAllowed);
        //System.out.println(chessGameControler.getMessage());
        //if (true) {
        // mouvement autorisé
        if (c instanceof JLabel) {
            // il y a déjà un JLabel (pièce)
            Container parent = c.getParent();
            parent.remove(0);
            parent.add(chessPiece);
        } else {
            Container parent = (Container) c;
            parent.add(chessPiece);
        }
        //} else {
        // mouvement interdit
        //c = parentInit;
        //}

        chessPiece.setVisible(true);
    }

    /**
     * Méthode mouseEntered non utilisée
     * @param e 
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Méthode mouseExited non utilisée
     * @param e 
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Déplace la pièce cliquée en fonction de la position de la souris
     * @param e 
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (chessPiece == null) {
            return;
        }
        chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
    }

    /**
     * Méthode mouseMoved non utilisée
     * @param e 
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        
        /*System.out.println("("+e.getX()+","+e.getY()+")");
        int x = e.getX() / (this.chessBoard.getWidth() / 8);
        int y = e.getY() / (this.chessBoard.getHeight() / 8);
        System.out.println("("+x+","+y+")");*/
    }

    /**
     * Raffraichit l'affichage de l'interface graphique  en fonction des modifications appliquées au modèle après un déplacement.
     * @param o
     * @param arg 
     */
    @Override
    public void update(Observable o, Object arg) {
        System.out.println(chessGameControler.getMessage());

        chessBoard.removeAll();

        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel(new BorderLayout());
            chessBoard.add(square);

            int row = (i / 8) % 2;
            if (row == 0) {
                square.setBackground(i % 2 == 0 ? Color.DARK_GRAY : Color.white);
            } else {
                square.setBackground(i % 2 == 0 ? Color.white : Color.DARK_GRAY);
            }
        }

        List<PieceIHM> piecesIHM = (List<PieceIHM>) arg;

        for (PieceIHM pieceIHM : piecesIHM) {

            Couleur color = pieceIHM.getCouleur();

            int x = pieceIHM.getX();
            int y = pieceIHM.getY();
            String fileName = ChessImageProvider.getImageFile(pieceIHM.getNamePiece(), color);
            JLabel piece = new JLabel(new ImageIcon(fileName));
            JPanel panel = (JPanel) chessBoard.getComponent(8 * y + x);

            panel.add(piece);
        }
        //affichage des cases menacees
        showCasesMenaces();
    }
    
    /**
     * Affiche sur l'interface les différents movements possibles pour la pièce sélectionnée
     * @param xInit
     * @param yInit 
     */
    private void showPossibleMoves(int xInit , int yInit) {
        
        //appel de la méthode du controleur qui retourne la liste des coordonnée à modifier
        List<Coord> listCoord = new LinkedList<>(chessGameControler.getPossibleMoves(xInit,yInit));
        
        for (Coord coord : listCoord) {
            int x = (coord.x * (this.chessBoard.getWidth() / 8));
            int y = (coord.y * (this.chessBoard.getWidth() / 8));
            
            Component c = chessBoard.getComponentAt(x, y);
            
            if (c instanceof JLabel) {
                // il y a déjà un JLabel (pièce)
                JPanel parent = (JPanel) c.getParent();
                //parent.setBackground(Color.ORANGE);
                parent.setBorder(BorderFactory.createLineBorder(Color.GREEN , 5));
                
            } else {
                JPanel parent = (JPanel) (Container) c;
                //parent.setBackground(Color.ORANGE);
                parent.setBorder(BorderFactory.createLineBorder(Color.GREEN,5));
            }
            
            //chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
            
            
        }
    }
        
        private void showCasesMenaces() {
        
        //appel de la méthode du controleur qui retourne la liste des coordonnée à modifier
        List<Coord> listCoord = new ArrayList<>(chessGameControler.getCasesMenacees());
        System.out.println("Liste coord" + listCoord.toString());
        
        for (Coord coord : listCoord) {
            int x = (coord.x * (this.chessBoard.getWidth() / 8));
            int y = (coord.y * (this.chessBoard.getWidth() / 8));
            System.out.println("("+x+","+y+")");
            Component c = chessBoard.getComponentAt(x, y);
            
            System.out.println(c.toString());
            
            if (c instanceof JLabel) {
                // il y a déjà un JLabel (pièce)
                JPanel parent = (JPanel) c.getParent();
                //parent.setBackground(Color.ORANGE);
                parent.getComponentAt(x, y).setBackground(Color.ORANGE);
            } else {
                JPanel parent = (JPanel) (Container) c;
                parent.setBackground(Color.ORANGE);
                //parent.setBorder(BorderFactory.createLineBorder(Color.ORANGE,5));
            }
            
            //chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
            
            
        }
        
        
    }
    
    

}
