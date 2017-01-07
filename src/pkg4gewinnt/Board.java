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
    private int[][] board = new int[7][6];
    
    private final int intervalX = Math.round(WIDTH / COL);
    private final int intervalY = Math.round((HEIGHT - 180) / ROW);
    
    public int getCol(int mouseX) {
        int col = getColByCord(mouseX);
        int x = 0;
        for(int row = ROW; row > 0; row--) {
            if(this.board[col - 1][row - 1] == 0) {
                this.board[col - 1][row - 1] = 1;
                x = row;
                break;
            }
        }
        System.out.println("col: " + col + " / row: " + x);
        return col;
    }
    
    public int getRow(int mouseX) {
        int col = getColByCord(mouseX);
        int row = 0;
        for(int x = 1; x <= ROW; x++) {
            if(this.board[col - 1][x - 1] == 1) {
                row = x;
                break;
            }
        }
        return row;
    }
    
    private int getColByCord(int cordX) {
        int value = 0;
        for(int x = 0; x < COL; x++) {
            if(cordX >= x * intervalX && cordX <= (x + 1) * intervalX) {
                value = x + 1;
                break;
            }
        }
        return value;
    }
    
    public void tick() {
        
    }
    
    public void render(Graphics g) {
        
        g.setColor(Color.blue);
        g.fillRect(0, 120, WIDTH, HEIGHT - 120);
          
        g.setColor(Color.DARK_GRAY);
        for(int border = 1; border <= COL; border++)
            g.fillRect(border * intervalX - 1, 0, 2, 120);
        
        g.setColor(Color.lightGray);
        for(int x = 0; x < COL; x ++) {
            for(int y = 0; y < ROW; y++) {
                g.fillOval(x * intervalX + 20, y * intervalY + 140, 120, 120);
            }
        }
    }
    
}
