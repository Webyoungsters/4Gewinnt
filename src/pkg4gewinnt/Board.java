/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4gewinnt;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

/**
 *
 * @author I018
 */
public class Board {
    
    private final int ROW = 6, COL = 7, WIDTH = 1180, HEIGHT = WIDTH / 10 * 9;
    LinkedList<Stein> steine = new LinkedList<Stein>();
    
    public void add(Stein stein) {
        steine.add(stein);
    }
    
    public void tick() {
        
        for(int i = 0; i < steine.size(); i++) {
            Stein tempStein = steine.get(i);
            tempStein.tick();
        }
        
    }
    
    public void render(Graphics g) {
        
        g.setColor(Color.blue);
        g.fillRect(0, 120, WIDTH, HEIGHT - 120);
        
        int intervalX = Math.round(WIDTH / COL);
        int intervalY = Math.round((HEIGHT - 180) / ROW);
        g.setColor(Color.lightGray);
        for(int y = 0; y < ROW; y ++) {
            
            for(int x = 0;x < COL; x++) {
                g.fillOval(x * intervalX + 20, y * intervalY + 140, 120, 120);
            }
            
        }
        
        for(int i = 0; i < steine.size(); i++) {
            Stein tempStein = steine.get(i);
            tempStein.render(g);
        }
        
    }
    
}
