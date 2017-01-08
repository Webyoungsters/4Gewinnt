/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4gewinnt;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author I018
 */
public class Board {
    
    private final int ROW = 6, COL = 7, WIDTH = 1180, HEIGHT = WIDTH / 10 * 9;
    private int[][] board = new int[7][6];
    
    public List<Feld> verfuegbareFelder;
    
    private Feld computersMove;
    
    private final Handler handler;
    
    private Feld winFeld1, winFeld2;
    
    private final int intervalX = Math.round(WIDTH / COL);
    private final int intervalY = Math.round((HEIGHT - 180) / ROW);
    
    public Board(Handler handler) {
        this.handler = handler;
    }
    
    public void addPlayerMove(int mouseX) {
        int col = getColByCord(mouseX);
        int x = 0;
        for(int row = ROW; row > 0; row--) {
            if(this.board[col - 1][row - 1] == 0) {
                saveMove(new Feld(col - 1, row - 1), 2);
                this.handler.addStein(new Stein(Color.RED, new Feld(col, row)));
                displayBoard();
                break;
            }
        }
    }
    
    public void addComputerMove() {
       
       this.verfuegbareFelder = getVerfuegbareFelder();
       int randomNum = 1 + (int)(Math.random() * this.verfuegbareFelder.size());
       
       Feld feld = this.verfuegbareFelder.get(randomNum);
       Feld steinFeld = new Feld(feld.getX() + 1, feld.getY() + 1);
       this.handler.addStein(new Stein(Color.YELLOW, steinFeld));
       saveMove(feld, 1);
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
    
    public boolean isGameOver() {
        return (hasComputerWon() || hasPlayerWon() || getVerfuegbareFelder().isEmpty());
    }
    
    public Feld getComputerMove() {
        return this.computersMove;
    }
    
    public List<Feld> getVerfuegbareFelder() {
        this.verfuegbareFelder = new ArrayList<>();
        for (int col = 0; col < COL; col++) {
            for (int row = 5; row >= 0; row--) {
                if(this.board[col][row] == 0) {
                    verfuegbareFelder.add(new Feld(col, row));
                    break;
                }
            }
        }
        return verfuegbareFelder;
    }
    
    public void saveMove(Feld feld, int player) {
        this.board[feld.getX()][feld.getY()] = player;   //player = 1 for X, 2 for O
    }
    
    public void displayBoard() {
        System.out.println();

        for (int i = 0; i < ROW; ++i) {
            for (int j = 0; j < COL; ++j) {
                System.out.print(board[j][i] + " ");
            }
            System.out.println();

        }
    }
    
   public int minimax(int depth, int turn) {
       return 0;
    }
    
    
    
    public boolean hasPlayerWon() {
        return (checkDiagonal(2) || checkVertical(2) || checkHorizontal(2));
    }

    public boolean hasComputerWon() {
        return (checkDiagonal(1) || checkVertical(1) || checkHorizontal(1));
    }
    
    public boolean checkDiagonal(int player) {
        for(int col = 3; col < COL; col++) {
            for(int row =  3; row < ROW; row++) {
                if(this.board[col][row] == player) {
                    if(this.board[col - 1][row - 1] == player) {
                        if(this.board[col - 2][row - 2] == player) {
                            if(this.board[col - 3][row - 3] == player) {
                                System.out.println("richtige Lösung: " + (col - 3) + "/" + (row-3) + " bis " + col + "/" + row);
                                this.winFeld1 = new Feld(col, row);
                                this.winFeld2 = new Feld(col - 3, row - 3);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        for(int col = 0; col < COL - 3; col++) {
            for(int row =  3; row < ROW; row++) {
                if(this.board[col][row] == player) {
                    if(this.board[col + 1][row - 1] == player) {
                        if(this.board[col + 2][row - 2] == player) {
                            if(this.board[col + 3][row - 3] == player) {
                                System.out.println("richtige Lösung: " + (col + 3) + "/" + (row-3) + " bis " + col + "/" + row);
                                this.winFeld1 = new Feld(col, row);
                                this.winFeld2 = new Feld(col + 3, row - 3);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        
        return false;
    }
    
    public boolean checkVertical(int player) {
        for(int col = 0; col < COL; col++) {
            for(int row = 0; row < ROW - 3; row++) {
                if(this.board[col][row] == player) {
                    if(this.board[col][row + 1] == player) {
                        if(this.board[col][row + 2] == player) {
                            if(this.board[col][row + 3] == player) {
                                System.out.println("richtige Lösung: " + col + "/" + row + " bis " + col + "/" + (row + 3));
                                this.winFeld1 = new Feld(col, row);
                                this.winFeld2 = new Feld(col, row + 3);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public boolean checkHorizontal(int player) {
        for(int col = 0; col < COL - 3; col++) {
            for(int row = 0; row < ROW; row++) {
                if(this.board[col][row] == player) {
                    if(this.board[col + 1][row] == player) {
                        if(this.board[col + 2][row] == player) {
                            if(this.board[col + 3][row] == player) {
                                System.out.println("richtige Lösung: " + col + "/" + row + " bis " + (col + 3) + "/" + row);
                                this.winFeld1 = new Feld(col, row);
                                this.winFeld2 = new Feld(col + 3, row);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
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
        
        if(isGameOver()) {
            g.setColor(Color.GREEN);
            g.drawLine(this.winFeld1.getX() * intervalX + 80, this.winFeld1.getY() * intervalY + 200, this.winFeld2.getX() * intervalX + 80, this.winFeld2.getY() * intervalY + 200);
        }
    }
    
}
