package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class MapGenerator {
    public static long seed;
    public static int lengthone;
    public static int lengthtwo;
    public static TETile[][] world;
    public static void MapGenerator(TETile[][] c,long a) {
        world = c;
        seed = a;
        lengthone = world.length;
        lengthtwo = world[0].length;
        for (int x=0;x<lengthone;x++){
            for(int y=0;y<lengthtwo;y++){
                world[x][y] = Tileset.NOTHING;
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

