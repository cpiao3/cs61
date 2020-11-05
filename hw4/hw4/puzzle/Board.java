package hw4.puzzle;
import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState{
    private int[][] board;
    Board(int[][] tiles){
        board = new int[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length;i++){
            for (int j = 0;j< tiles[0].length;j++){
                board[i][j] = tiles[i][j];
            }
        }
    }
    public int tileAt(int i, int j){
        return board[i][j];
    }

    public int size(){
        return board.length;
    }

    @Override
    public Iterable<WorldState> neighbors() {
        int BLANK = 0;
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }
    public int hamming(){
        int ham = 0;
        for (int i = 0;i<size();i++){
            for (int j = 1;j<=size();j++){
                if (i*size()+j != board[i][j-1]&& board[i][j-1]!=0){
                    ham++;
                }
            }
        }
        return ham;
    }
    public int manhattan(){
        int man = 0;
        for (int i = 0 ; i<size();i++){
            for (int j = 0;j<size();j++){
                if (i*size()+j+1 != board[i][j]&&board[i][j]!=0){
                    man += positiondif(i,j);
                }
            }
        }
        return man;
    }

    private int positiondif(int i ,int j){
        int y = board[i][j]/size();
        int x = board[i][j]%size();
        int xdif=0;
        int ydif=0;
        if (x == 0){
            x = size()-1;
            y = y-1;
        } else{
            x = x-1;
        }

        if (x > j){
            xdif = x - j;
        } else{
            xdif = j - x;
        }

        if (y > i){
            ydif = y - i;
        } else{
            ydif = i - y;
        }
        return (xdif+ydif);

    }

    @Override
    public int estimatedDistanceToGoal(){
        int a = manhattan();
        return a;
    }

    @Override
    public boolean equals(Object y){
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }
        Board a = (Board) y;
        if (this.size() !=a.size()){
            return false;
        }
        for (int i = 0; i<size();i++){
            for(int j = 0; j<size();j++){
                if (this.tileAt(i,j) != a.tileAt(i,j)){
                    return false;
                }
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}

