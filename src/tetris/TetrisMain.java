package tetris;

import javafx.scene.control.Cell;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.*;


/**
 * Created by bwbecker on 2016-09-19.
 */
public class TetrisMain implements KeyListener {
    public static int FPS;
    public static double speed;
    public static TetrisDraw draw;
    private static Board board;
    private static Piece currentCell;
    private static Piece[] nextCell, holdCell;
    private static Timer timer;
    private static boolean pause;
    private static boolean updateP;
    private static ActionListener al;
    private static int score;
    private static int numHold;
    private static char[] seq;
    private static boolean select;
    private static MouseListener mouseListener;
    private static MouseMotionListener mouseMotionListener;
    private static MouseWheelListener mouseWheelListener;
    private static int level;
    private static int record;

    public TetrisMain(int fps, double sp, String sequence)   {
        JWindow Splash = new JWindow();
        String filename = System.getProperty("user.dir") + "/Tetris.jpg";

        try {
            JLabel label = new JLabel(new ImageIcon(filename));
            Splash.getContentPane().add(label, BorderLayout.CENTER);
            Splash.setSize(600, 600);
            Splash.setLocation(200, 200);
            Splash.setVisible(true);
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Splash.setVisible(false);
        Splash.dispose();

        JFrame Tframe = new JFrame("Tetris");
        FPS = fps;
        speed = sp;
        board = new Board();
        seq = sequence.toCharArray();
        record = 0;
        currentCell = new Piece(2, 0, seq[record]);
        record++;
        nextCell = new Piece[4];
        holdCell = new Piece[4];
        numHold = 0;
        for(int i = 0; i < 4; i++) {
            if (record == seq.length - 1) record = 0;
            nextCell[i] = new Piece(0, 0, seq[record]);
            record++;
        }
        seq = Arrays.copyOfRange(seq, 5, seq.length-1);
        updateP = false;
        score = 0;
        level = 1;
        draw = new TetrisDraw(board, currentCell, nextCell, holdCell, numHold, score, level);
        Tframe.add(draw);
        Tframe.setSize(600, 600);
        Tframe.setLocation(200, 200);
        Tframe.setDefaultCloseOperation(Tframe.EXIT_ON_CLOSE);
        Tframe.setVisible(true);
        Tframe.setResizable(true);
        Tframe.addKeyListener(this);
        al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerStart();
            }
        };
        timer = new Timer(1000/FPS, al);
        timer.start();
        pause = false;
        timer.setDelay((1000*(int)speed)/24);
        select = false;
        mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (select) {
                    if (board.moveable(currentCell, 0, 1)) board.move_to_end(currentCell);
                    select = false;
                } else {
                    int px = e.getX();
                    int py = e.getY();
                    if (draw.checkLocation(px, py))
                        select = true;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
        Tframe.addMouseListener(this.mouseListener);

        mouseMotionListener = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (e.getID() == MouseEvent.MOUSE_MOVED && select){
                    int px = e.getX();
                    int py = e.getY();
                    int po = draw.checkPosition(px, py);
                    if (po == 0) {
                        if (board.moveable(currentCell, -1, 0)) currentCell.moveLeft();
                    } else if (po == 1) {
                        if (board.moveable(currentCell, 1, 0)) currentCell.moveRight();
                    } else {}
                }
            }
        };
        Tframe.addMouseMotionListener(mouseMotionListener);

        mouseWheelListener = new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int amount = e.getWheelRotation();
                while (amount != 0) {
                    if (amount > 0) {
                        rotate(1);
                        amount--;
                    } else {
                        rotate(0);
                        amount++;
                    }
                }
            }
        };
        Tframe.addMouseWheelListener(mouseWheelListener);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int k = e.getKeyCode();
        if (!pause) {
            switch (k) {
                case KeyEvent.VK_CONTROL:
                    rotate(1);
                    break;
                case KeyEvent.VK_SPACE:
                    if (board.moveable(currentCell, 0, 1)) board.move_to_end(currentCell);
                    break;
                case KeyEvent.VK_LEFT:
                    if (board.moveable(currentCell, -1, 0)) currentCell.moveLeft();
                    break;
                case KeyEvent.VK_RIGHT:
                    if (board.moveable(currentCell, 1, 0)) currentCell.moveRight();
                    break;
                case KeyEvent.VK_UP:
                    rotate(0);
                    break;
                case KeyEvent.VK_4:
                    if (board.moveable(currentCell, -1, 0)) currentCell.moveLeft();
                    break;
                case KeyEvent.VK_6:
                    if (board.moveable(currentCell, 1, 0)) currentCell.moveRight();
                    break;
                case KeyEvent.VK_8:
                    if (board.moveable(currentCell, 0, 1)) board.move_to_end(currentCell);
                    break;
                case KeyEvent.VK_1:
                    rotate(0);
                    break;
                case KeyEvent.VK_5:
                    rotate(0);
                    break;
                case KeyEvent.VK_9:
                    rotate(0);
                    break;
                case KeyEvent.VK_X:
                    rotate(0);
                    break;
                case KeyEvent.VK_Z:
                    rotate(1);
                    break;
                case KeyEvent.VK_3:
                    rotate(1);
                    break;
                case KeyEvent.VK_7:
                    rotate(1);
                    break;
                case KeyEvent.VK_P:
                    timer.stop();
                    pause = true;
                    break;
                case KeyEvent.VK_H:
                    holdCell[numHold] = currentCell;
                    updatePiece();
                    numHold++;
                    draw.renewDraw(board, currentCell, nextCell, holdCell, numHold, score, level);
                    break;
                case KeyEvent.VK_A:
                    updateP = true;
                    break;

            }
        } else {
            switch (k) {
                case KeyEvent.VK_P:
                    timer.restart();
                    pause = false;
                    break;

            }
        }
        draw.repaint();
    }


    public void rotate(int dic) {
        int tmp;

        tmp = board.rotation(currentCell);
        if (dic == 1) {
            currentCell.rotateCell();
        } else {
            currentCell.rotateRCell();
        }
        int x_loca = currentCell.getLoca_x();
        int y_loca = currentCell.getLoca_y();
        int r_margin = currentCell.getRmargin();
        int l_margin = currentCell.getLmargin();
        if (tmp < 0) x_loca-= min(l_margin, 0-tmp);
        if (tmp > 6) x_loca+= min(r_margin, tmp-6);
        currentCell.renewPiece(x_loca, y_loca);
    }


    public void timerStart() {
        if (board.moveable(currentCell, 0, 1)) {
            currentCell.moveDown();
        } else {
            update();
        }
        draw.repaint();
    }

    public void updatePiece() {
        currentCell = nextCell[0];
        currentCell.renewPiece(2, 0);
        for(int i = 0; i < 3; i++) {
            nextCell[i] = nextCell[i+1];
        }
        if (record == seq.length - 1) record = 0;
        nextCell[3] = new Piece(0,0, seq[record]);
        record++;
        seq = Arrays.copyOfRange(seq, 5, seq.length-1);
    }

    public void update() {
        board.renewBoard(currentCell);
        int lines = board.lines_to_delete();
        if (updateP) {
            numHold--;
            currentCell = holdCell[numHold];
            currentCell.renewPiece(2, 0);
            updateP = false;
        } else {
            updatePiece();
        }
        score = score + lines * 100;
        if (score > (level*300)) {
            speed = speed * 1.1;
            timer.setDelay((1000*(int)(speed))/24);
            level++;
        }
        if (!board.moveable(currentCell, 0, 1)) {
            timer.stop();
            draw.gameover();
        } else {
            draw.renewDraw(board, currentCell, nextCell, holdCell, numHold, score, level);
        }
    }



    public static void main(String[] args) {

        try {
            ProgramArgs a = ProgramArgs.parseArgs(args);
            TetrisMain tmain = new TetrisMain(a.getFPS(), a.getSpeed(), a.getSequence());
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }

    }

}


