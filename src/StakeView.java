import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StakeView extends JFrame implements KeyListener, MouseListener, MouseMotionListener {
    // Instance variables
    private final int WINDOW_WIDTH = 1000;
    private final int WINDOW_HEIGHT = 800;
    private final JTextField minesField;
    private final JTextField betField;
    private boolean doneTyping;
    private Stake stake;
    private int GAME_STATE;
    private Image minesLogo, stakeBackground, minesSquares, diamond, bomb, play;

    // Constructor
    public StakeView(Stake stake) {
        minesLogo = new ImageIcon("Resources/StakeMinesLogo.jpeg").getImage();
        stakeBackground = new ImageIcon("Resources/StakeBackground.png").getImage();
        minesSquares = new ImageIcon("Resources/MinesSquares.png").getImage();
        diamond = new ImageIcon("Resources/diamond.png").getImage();
        bomb = new ImageIcon("Resources/bomb.png").getImage();
        play = new ImageIcon("Resources/play1.png").getImage();
        doneTyping = false;
        GAME_STATE = 1;
        this.stake = stake;
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Stake");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocusInWindow();
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);

        minesField = new JTextField();
        minesField.setBounds(10, 50, 140, 40);
        minesField.setVisible(false);
        add(minesField);

        betField = new JTextField();
        betField.setBounds(10, 110, 140, 40);
        betField.setVisible(false);
        add(betField);
    }

    // If at home screen and click mines logo, starts game
    // If game is playing, gets bets and gets which boxes clicked on
    @Override
    public void mouseClicked(MouseEvent e) {
        requestFocusInWindow();
        int x = e.getX();
        int y = e.getY();
        // If at menu screen check which game is selected
        if (GAME_STATE == 1) {
            if (x >= 100 && x <= 300 && y >= 300 && y <= 600) {
                GAME_STATE = 2;
            }
        }
        // If game is at mines screen check which mine is selected
        else if (GAME_STATE == 2) {
            if (x >= 10 && x <= 150 && y >= 400 && y<= 430) {
                int mines = Integer.parseInt(minesField.getText());
                double bet = Double.parseDouble(betField.getText());
                stake.getMines().updateMines(mines);
                stake.getPlayer().setBet(bet);
                stake.getPlayer().updateBalance(-1 * bet);
                repaint();
                doneTyping = true;
            }
            if (getIndexeX(x) == -1 || getIndexeY(y) == -1) {
                return;
            }
            if (stake.getMines().selectMine(getIndexeY(y), getIndexeX(x))) {
                if (!stake.getMines().getClicked(getIndexeY(y), getIndexeX(x))) {
                    stake.getMines().mineSafe();
                }
                stake.getMines().setClicked(getIndexeY(y), getIndexeX(x));
                stake.play();
            }
            else {
                stake.getMines().setLost(true);
                stake.getMines().setClicked(getIndexeY(y), getIndexeX(x));
                double multiplier = stake.getMines().getMultiplier();
                stake.getPlayer().updateBalance(stake.getPlayer().getBet() * multiplier);
                GAME_STATE = 3;
            }
            if (GAME_STATE != 3) {
                stake.play();
            }
        }
        repaint();
    }

    // Returns x value of which mine is clicked, or -1
    public int getIndexeX(int x) {
        int gridStartX = 164;
        int rectWidth = 125;
        int xOffset = 13;

        if (x < gridStartX) {
            return -1;
        }

        int col = (x - gridStartX) / (rectWidth + xOffset);

        if (col >= 0 && col < 5) {
            return col;
        }
        return -1;
    }

    // Returns y value of which mine is clicked or -1
    public int getIndexeY(int y) {
        int gridStartY = 19;
        int rectHeight = 140;
        int yOffset = 10;

        int row = (y - gridStartY) / (rectHeight + yOffset);

        if (row >= 0 && row < 5) {
            return row;
        }
        return -1;
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    // If c is clicked - cashes out
    // If r is clicked - restarts round
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_C && GAME_STATE == 2) {
            stake.getMines().setCashOut(true);
            stake.play();
            GAME_STATE = 3;
        }
        if (e.getKeyCode() == KeyEvent.VK_R && GAME_STATE == 3) {
            resetGame();
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    // Resets game after lost or cashout is clicked
    public void resetGame() {
        doneTyping = false;
        GAME_STATE = 2;
        stake.getMines().updateMines(stake.getMines().getNumMines());
        minesField.setText("");
        betField.setText("");
        stake.getMines().setLost(false);
        stake.getMines().setCashOut(false);
        stake.getMines().resetNumClicked();
        repaint();
    }

    // Paints title screen if game hasn't started
    // Once in game paints the current state of the game
    public void paint(Graphics g) {
        if (GAME_STATE == 1) {
            g.setColor(Color.WHITE);
            g.drawRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
            g.drawImage(stakeBackground, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
            g.drawImage(minesLogo, 100, 300, 200, 300, this);
        }
        if (GAME_STATE == 2 || GAME_STATE == 3) {
            minesField.setVisible(true);
            betField.setVisible(true);
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
            g.drawImage(minesSquares, 0, 28, WINDOW_WIDTH, WINDOW_HEIGHT - 45, this);
            g.setColor(Color.WHITE);
            g.drawString("Current Balance: ", 10, 260);
            g.drawString("Current Bet: ", 10, 310);
            g.drawString("Press 'c' to cashOut", 850, 260);
            g.drawString("Press 'r' to restart", 850, 310);
            if (!doneTyping) {
                g.drawString("Mines: ", 10, 65);
                g.drawString("Bet ^^^ ", 10, 200);
            }
            else {
                g.drawString("Current Multiplier: " + stake.getMines().getMultiplier(), 10, 200);
            }
            g.drawString(String.valueOf(stake.getPlayer().getBalance()), 10, 280);
            g.drawString(String.valueOf(stake.getPlayer().getBet()), 10, 330);
            g.drawImage(play, 10, 400, 140, 30, this);
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (stake.getMines().getClicked(i, j)) {
                        if (stake.getMines().selectMine(i, j)) {
                            g.drawImage(diamond, 154 + (125 * j) + (13 * j), 19 + (140 * i) + (10 * i), 143, 150, this);
                        }
                        else {
                            g.drawImage(bomb, 154 + (125 * j) + (13 * j), 19 + (140 * i) + (10 * i), 143, 150, this);
                        }
                    }
                }
            }
        }
    }
}
