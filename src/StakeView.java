import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StakeView extends JFrame implements KeyListener, MouseListener, MouseMotionListener {
    private final int WINDOW_WIDTH = 1000;
    private final int WINDOW_HEIGHT = 800;
    private final JTextField minesField;
    private final JTextField betField;
    private final JButton cashOut;
    private Stake stake;
    private int GAME_STATE;
    private Image minesLogo, stakeBackground, minesSquares, diamond, bomb;

    public StakeView(Stake stake) {
        minesLogo = new ImageIcon("Resources/StakeMinesLogo.jpeg").getImage();
        stakeBackground = new ImageIcon("Resources/StakeBackground.png").getImage();
        minesSquares = new ImageIcon("Resources/MinesSquares.png").getImage();
        diamond = new ImageIcon("Resources/diamond.png").getImage();
        bomb = new ImageIcon("Resources/bomb.png").getImage();
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

        cashOut = new JButton("Cashout");
        cashOut.setBounds(10, 170, 140, 40);
        cashOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stake.getMines().setCashOut(true);
            }
        });
        cashOut.setVisible(false);
        add(cashOut);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        repaint();
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
        else if (GAME_STATE == 2) {
            if (getIndexeX(x) == -1 || getIndexeY(y) == -1) {
                return;
            }
            if (stake.getMines().selectMine(getIndexeY(y), getIndexeX(x))) {
                stake.getMines().setClicked(getIndexeY(y), getIndexeX(x));
                stake.getMines().mineSafe();
                stake.play();
            }
            else {
                stake.getMines().setLost();
                stake.getMines().setClicked(getIndexeY(y), getIndexeX(x));
                stake.play();
            }
        }
        requestFocusInWindow();
    }

    public int getIndexeX(int x) {
        int gridStartX = 164;
        int rectWidth = 125;
        int xOffset = 13;

        int col = (x - gridStartX) / (rectWidth + xOffset);

        if (col >= 0 && col < 5) {
            return col;
        }
        return -1;
    }

    public int getIndexeY(int y) {
        int gridStartY = 39;
        int rectHeight = 150;
        int yOffset = 10;

        int row = (y - gridStartY) / (rectHeight + yOffset);

        if (row >= 0 && row < 5) {
            return row;
        }
        return -1;
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
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            int mines = Integer.parseInt(minesField.getText());
            double bet = Double.parseDouble(betField.getText());
            stake.getMines().updateMines(mines);
            stake.getPlayer().setBet(bet);
            minesField.setVisible(false);
            betField.setVisible(false);
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void paint(Graphics g) {
        super.paint(g);
        if (GAME_STATE == 1) {
            g.setColor(Color.WHITE);
            g.drawRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
            g.drawImage(stakeBackground, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
            g.drawImage(minesLogo, 100, 300, 200, 300, this);
        }
        if (GAME_STATE == 2) {
            cashOut.setVisible(true);
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
            g.drawImage(minesSquares, 0, 28, WINDOW_WIDTH, WINDOW_HEIGHT - 45, this);
            minesField.setVisible(true);
            betField.setVisible(true);
            g.setColor(Color.WHITE);
            g.drawString("Current Balance: ", 10, 260);
            g.drawString("Current Bet: ", 10, 310);
            g.drawString(String.valueOf(stake.getPlayer().getBalance()), 10, 280);
            g.drawString(String.valueOf(stake.getPlayer().getBet()), 10, 330);
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (stake.getMines().getClicked(i, j)) {
                        if (stake.getMines().selectMine(i, j)) {
                            g.drawImage(diamond, 154 + (125 * j) + (13 * j), 19 + (140 * i) + (10 * i), 143, 150, this);
                        }
                        else {
                            g.drawImage(bomb, 154 + (125 * j) + (13 * j), 19 + (125 * i) + (10 * i), 143, 150, this);
                        }
                    }
                }
            }
        }
    }
}
