public class Player {
    // Instance Variables
    private int balance;
    private double bet;

    // Constructor
    public Player(int balance) {
        this.balance = balance;
    }

    // Returns current bet
    public double getBet() {
        return bet;
    }

    // Sets current bet
    public void setBet(double bet) {
        this.bet = bet;
    }

    // Returns current balance
    public double getBalance() {
        return this.balance;
    }

    // Updates current balance
    public void updateBalance(double number) {
        this.balance += number;
    }
}
