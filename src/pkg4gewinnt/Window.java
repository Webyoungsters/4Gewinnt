/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4gewinnt;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author seralichtenhahn
 */
public class Window extends Canvas {
    
    private JFrame frame;
    
    public Window(String title, int width, int height, Game game) {
        
        this.frame = new JFrame();
        frame.setTitle(title);
        
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        game.start();
        
        
    }
    
    public void add(JComponent component) {
        this.frame.add(component);
    }
    
    public JFrame getFrame() {
        return this.frame;
    }
    
}
