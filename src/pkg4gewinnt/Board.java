/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4gewinnt;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author I018
 */
public class Board {
    
    private final int ROW = 6, COL = 7, WIDTH = 1180, HEIGHT = WIDTH / 10 * 9;
    private final int[][] board;
    private final int intervalX = Math.round(WIDTH / COL);
    private final int intervalY = Math.round((HEIGHT - 180) / ROW);
    private final int MAX_DEPTH = 8;
    private final Handler handler;
    
    public List<Feld> verfuegbareFelder;
    
    private Feld computersMove;
    private Feld winFeld1, winFeld2;
    
    public Board(Handler handler) {
        this.board = new int[COL][ROW];
        this.handler = handler;
        this.winFeld1 = new Feld(0, 0);
        this.winFeld2 = new Feld(0, 0);
    }
    
    public void addPlayerMove(int mouseX) {
        int col = getColByCord(mouseX);
        for(int row = ROW; row > 0; row--) {
            if(this.board[col - 1][row - 1] == 0) {
                saveMove(new Feld(col - 1, row - 1), 2);
                this.handler.addStein(new Stein(Color.RED, new Feld(col, row)));
                //displayBoard();
                break;
            }
        }
    }
    
    public void addComputerMove() {
       this.handler.addStein(new Stein(Color.YELLOW, new Feld(this.computersMove.getX() + 1, this.computersMove.getY() + 1), 150));
       saveMove(this.computersMove, 1);
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
        if(feld != null)
            this.board[feld.getX()][feld.getY()] = player;
    }
    
    public void resetMove(Feld feld) {
        if(feld != null)
            this.board[feld.getX()][feld.getY()] = 0;
    }
    
    //Zum einfacheren debuggen. wird im fertigen Projekt nicht gebraucht. 
    public void displayBoard() {
        System.out.println();

        for (int i = 0; i < ROW; ++i) {
            for (int j = 0; j < COL; ++j) {
                System.out.print(board[j][i] + " ");
            }
            System.out.println();

        }
    }
    
    public void callMiniMax() {
        minimax(0, 1);
    }
    
    
    //basiert auf dem MiniMax Algorithmus.
    //https://de.wikipedia.org/wiki/Minimax-Algorithmus
    public int minimax(int depth, int spieler) {
       if(hasPlayerWon())
           return -1;
       if(hasComputerWon())
           return +1;
       
       int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
       
       List<Feld> tempVerfuegbareFelder = getVerfuegbareFelder();
       if(tempVerfuegbareFelder.isEmpty())
           return 0;
       
       if(depth == this.MAX_DEPTH)
           return min;
       
       int zeroCounter = 0;
       for(int i = 0; i < tempVerfuegbareFelder.size(); i++) {
           Feld feld = tempVerfuegbareFelder.get(i);
           if(spieler == 1) {
               saveMove(feld, 1);
                int currentScore = minimax(depth + 1, 2);
                max = Math.max(currentScore, max);
               
                if(depth == 0) {
                   if(currentScore == 0)
                       zeroCounter++;
                }
                   
                   
               if(currentScore >= 0 && depth == 0) {
                   this.computersMove = feld;
               }
               if(currentScore == 1) {
                   resetMove(feld);
                   break;
               }
               if(i == tempVerfuegbareFelder.size() - 1 && max < 0) {
                   if(depth == 0)
                       this.computersMove = feld;
               }
               
               if(zeroCounter == 7 && tempVerfuegbareFelder.size() == 7)
                   this.computersMove = tempVerfuegbareFelder.get(3);
               
           } else if(spieler == 2) {
               saveMove(feld, 2);
               int currentScore = minimax(depth + 1, 1);
               min = Math.min(currentScore, min);
               if(min == -1) {
                   resetMove(feld);
                   break;
               }
           }
            resetMove(feld);
       }
       return spieler == 1 ? max : min;
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
        
        if(this.hasComputerWon() || this.hasPlayerWon()) {
            g.setColor(Color.GREEN);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(7));
            g.drawLine(this.winFeld1.getX() * intervalX + 80,this.winFeld1.getY() * intervalY + 200, this.winFeld2.getX() * intervalX + 80, this.winFeld2.getY() * intervalY + 200);
        }
    }
    
}
