/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4gewinnt;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import javax.swing.JPanel;

/**
 *
 * @author I018
 */
public class Game extends Canvas implements Runnable {
    
    private static final int WIDTH = 1180, HEIGHT = WIDTH / 10 * 9;
    private Board board;
    private Player player;
    private Thread thread;
    private Handler handler;
    private boolean running = false;
    private final Window window;
    private int mouseX;
    private PointerInfo a;
    private Point b;
    
    public Game() {
        this.handler = new Handler();
        this.window = new Window( "4 Gewinnt by Serafin Lichtenhahn", WIDTH, HEIGHT, this);
        this.player = new Player();
        this.board = new Board();
        
        listeners();
    }
    
    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    
    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    
    @Override
    public void run() {
        
        //Game Loop -- gefunden auf Stack Overflow
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1){
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("Fps " + frames);
                frames = 0;
            }

        }
        stop();
        
    }
    
    public void tick() {
        this.board.tick();
        this.handler.tick();
        
        this.a = MouseInfo.getPointerInfo();
        this.b = a.getLocation();
        this.mouseX = (int) b.getX();
        this.mouseX -= window.getFrame().getLocation().getX();
    }
    
    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs ==  null) {
            this.createBufferStrategy(3);
            return;
        }       
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        g.setColor(Color.red);
        g.fillOval(mouseX - 60, 0, 120, 120);
        
        this.board.render(g);
        this.handler.render(g);
        
        g.dispose();
        bs.show();
    }

    private void listeners() {
        addMouseListener(new MouseAdapter() { 
          @Override
          public void mousePressed(MouseEvent me) {
              int col = board.getCol(mouseX);
              int row = board.getRow(mouseX);
              handler.addStein(new Stein(Color.RED, col,row));
          } 
        });
    }
    
}