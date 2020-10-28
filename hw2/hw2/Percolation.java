package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.io.IOException;

public class Percolation {
    public boolean[][] perc;
    WeightedQuickUnionUF set;
    public int top;
    public int bottom;
    public int opensites=0;
    public int length;

    public Percolation(int N) {
            perc = new boolean[N][N];
            set = new WeightedQuickUnionUF(N*N+2);
            top = N*N+1;
            bottom = N*N;
            length = N;
    }
    public void open(int row, int col) {
        if (perc[col][row]==false) {
            if (col < perc.length && row == 0) {
                perc[col][row] = true;
                set.union(top, index(row, col));
                connect(row, col);
            } else if (col < perc.length && row == perc.length - 1) {
                perc[col][row] = true;
                connect(row, col);
                if (set.connected(top,index(row,col))) {
                    set.union(bottom, index(row, col));
                }
            } else {
                perc[col][row] = true;
                opensites++;
                connect(row, col);
            }
        }
    }
    public boolean isOpen(int row, int col){
        if (perc[col][row]==true){
            return true;
        }
        return false;
    }
    public boolean isFull(int row, int col){
        if (set.connected(top,index(row,col))){
            return true;
        }
        return false;
    }
    public int numberOfOpenSites()  {
        return opensites;
    }
    public boolean percolates(){
        if (set.connected(top,bottom)){
            return true;
        }
        return false;
    }

    private int index(int y,int x){
        return (y*length)+x;
    }

    private void connect(int row,int col){
        if (row != perc.length-1) {
            if (isOpen(row + 1, col)) {
                set.union(index(row + 1, col), index(row, col));
            }
        }
        if (row != 0) {
            if (isOpen(row - 1, col)) {
                set.union(index(row - 1, col), index(row, col));
            }
        }
        if (col != perc.length-1) {
            if (isOpen(row, col + 1)) {
                set.union(index(row, col + 1), index(row, col));
            }
        }
        if (col != 0) {
            if (isOpen(row, col - 1)) {
                set.union(index(row , col-1), index(row, col));
            }
        }
    }






}
