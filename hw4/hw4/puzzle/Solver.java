package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import java.util.ServiceConfigurationError;
import java.util.LinkedList;


public class Solver {

    private MinPQ<SearchNode> PQ = new MinPQ<>();
    private SearchNode prevs;
    private WorldState firstWorldState;
    private int finalmove;
    private LinkedList<WorldState> solution =  new LinkedList();
    public Solver(WorldState initial){
        if (PQ.isEmpty()) {
            firstWorldState = initial;
            SearchNode first = new SearchNode(initial, 0, null);
            PQ.insert(first);
            prevs = first;
        }
        Explore();
    }

    public int moves(){
        return finalmove;
    }

    public Iterable<WorldState> solution(){
        return createsolution();
    }

    private void Explore(){
        while (!PQ.min().word.isGoal()) {
            prevs = PQ.delMin();
            for (WorldState x : prevs.word.neighbors()) {
                if (prevs.prev != null) {
                    if (!prevs.prev.word.equals(x)) {
                        SearchNode neighbor = new SearchNode(x, prevs.moves + 1, prevs);
                        PQ.insert(neighbor);
                    }
                } else {
                    SearchNode neighbor = new SearchNode(x, prevs.moves + 1, prevs);
                    PQ.insert(neighbor);
                }
            }
        }
            prevs = PQ.delMin();
            finalmove = prevs.moves;
    }

    private  LinkedList<WorldState> createsolution(){
        while (prevs != null) {
            solution.addFirst(prevs.word);
            prevs = prevs.prev;
        }
        return solution;
    }




}
