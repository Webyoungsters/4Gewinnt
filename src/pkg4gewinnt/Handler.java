/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4gewinnt;

import java.awt.Graphics;
import java.util.LinkedList;

/**
 *
 * @author seralichtenhahn
 */
public class Handler {
    
    private LinkedList<Stein> steine = new LinkedList<Stein>();
    
    public void tick() {
        for(int i = 0; i < steine.size(); i++) {
            Stein tempStein = steine.get(i);
            tempStein.tick();
        }
    }
    
    public void render(Graphics g) {
        for(int i = 0; i < steine.size(); i++) {
            Stein tempStein = steine.get(i);
            tempStein.render(g);
        }
    }
    
    public void addStein(Stein stein) {
        this.steine.add(stein);
    }
    
}
