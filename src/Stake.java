import java.util.Scanner;

public class Stake {
    public static void main(String[] args) {
        Scanner s1 = new Scanner(System.in);
        System.out.println("Number of Mines:");
        Mines g1 = new Mines(s1.nextInt());
        s1.nextLine();
        int int1 = s1.nextInt();
        s1.nextLine();
        int int2 = s1.nextInt();
        int numwins = 0;
        while (g1.selectMine(int1, int2)) {
            numwins++;
            s1.nextLine();
            int1 = s1.nextInt();
            s1.nextLine();
            int2 = s1.nextInt();
        }
        System.out.println(g1.getMultiplier(numwins));
    }
}
