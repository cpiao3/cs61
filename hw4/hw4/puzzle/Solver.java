package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import java.util.ServiceConfigurationError;
import java.util.LinkedList;


public class Solver {
    public int i = 0;
    private MinPQ<SearchNode> PQ = new MinPQ<>();
    private SearchNode prev;
    private WorldState firstWorldState;
    private int finalmove;
    private LinkedList<WorldState> solution =  new LinkedList();
    public Solver(WorldState initial){
        if (PQ.isEmpty()) {
            firstWorldState = initial;
            SearchNode first = new SearchNode(initial, 0, null);
            PQ.insert(first);
            prev = first;
        }
        Explore();
    }

    public int moves(){
        return finalmove;
    }

    public Iterable<WorldState> solution(){
        return solution;
    }

    private void Explore(){
        prev = PQ.min();
        if (!PQ.delMin().word.isGoal()) {
            Iterable<WorldState> neighbors = prev.word.neighbors();
            for (WorldState x : neighbors) {
                if (prev.prev != null) {
                    if (!x.equals(prev.prev.word)) {
                        SearchNode neighbor = new SearchNode(x, prev.moves + 1, prev);
                        PQ.insert(neighbor);
                    }
                } else {
                    SearchNode neighbor = new SearchNode(x, prev.moves + 1, prev);
                    PQ.insert(neighbor);
                }
            }
            Explore();
        } else {
            finalmove = prev.moves;
            creatsolution();
        }
    }

    private void creatsolution(){
        while (prev != null) {
            solution.addFirst(prev.word);
            prev = prev.prev;
        }
    }

    private class SearchNode implements Comparable<SearchNode>{
        private WorldState word;
        private int moves;
        private SearchNode prev;
        private int priority;
        public SearchNode(WorldState word, int moves, SearchNode prev){
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
