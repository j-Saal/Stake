import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StakeView extends JFrame implements KeyListener, MouseListener, MouseMotionListener {
    private final int WINDOW_WIDTH = 1000;
    private final int WINDOW_HEIGHT = 800;
    private Stake stake;
    private int GAME_STATE;
    private Image minesLogo, stakeBackground;

    public StakeView (Stake stake) {
        minesLogo = new ImageIcon("Resources/StakeMinesLogo.jpeg").getImage();
        stakeBackground = new ImageIcon("Resources/StakeBackground.png").getImage();
        GAME_STATE = 1;
        this.stake = stake;
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Stake");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        // If at menu screen check which game is selected
        if (GAME_STATE == 1) {
            if (x >= 100 && x <= 300 && y >= 300 && y <= 600) {
                GAME_STATE = 2;
                repaint();
            }
        }
        // If game is at mines screen check which mine is selected
        if (GAME_STATE == 2) {

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

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void paint(Graphics g) {
        if (GAME_STATE == 1) {
            g.setColor(Color.WHITE);
            g.drawRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
            g.drawImage(stakeBackground, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
            g.drawImage(minesLogo, 100, 300, 200, 300, this);
        }
        if (GAME_STATE == 2) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        }
    }
}
