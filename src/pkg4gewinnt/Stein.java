/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4gewinnt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;

/**
 *
 * @author seralichtenhahn
 */
public class Stein {
    
    private int x;
    private int y;
    private final int WIDTH = 120, HEIGHT = 120;
    private int moveY;
    private final Farbe farbe;
    private Color color;
    private PointerInfo a;
    private Point b;
    
    public Stein(Farbe farbe) {
        this.farbe = farbe;
        if(Farbe.)
    }
    
    public void startMoving() {
        //moveY = 10;
    }
    
    public void stopMoving() {
        //moveY = 0;
    }
    
    public int getCenterX() {
        return x-= WIDTH;
    }
    
    public int getCenterY() {
        return y-= HEIGHT;
    }
    
    public void tick() {
        //y += moveY;
        
        double mouseX = MouseInfo.getPointerInfo().getLocation().getX();
        double mouseY = MouseInfo.getPointerInfo().getLocation().getY();
        x = (int) mouseX;
        y = (int) mouseY;
        System.out.println(x);
    }
    
    public void render(Graphics g) {
        
        g.setColor(color);
        g.fillOval(getCenterX(), getCenterY(), WIDTH, HEIGHT);
        
    }
    
}
