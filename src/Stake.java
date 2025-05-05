// Mines by Jared Saal
public class Stake {
    // Instance Variables
    private StakeView window;
    private Mines mines;
    private Player player;

    // Constructor
    public Stake() {
        window = new StakeView(this);
        mines = new Mines(0);
        player = new Player(1000);
    }

    // Checks if game over and updates corresponding player balance
    public void play() {
        if (mines.getOver()) {
            player.updateBalance(player.getBet() * mines.getMultiplier());
        }
    }

    // Returns instance of player
    public Player getPlayer() {
        return this.player;
    }

    // Returns instance of mines
    public Mines getMines() {
        return this.mines;
    }

    // Main method
    public static void main(String[] args) {
        Stake s = new Stake();
        s.play();
    }
}
