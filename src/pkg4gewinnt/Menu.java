/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4gewinnt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author seralichtenhahn
 */
class Menu extends MouseAdapter {
    
    private BufferedImage sprite;
    private Game game;
    private static final int WIDTH = 1180, HEIGHT = WIDTH / 10 * 9;
    
    public Menu(Game game) {
        loadImage();
        this.game = game;
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        
        //Neues Spiel
        if(mouseOver(mouseX, mouseY, WIDTH/2 - 200, 500, 400, 100) && this.game.gameStatus == Game.STATUS.Menu) {
            this.game.setGameStatus(Game.STATUS.Game);
        }
        
        //Beenden
        if(mouseOver(mouseX, mouseY, WIDTH/2 - 200, 640, 400, 100) && this.game.gameStatus == Game.STATUS.Menu) {
            this.game.getWindow().getFrame().dispatchEvent(new WindowEvent(this.game.getWindow().getFrame(), WindowEvent.WINDOW_CLOSING));
        }
    }
    
    public boolean mouseOver(int mouseX, int mouseY, int x , int y, int width, int height) {
        if(mouseX > x && mouseX < x + width) {
            if(mouseY > y && mouseY < y + height)
                return true;
        }
        return false;
    }
    
    public void tick() {
        
    }
    
    public void render(Graphics g) {
        g.setColor(new Color(0,0,255, 220));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        g.drawImage(sprite, WIDTH/2 - 200, 200, WIDTH/2 + 200, 400, 0, 0, 518, 277, null);
        g.drawImage(sprite, WIDTH/2 - 200, 500, WIDTH/2 + 200, 600, 0, 280, 518, 402, null);
        g.drawImage(sprite, WIDTH/2 - 200, 640, WIDTH/2 + 200, 740, 0, 403, 518, 525, null);
    }
    
    private void loadImage() {
        try {
            this.sprite = ImageIO.read(new File("src/assets/sprite.png"));
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
