package hw4.puzzle;
import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState{
    private int[] board;
    private int N;
     Board(int[][] tiles){
         N= tiles.length;
        board = new int[N*N];
        int b = 0;
        for (int i = 0; i < N;i++){
            for (int j = 0;j< N;j++){
                board[b] = tiles[i][j];
                b++;
            }
        }
    }
    public int tileAt(int i, int j){
        return board[i*size()+j];
    }
    public int size(){
        return N;
    }
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
        int a = 0;
        for (int i = 0;i<size()*size();i++){
                if (i+1 != board[a]&& board[a]!=0){
                    ham++;
                }
                a++;

        }
        return ham;
    }
    public int manhattan(){
        int man = 0;
        for (int i = 0 ; i<size()*size();i++){
                if (i+1 != board[i] && board[i]!=0){
                    man += positiondif(i);
            }
        }
        return man;
    }

    private int positiondif(int i){
        int y = board[i]/size();
        int x = board[i]%size();
        int xorg = i / size();
        int yorg = i / size();
        int xdif=0;
        int ydif=0;
        if (x == 0){
            x = size()-1;
            y = y-1;
        } else{
            x = x-1;
        }
        xdif = Math.abs(x - xorg);
        ydif = Math.abs(y - yorg);

        return (xdif+ydif);

    }


    public int estimatedDistanceToGoal(){
        return manhattan();
    }

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
