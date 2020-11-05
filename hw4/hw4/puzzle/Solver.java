package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import java.util.ServiceConfigurationError;
import java.util.LinkedList;


public class Solver {

    private MinPQ<SearchNode> P = new MinPQ<>();
    private SearchNode prevs;
    private WorldState firstWorldState;
    private int finalmove;
    private LinkedList<WorldState> solution =  new LinkedList();
    public Solver(WorldState initial){
        if (P.isEmpty()) {
            firstWorldState = initial;
            SearchNode first = new SearchNode(initial, 0, null);
            P.insert(first);
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
        while (!P.min().word.isGoal()) {
            prevs = P.delMin();
            for (WorldState x : prevs.word.neighbors()) {
                if (prevs.prev != null) {
                    if (!prevs.prev.word.equals(x)) {
                        SearchNode neighbor = new SearchNode(x, prevs.moves + 1, prevs);
                        P.insert(neighbor);
                    }
                } else {
                    SearchNode neighbor = new SearchNode(x, prevs.moves + 1, prevs);
                    P.insert(neighbor);
                }
            }
        }
            prevs = P.delMin();
            finalmove = prevs.moves;
    }

    private LinkedList<WorldState> createsolution(){
        while (prevs != null) {
            solution.addFirst(prevs.word);
            prevs = prevs.prev;
        }
        return solution;
    }
    private class SearchNode implements Comparable<SearchNode>{
        private WorldState word;
        private int moves;
        private SearchNode prev;
        private int priority;
        private SearchNode(WorldState word, int moves, SearchNode prev){
            this.word = word;
            this.prev = prev;
            this.moves = moves;
            priority = this.moves + word.estimatedDistanceToGoal();
        }
        @Override
        public int compareTo(SearchNode o2){
            return this.priority-o2.priority;
        }
    }



}
