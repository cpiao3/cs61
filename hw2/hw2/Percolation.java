package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.io.IOException;

public class Percolation {
    public boolean[][] perc;
    WeightedQuickUnionUF set;
    WeightedQuickUnionUF tract;
    public int top;
    public int bottom;
    public int opensites=0;
    public int length;


    public Percolation(int N) {
        if(N<=0){
            throw new IllegalArgumentException();
        }
            perc = new boolean[N][N];
            set = new WeightedQuickUnionUF(N*N+2);
            tract = new WeightedQuickUnionUF(N*N+2);
            top = N*N+1;
            bottom = N*N;
            length = N;
    }
    public void open(int row, int col) {
        if (perc[col][row]==false) {
            if (row == 0) {
                perc[col][row] = true;
                set.union(top, index(row, col));
                tract.union(top,index(row, col));
                connect(row, col);
            } else if (row == perc.length - 1) {
                perc[col][row] = true;
                connect(row, col);
                set.union(bottom, index(row, col));
            } else {
                perc[col][row] = true;
                connect(row, col);
            }
            opensites++;
        }
    }
    public boolean isOpen(int row, int col){
        if (perc[col][row]==true){
            return true;
        }
        return false;
    }
    public boolean isFull(int row, int col){
        if (tract.connected(top,index(row,col))){
            return true;
        }
        return false;
    }
    public int numberOfOpenSites()  {
        return opensites;
    }
    public boolean percolates(){
        return (set.connected(top,bottom));
    }

    private int index(int y,int x){
        return (y*length)+x;
    }

    private void connect(int row,int col){
        if (row != perc.length-1) {
            if (isOpen(row + 1, col)) {
                set.union(index(row + 1, col), index(row, col));
                tract.union(index(row + 1, col), index(row, col));
            }
        }
        if (row != 0) {
            if (isOpen(row - 1, col)) {
                set.union(index(row - 1, col), index(row, col));
                tract.union(index(row - 1, col), index(row, col));
            }
        }
        if (col != perc.length-1) {
            if (isOpen(row, col + 1)) {
                set.union(index(row, col + 1), index(row, col));
                tract.union(index(row, col + 1), index(row, col));

            }
        }
        if (col != 0) {
            if (isOpen(row, col - 1)) {
                set.union(index(row , col-1), index(row, col));
                tract.union(index(row , col-1), index(row, col));
            }
        }
    }

    public static void main(String arg[]){
        Percolation perc = new Percolation(2);
        perc.open(1,0);
        perc.open(0,0);
        System.out.println(perc.percolates());
    }





}
