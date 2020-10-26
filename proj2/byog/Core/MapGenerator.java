package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class MapGenerator extends Game{
    public static long seed;
    public static TETile[][] world;
    public static void MapGenerator(TETile[][] c,long a) {
        seed = a;
        world = c;
        for (int x=0;x<c.length;x++){
            for(int y=0;y<c[0].length;y++){
                c[x][y] = Tileset.NOTHING;
            }
        }
        Hallways_and_Rooms();
    }

    public static void Hallways_and_Rooms(){
        Random r = new Random(seed);
        int c =r.nextInt(); ;
        while ( c<8||c>20)
            { c = r.nextInt();}
        Room.roommaker(c);
    }
}

