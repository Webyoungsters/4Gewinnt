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
    private int delay;
    private boolean visible;
    
    public Stein(Color farbe, Feld feld) {
        this.farbe = farbe;
        this.x = feld.getX() * 168 - 148;
        this.y = 0;
        this.moveY = 10;
        this.row = feld.getY();
        this.col = feld.getX();
        this.delay = 0;
        this.visible = true;
    }
    
    public Stein(Color farbe, Feld feld, int delay) {
        this.farbe = farbe;
        this.x = feld.getX() * 168 - 148;
        this.y = 0;
        this.moveY = 10;
        this.row = feld.getY();
        this.col = feld.getX();
        this.delay = delay;
        this.visible = false;
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
        if(visible) {
            this.y += this.moveY;
            stopMoving();
        } else {
            this.delay -= 2;
        }
        
        if(this.delay < 0)
            this.visible = true;
    }
    
    public void render(Graphics g) {
        if(visible) {
            g.setColor(farbe);
            g.fillOval(x, y, WIDTH, HEIGHT);
        }
    }
    
}
