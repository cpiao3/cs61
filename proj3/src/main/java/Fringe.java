public class Fringe implements Comparable<Fringe> {
    private long id;
    private double priority = Double.POSITIVE_INFINITY;
    Fringe(long id){
        this.id = id;
    }
    Fringe(long id,double p){
        this.id = id;
        this.priority = p;
    }
    public void set_priority(double priority){
        this.priority = priority;
    }
    public double get_priority(){
        return priority;
    }
    public long id(){
        return id;
    }
    @Override
    public int compareTo(Fringe o) {
        if (this.priority - o.priority > 0){
            return 1;
        } if (this.priority - o.priority < 0){
            return -1;
        }
        return 0;
    }
}
