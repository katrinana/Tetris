package tetris;

/**
 * Created by katrinawang on 2016-09-29.
 */
public class Board {
    /*public static int sHeight = 30;
    public static int sWidth = 100;
    public static int height = 480;
    public static int width = 200;*/

    private int[][] board;

    public Board() {
        board = new int[24][10];
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = 0;
            }
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public int rotation(Piece cell) {
        int x_loca = cell.getLoca_x();
        int y_loca = cell.getLoca_y();
        int r_margin = cell.getRmargin();
        int l_margin = cell.getLmargin();
        int tmp = x_loca;
        if (x_loca < 0) x_loca+=l_margin;
        if (x_loca > 6) x_loca-=r_margin;
        cell.renewPiece(x_loca, y_loca);
        return tmp;

    }

    public void renewBoard(Piece cell) {
        int[][] c = cell.getCell();
        int x_loca = cell.getLoca_x();
        int y_loca = cell.getLoca_y();
        //int
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (c[i][j] == 1) board[y_loca + i][x_loca + j] = 1;
            }
        }
    }

    public boolean moveable(Piece cell, int x, int y) {
        boolean end = true;
        int[][] c = cell.getCell();
        int x_loca = cell.getLoca_x();
        int y_loca = cell.getLoca_y();
        x_loca+=x;
        y_loca+=y;
        try {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if ((c[i][j] == 1) && (board[y_loca + i][x_loca + j] == 1)) {
                        end = false;
                        break;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return end;
    }

    public int lines_to_delete() {
        int line = 0;
        for (int i = 23; i >= 0;) {
            boolean full = true;
            for (int j = 0; j < 10; j++) {
                if (board[i][j] == 0) {
                    full = false;
                    break;
                }
            }
            if (full) {
                for(int k = i; k > 0; k--) {
                    board[k] = board[k-1];
                }
                board[0] = new int[10];
                line++;
            } else {
                i--;
            }
        }
        return line;
    }

    public void move_to_end(Piece cell) {
        while(moveable(cell, 0, 1)) {
            cell.moveDown();
        }
    }


}
