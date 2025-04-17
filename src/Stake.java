import java.util.Scanner;

public class Stake {
    private StakeView window;
    private Mines mines;

    public Stake() {
        window = new StakeView(this);
        mines = new Mines(0);
    }
    public static void main(String[] args) {
        Stake s = new Stake();
    }
}
