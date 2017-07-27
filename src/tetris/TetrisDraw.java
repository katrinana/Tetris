package tetris;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bwbecker on 2016-09-19.
 */

public class TetrisDraw extends JComponent {
    public static Board board;
    public static Piece cell;
    private static int Wid = 200;
    private static int Hig = 30;
    public static Piece[] next;
    public static Piece[] hold;
    public static int nHold;
    public static int score;
    public static int level;
    private static boolean over;
    //public int psize = getSize();

    public TetrisDraw(Board board, Piece cell, Piece[] next, Piece[] hold, int numHold, int s, int l) {
        this.board = board;
        this.cell = cell;
        this.next = next;
        this.hold = hold;
        this.nHold = numHold;
        this.score = s;
        this.level = l;
        over = false;
    }

    public void renewDraw(Board board, Piece cell, Piece[] next, Piece[] hold, int numHold, int s, int l){
        this.board = board;
        this.cell = cell;
        this.next = next;
        this.hold = hold;
        this.nHold = numHold;
        this.score = s;
        this.level = l;
    }


    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        //g2.setPaint(Color.white);
        g2.drawString("Score: "+(int)score, 80, 100);
        g2.drawString("Level: "+ level, 480, 100);
        g2.drawString("Hold", 80, 150);
        g2.drawString("Next", 480, 150);
        g2.drawRect(Wid, Hig, 200, 480);

        int loca_x = cell.getLoca_x() * 20 + Wid;
        int loca_y = cell.getLoca_y() * 20 + Hig;
        drawPiece(loca_x, loca_y, g2, cell);
        for (int i = 0; i < 4; i++) {
            drawPiece(450, 170+i*90, g2, next[i]);
        }

        for(int i = nHold-1; i >= 0; i--) {
            drawPiece(50, 170+(nHold-1-i)*90, g2, hold[i]);
        }

        drawBoard(g2);

        if (over) {
            g2.setColor(Color.WHITE);
            g2.fillRect(Wid-50, Hig+150, 300, 150);
            g2.setColor(Color.RED);
            g2.setFont(new Font("Courier New", Font.BOLD, 20));
            g2.drawString("Gameover", Wid+30, Hig+230);
        }
    }

    public void drawBoard(Graphics2D g) {
        int[][] dBoard = board.getBoard();
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 10; j++) {
                if (dBoard[i][j] == 1) {
                    g.setColor(Color.darkGray);
                    g.fillRect((Wid + j * 20), (Hig + i * 20), 20, 20);
                }
            }
        }
    }

    public void drawPiece(int x, int y, Graphics2D g2, Piece p) {
        char sType = p.getC_type();
        int[][] drawCell = p.getCell();
        switch (sType) {
            case 'I' : //case 'I'
                g2.setColor(Color.blue);
                break;
            case 'L' : //case 'L'
                g2.setColor(Color.orange);
                break;
            case 'J' : //case 'J'
                g2.setColor(Color.cyan);
                break;
            case 'T' : //case 'T'
                g2.setColor(Color.red);
                break;
            case 'O' : //case 'O'
                g2.setColor(Color.yellow);
                break;
            case 'S' : //case 'S'
                g2.setColor(Color.green);
                break;
            case 'Z' : //case 'Z'
                g2.setColor(Color.pink);
                break;
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (drawCell[i][j] == 1) {
                    g2.fillRect(x + j*20, y + i*20, 20, 20);
                }
            }
        }
    }

    public boolean checkLocation(int x, int y) {
        int loca_x = cell.getLoca_x() * 20 + Wid;
        int loca_y = cell.getLoca_y() * 20 + Hig;
        if ((x > loca_x) && (x < loca_x + 4*20) &&
                (y > loca_y) && (y < loca_y + 4*20))
            return true;
        else return false;
    }

    public int checkPosition(int x, int y) {
        int loca_x = cell.getLoca_x() * 20 + Wid;
        int loca_y = cell.getLoca_y() * 20 + Hig;
        if (x > loca_x) return 1;
        else if (x < loca_x + 4*20) return 0;
        else return -1;
    }

    public void gameover() {
        over = true;
    }






    /*public Tetris(int fps, double speed, String sequence) {

    }*/

}