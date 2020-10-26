package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class MapTest {
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(80,30);

        TETile[][] randomTiles = new TETile[80][30];
        MapGenerator a = new MapGenerator(randomTiles,14);
        ter.renderFrame(randomTiles);

    }




}
