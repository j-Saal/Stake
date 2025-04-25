public class Player {

    private int balance;
    private double bet;

    public Player(int balance) {
        this.balance = balance;
    }

    public double getBet() {
        return bet;
    }

    public double getBalance() {
        return this.balance;
    }

    public void updateBalance(double number) {
        this.balance += number;
    }
}
