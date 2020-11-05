package hw4.puzzle;

public class SearchNode implements Comparable<SearchNode>{
        public WorldState word;
        public int moves;
        public SearchNode prev;
        public int priority;
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


