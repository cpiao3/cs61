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
        Game game = new Game();
        TETile[][] world = game.playWithInputString("n5197880843569031643s");
        System.out.println(TETile.toString(world));
        TETile[][] world2 = game.playWithInputString("n5197880843569031643s");
        System.out.println(TETile.toString(world2));


    }




}
