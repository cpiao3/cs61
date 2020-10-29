package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    public boolean[][] perc;
    WeightedQuickUnionUF set;
    public int opensites = 0;
    public int length;
    public int top;
    public boolean[] bottom;
    public boolean perco = false;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        perc = new boolean[N][N];
        set = new WeightedQuickUnionUF(N * N + 2);
        bottom = new boolean[N * N];
        top = N * N + 1;
        length = N;
    }

    public void open(int row, int col) {
        if (perc[col][row] == false) {
            perc[col][row] = true;
            bottom(row, col);
            opensites++;
            if (row == 0) {
                set.union(index(row, col), top);

            }
            if (bottom[set.find(index(row, col))] && set.connected(index(row, col), top)) {
                perco = true;
            }
        }
    }

    private void bottom(int row, int col) {
        boolean a = false;
        if (row == length - 1) {
            a = true;
        }
        if (row <= length - 1 && row > 0 && isOpen(row - 1, col)) {
            if (bottom[set.find(index(row - 1, col))] || bottom[set.find(index(row, col))]) {
                a = true;
            }
        }
        if (row >= 0 && row < length - 1 && isOpen(row + 1, col)) {
            if (bottom[set.find(index(row + 1, col))] || bottom[set.find(index(row, col))]) {
                a = true;
            }
        }
        if (col >= 0 && col < length - 1 && isOpen(row, col + 1)) {
            if (bottom[set.find(index(row, col + 1))] || bottom[set.find(index(row, col))]) {
                a = true;
            }
        }
        if (col <= length - 1 && col > 0 && isOpen(row, col - 1)) {
            if (bottom[set.find(index(row, col - 1))] || bottom[set.find(index(row, col))]) {
                a = true;
            }
        }
        connect(row, col);
        bottom[set.find(index(row, col))] = a;
    }

    public boolean isOpen(int row, int col) {
        if (perc[col][row] == true) {
            return true;
        }
        return false;
    }

    public boolean isFull(int row, int col) {
        if (set.connected(top, index(row, col))) {
            return true;
        }
        return false;
    }

    public int numberOfOpenSites() {
        return opensites;
    }

    public boolean percolates() {
        return perco;
    }

    private int index(int y, int x) {
        return ((y * length) + x);
    }

    private void connect(int row, int col) {
        if (row != perc.length - 1) {
            if (isOpen(row + 1, col)) {
                set.union(index(row + 1, col), index(row, col));
            }
        }
        if (row != 0) {
            if (isOpen(row - 1, col)) {
                set.union(index(row - 1, col), index(row, col));
            }
        }
        if (col != perc.length - 1) {
            if (isOpen(row, col + 1)) {
                set.union(index(row, col + 1), index(row, col));
            }
        }
        if (col != 0) {
            if (isOpen(row, col - 1)) {
                set.union(index(row, col - 1), index(row, col));
            }
        }
    }

    public static void main(String arg[]) {
    }
}