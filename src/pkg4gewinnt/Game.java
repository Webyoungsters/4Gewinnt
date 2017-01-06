/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4gewinnt;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 *
 * @author I018
 */
public class Game extends Canvas implements Runnable {
    
    private static final int WIDTH = 1180, HEIGHT = WIDTH / 10 * 9;
    private Board board;
    private Thread thread;
    private boolean running = false;
    
    public Game() {
        new Window( "4 Gewinnt", WIDTH, HEIGHT, this);
        this.board = new Board();
        this.board.add(new Stein(Farbe.ROT));
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
    }   
    
    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs ==  null) {
            this.createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        
        //g.setColor(Color.magenta);
        //g.fillRect(0, 0, WIDTH, HEIGHT);
        
        this.board.render(g);
        
        g.dispose();
        bs.show();
    }
    
}
