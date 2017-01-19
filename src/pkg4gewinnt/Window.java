/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4gewinnt;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author seralichtenhahn
 */
public class Window extends Canvas {
    
    private final JFrame frame;
    
    public Window(String title, int width, int height, Game game) {
        
        this.frame = new JFrame();
        this.frame.setTitle(title);
        
        this.frame.setPreferredSize(new Dimension(width, height));
        this.frame.setMinimumSize(new Dimension(width, height));
        this.frame.setMaximumSize(new Dimension(width, height));
        
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo(null);
        this.frame.add(game);
        this.frame.setVisible(true);
        game.start();
        
        
    }
    
    public JFrame getFrame() {
        return this.frame;
    }
    
}
