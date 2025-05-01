

public class Stake {
    private StakeView window;
    private Mines mines;
    private Player player;

    public Stake() {
        window = new StakeView(this);
        mines = new Mines(0);
        player = new Player(1000);
    }

    public void play() {
        if (mines.getOver()) {
            player.updateBalance(player.getBet() * mines.getMultiplier());
        }
    }

    public Player getPlayer() {
        return this.player;
    }
    public Mines getMines() {
        return this.mines;
    }
    public static void main(String[] args) {
        Stake s = new Stake();
        s.play();
    }
}
