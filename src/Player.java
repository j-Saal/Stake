public class Player {

    private int balance;
    private int bet;

    public Player(int balance) {
        this.balance = balance;
    }

    public void updateBalance(int number) {
        this.balance += number;
    }
}
