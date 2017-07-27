package tetris;

import java.util.Random;
import java.awt.*;

/**
 * Created by katrinawang on 2016-09-29.
 */
public class Piece{
    private int[][] cell = new int[4][4];
    private int loca_x;
    private int loca_y;
    private char c_type;
    private int r_margin;
    private int l_margin;
    private int b_margin;

    public Piece(int lx, int ly, char seq) {
        loca_x = lx;
        loca_y = ly;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cell[i][j] = 0;
            }
        }
        l_margin = 0;
        r_margin = 0;
        Random rand = new Random();
        int ct = rand.nextInt(7) + 0;
        switch (seq) {
            case 'I' : //case 'I'
                c_type = 'I';
                cell[0][1] = 1;
                cell[1][1] = 1;
                cell[2][1] = 1;
                cell[3][1] = 1;
                break;
            case 'L' : //case 'L'
                c_type = 'L';
                cell[0][1] = 1;
                cell[1][1] = 1;
                cell[2][1] = 1;
                cell[2][2] = 1;
                break;
            case 'J' : //case 'J'
                c_type = 'J';
                cell[0][2] = 1;
                cell[1][2] = 1;
                cell[2][1] = 1;
                cell[2][2] = 1;
                break;
            case 'T' : //case 'T'
                c_type = 'T';
                cell[1][1] = 1;
                cell[1][2] = 1;
                cell[1][3] = 1;
                cell[2][2] = 1;
                break;
            case 'O' : //case 'O'
                c_type = 'O';
                cell[1][1] = 1;
                cell[1][2] = 1;
                cell[2][2] = 1;
                cell[2][1] = 1;
                break;
            case 'S' : //case 'S'
                c_type = 'S';
                cell[1][2] = 1;
                cell[1][3] = 1;
                cell[2][1] = 1;
                cell[2][2] = 1;
                break;
            case 'Z' : //case 'Z'
                c_type = 'Z';
                cell[1][1] = 1;
                cell[1][2] = 1;
                cell[2][2] = 1;
                cell[2][3] = 1;
                break;
        }

        for (int i = 3; i >= 0; i--) {
            boolean resize = true;
            for (int j = 0; j < 4; j++) {
                if (cell[j][i] == 1) resize = false;
            }
            if (resize) r_margin++;
        }

        for (int i = 0; i < 4; i++) {
            boolean resize = true;
            for (int j = 0; j < 4; j++) {
                if (cell[j][i] == 1) resize = false;
            }
            if (resize) l_margin++;
        }

    }


    public int[][] getCell() {
        return cell;
    }

    public char getC_type() {
        return c_type;
    }

    public void renewPiece(int x, int y) {
        loca_x = x;
        loca_y = y;
        l_margin = 0;
        r_margin = 0;
        for (int i = 0; i < 4; i++) {
            boolean resize = true;
            for (int j = 0; j < 4; j++) {
                if (cell[j][i] == 1) resize = false;
            }
            if (resize) l_margin++;
        }
        for (int i = 3; i >= 0; i--) {
            boolean resize = true;
            for (int j = 0; j < 4; j++) {
                if (cell[j][i] == 1) resize = false;
            }
            if (resize) r_margin++;
        }
    }

    public int getLoca_x() {
        return loca_x;
    }

    public int getLoca_y() {
        return loca_y;
    }

    public void moveRight() {
        loca_x++;
        renewPiece(loca_x, loca_y);
    }

    public void moveLeft() {
        loca_x--;
        renewPiece(loca_x, loca_y);
        //loca_x-=c_size;
    }

    public void moveDown() {
        loca_y++;
        //loca_y+=c_size;
        for(int i = 3; i >= 0; i--) {
            boolean resize = true;
            for (int j = 0; j < 4; j++) {
                if (cell[i][j] == 1) resize = false;
            }
            if (resize) b_margin++;
        }
    }

    public int getLmargin() {
        return l_margin;
    }

    public int getRmargin() {
        return r_margin;
    }

    public int getBmargin() {
        return b_margin;
    }

    public void rotateCell() {
        int[][] tmp = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tmp[i][j] = cell[j][3-i];
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cell[i][j] = tmp[i][j];
            }
        }
        renewPiece(loca_x, loca_y);
    }

    public void rotateRCell() {
        int[][] tmp = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tmp[i][j] = cell[3-j][i];
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cell[i][j] = tmp[i][j];
            }
        }
        renewPiece(loca_x, loca_y);
    }
}