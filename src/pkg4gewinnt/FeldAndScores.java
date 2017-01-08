/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4gewinnt;

/**
 *
 * @author seralichtenhahn
 */
class FeldAndScores {
    
    private final int score;
    private final Feld feld;

    FeldAndScores(int score, Feld feld) {
        this.score = score;
        this.feld = feld;
    }

    public int getScore() {
        return score;
    }

    public Feld getFeld() {
        return feld;
    }
    
    
}
