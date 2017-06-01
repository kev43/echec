/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue;

import controler.ChessGameControlers;
import controler.controlerLocal.ChessGameControler;
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
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

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
    
    public ChessGameGUI(String title, ChessGameControlers chessGameControler, Dimension dim) {
        super(title);
        this.chessGameControler = chessGameControler;
        
         Dimension boardSize = new Dimension(600, 600);

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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        chessPiece = null;
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
        layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (chessPiece == null) {
            return;
        }

        chessPiece.setVisible(false);
        Component c = chessBoard.findComponentAt(e.getX(), e.getY());

        if (c instanceof JLabel) {
            Container parent = c.getParent();
            parent.remove(0);
            parent.add(chessPiece);
        } else {
            Container parent = (Container) c;
            parent.add(chessPiece);
        }

        chessPiece.setVisible(true);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (chessPiece == null) {
            return;
        }
        chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }

    @Override
    public void update(Observable o, Object arg) {

    }
    
}
