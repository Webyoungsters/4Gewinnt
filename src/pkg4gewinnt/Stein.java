/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4gewinnt;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author seralichtenhahn
 */
public class Stein {
    
    private int x;
    private int y;
    private final int WIDTH = 120, HEIGHT = 120;
    private int moveY;
    private final Color farbe;
    private final int row, col;
    
    public Stein(Color farbe, int col, int row) {
        this.farbe = farbe;
        this.x = col * 168 - 148;
        this.y = 0;
        this.moveY = 10;
        this.row = row;
        this.col = col;
    }
    
    public void stopMoving() {
        if(this.y >= (this.row - 1) * 148 + 140) {
            this.moveY = 0;
            this.y = (this.row - 1) * 147 + 140;
        }
    }
    
    public int getCenterX() {
        return x-= WIDTH/2;
    }
    
    public int getCenterY() {
        return y-= HEIGHT/2;
    }
    
    public void tick() {
        this.y += this.moveY;
        stopMoving();
        
    }
    
    public void render(Graphics g) {
        
        g.setColor(farbe);
        g.fillOval(x, y, WIDTH, HEIGHT); 
        
    }
    
}
