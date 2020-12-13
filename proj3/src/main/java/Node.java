import java.util.HashSet;
import java.util.Set;

public class Node{
        private long id;
        private double lon;
        private double lat;
        private Set<Long> nearby = new HashSet<>();

        public Node(String id,String lon,String lat){
            this.id = Long.parseLong(id);
            this.lon = Double.parseDouble(lon);
            this.lat = Double.parseDouble(lat);
        }

        public void Connect(String node2){
            nearby.add(Long.parseLong(node2));
        }

        public Iterable Iterable(){
            return nearby;
        }

        public double lon(){
            return lon;
        }
        public double lat(){
            return lat;
        }

}
