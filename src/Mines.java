import java.util.ArrayList;

public class Mines {
    private boolean[][] grid;
    private double[][] multipliers;
    private int numMines;
    private boolean cashOut;
    private int minesClicked;
    private boolean lost;
    private boolean[][] clicked;

    public Mines(int numMines) {
        this.numMines = numMines;
        lost = false;
        minesClicked = 0;
        grid = new boolean[5][5];
        clicked = new boolean[5][5];
        cashOut = false;
        updateMines(numMines);
    }

    public boolean selectMine(int row, int col) {
        return grid[row][col];
    }

    public void setClicked(int row, int col) {
        clicked[row][col] = true;
    }

    public boolean getClicked(int row, int col) {
        return this.clicked[row][col];
    }

    public void resetNumClicked() {
        this.minesClicked = 0;
    }

    public void updateMines(int numMines) {
        this.numMines = numMines;
        ArrayList<Integer> indexes = new ArrayList<>();
        int num = (int) (Math.random() * 25);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                clicked[i][j] = false;
            }
        }
        for (int i = 0; i < numMines; i++) {
            if (!indexes.contains(num)) {
                indexes.add(num);
            }
            else {
                i--;
            }
            num = (int) (Math.random() * 25);
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grid[i][j] = true;
            }
        }
        for (int i: indexes) {
            grid[i / 5][i % 5] = false;
        }
    }

    public void setLost(boolean lost) {
        this.lost = lost;
    }

    public void mineSafe() {
        minesClicked++;
    }

    public void setCashOut(boolean cashOut) {
        this.cashOut = cashOut;
    }

    public int getNumMines() {
        return this.numMines;
    }

    public boolean getOver() {
        return cashOut || lost;
    }

    public double getMultiplier() {
        multipliers = new double[][]{
                {0, 1.01, 1.08, 1.12, 1.18, 1.24, 1.3, 1.37, 1.46, 1.55, 1.65, 1.77, 1.9, 2.06, 2.25, 2.47, 2.75, 3.09, 3.54, 4.12, 4.95, 6.19, 8.25, 12.37, 24.75},
                {0, 1.08, 1.17, 1.29, 1.41, 1.56, 1.71, 1.94, 2.18, 2.47, 2.83, 3.26, 3.81, 4.45, 5.4, 6.6, 8.25, 10.61, 14.14, 19.8, 29.7, 49.5, 99, 297},
                {0, 1.12, 1.29, 1.48, 1.71, 2.09, 2.35, 2.79, 3.35, 4.07, 5, 6.26, 7.95, 10.35, 13.8, 18.97, 27.11, 40.66, 65.06, 113.85, 227.7, 569.3, 2277},
                {0, 1.18, 1.41, 1.71, 2.09, 2.58, 3.23, 4.09, 5.26, 6.88, 9.17, 12.51, 17.52, 25.3, 37.95, 59.64, 99.39, 178.91, 357.81, 834.9, 2504, 12523},
                {0, 1.24, 1.56, 2, 2.58, 3.39, 4.52, 6.14, 8.5, 12.04, 17.52, 26.77, 40.87, 66.41, 113.85, 208.72, 417.45, 939.26, 2504, 8768, 52598},
                {0, 1.3, 1.74, 2.35, 3.23, 4.52, 6.46, 9.44, 14.17, 21.89, 35.03, 58.38, 102.17, 189.75, 379.5, 834.9, 2087, 6261, 25047, 175329},
                {0, 1.37, 1.94, 2.79, 4.09, 6.14, 9.44, 14.95, 24.47, 41.6, 73.95, 138.66, 277.33, 600.87, 1442, 3965, 13219, 59486, 250047, 59486},
                {0, 1.46, 2.18, 3.35, 5.26, 8.5, 14.17, 24.47, 44.05, 83.2, 166.4, 356.56, 831.98, 2163, 6489, 23794, 118973, 1070759, 475893},
                {0, 1.55, 2.47, 4.07, 6.88, 12.04, 21.89, 41.6, 83.2, 176.8, 404.1, 1010, 2828, 9193, 36773, 202254, 2022545},
                {0, 1.65, 2.83, 5, 9.17, 17.52, 35.03, 73.95, 166.4, 404.1, 1077, 3232, 11314, 49031, 294188, 3236072},
                {0, 1.77, 3.26, 6.26, 12.51, 26.27, 58.38, 138.66, 356.56, 1010, 3232, 12123, 56574, 367773, 4412826},
                {0, 1.9, 3.81, 7.95, 17.52, 40.87, 102.17, 277.33, 831.98, 2828, 11314, 56574, 396022, 5148297},
                {0, 2.06, 4.5, 10.35, 25.3, 66.41, 189.75, 600.87, 2163, 9193, 49031, 367773, 396022},
                {0, 2.25, 5.4, 13.8, 37.95, 113.9, 379.5, 1442, 6489, 36773, 294188, 4412826},
                {0, 2.47, 6.6, 18.97, 59.64, 208.7, 834.9, 3965, 23794, 202254, 3236072},
                {0, 2.75, 8.25, 27.11, 99.39, 417.5, 2087, 13219, 118973, 2022545},
                {0, 3.09, 10.61, 40.66, 178.9, 939.3, 6261, 59486, 1070759},
                {0, 3.54, 14.14, 65.06, 357.8, 2504, 25047, 475893},
                {0, 4.12, 19.8, 113.9, 834.9, 8768, 175329},
                {0, 4.95, 29.7, 227.7, 2504, 52598},
                {0, 6.19, 49.5, 569.3, 12523},
                {0, 8.25, 99, 2277},
                {0, 12.38, 297},
                {0, 24.75}
        };
        if (lost) {
            return 0;
        }
        else {
            return multipliers[numMines - 1][minesClicked];
        }
    }
}
