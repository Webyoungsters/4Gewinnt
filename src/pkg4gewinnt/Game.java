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

/**
 *
 * @author I018
 */
public class Game extends Canvas implements Runnable {
    
    public static final int WIDTH = 1180, HEIGHT = WIDTH / 10 * 9;
    private final Board board;
    private final Handler handler;
    private final Window window;
    private final Menu menu;
    
    private Thread thread;
    private PointerInfo a;
    private Point b;
    private int mouseX;
    
    private boolean running = false;
    private boolean playerTurn;
    
    public enum STATUS {
        Menu,
        Game
    }
    
    public STATUS gameStatus = STATUS.Menu;
    
    public Game() {
        this.handler = new Handler();
        this.board = new Board(this.handler);
        this.menu = new Menu(this);
        this.window = new Window( "4 Gewinnt by Serafin Lichtenhahn", WIDTH, HEIGHT, this);
        this.playerTurn = true;
        
        listeners();
        
    }

    public void setGameStatus(STATUS gameStatus) {
        this.gameStatus = gameStatus;
    }
    
    public void gameMove() {
        if(!this.board.isGameOver()) {
            this.board.addPlayerMove(mouseX);
            this.playerTurn = false;
            if (this.board.isGameOver())
                return;
            this.board.callMiniMax();
            
            this.board.addComputerMove();
            this.playerTurn = true;
        }
    }
    
    //Teil des gefundenen GameLoops
    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    
    //Teil des gefundenen GameLoops
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
        if(this.gameStatus == STATUS.Game) {
            this.handler.tick();
            this.a = MouseInfo.getPointerInfo();
            this.b = a.getLocation();
            this.mouseX = (int) b.getX();
            this.mouseX -= window.getFrame().getLocation().getX();
        }
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
        
        if(this.gameStatus == STATUS.Game) {
            g.setColor(Color.red);
            g.fillOval(mouseX - 60, 0, 120, 120);
        }
        
        this.board.render(g);
        this.handler.render(g);
        
        if(this.gameStatus == STATUS.Menu)
            this.menu.render(g);
        
        
        
        g.dispose();
        bs.show();
    }

    private void listeners() {
        addMouseListener(new MouseAdapter() { 
          @Override
          public void mousePressed(MouseEvent me) {
            if(gameStatus == STATUS.Game && playerTurn)
                gameMove();
          }
        });
        addMouseListener(this.menu);
    }
    
    public Window getWindow() {
        return this.window;
    }
    
}