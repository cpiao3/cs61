package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;
import org.junit.Test;

import java.io.*;
import java.awt.*;

public class Game {
    TERenderer ter = new TERenderer();
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static boolean playwithstring=false;

    public void playWithKeyboard() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);
        StdDraw.enableDoubleBuffering();
        StdDraw.text(40, 17, "New Game(N)");
        StdDraw.text(40, 15, "Load Game(L)");
        StdDraw.text(40, 13, "Quit(Q)");
        StdDraw.show();
        String input = "";
        while ((!input.contains("Q") && !input.contains("N") && !input.contains("L")) && (!input.contains("q") && !input.contains("l") && !input.contains("n"))) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            input += key;
        }
        if (input.contains("N") || input.contains("n")) {
            StdDraw.clear(Color.black);
            String seed = "";
            while (!(seed.contains("S")||seed.contains("s"))) {
                StdDraw.text(40, 17, "Enter Seed:");
                StdDraw.show();
                if (!StdDraw.hasNextKeyTyped()) {
                    continue;
                }
                char key = StdDraw.nextKeyTyped();
                seed += key;
                StdDraw.clear(Color.black);
                StdDraw.text(40, 15, seed);
            }
            Newgame(Seedmaker(seed));
        }
        if (input.contains("q") || input.contains("Q")){
            Quit();
        }
        if (input.contains("l") || input.contains("L")){
            GameControl player = Loadgame();
            font = new Font("Monaco", Font.BOLD, 14);
            StdDraw.setFont(font);
            ter.renderFrame(MapGenerator.world);;
            player.control();
        }
    }


    private void Newgame(long seed) {
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        MapGenerator.MapGenerator(world, seed);
        int[] a = Room.player();
        ter.renderFrame(world);;
        GameControl b = new GameControl(a[0],a[1],a[2],a[3]);
        b.control();
    }

    private GameControl Loadgame() {
        GameControl player = null;
        try{
            FileInputStream file = new FileInputStream("saved.txt");
            ObjectInputStream in = new ObjectInputStream(file);
            MapGenerator.world = (TETile[][]) in.readObject();
            in.close();
            file.close();
            FileInputStream file2 = new FileInputStream("player.txt");
            ObjectInputStream in2 = new ObjectInputStream(file2);
            player = (GameControl) in2.readObject();
            in2.close();
            file2.close();
        } catch (IOException i){
            i.printStackTrace();
        } catch (ClassNotFoundException o){
            o.printStackTrace();
        }
        return player;
    }

    public void Quit() {
        java.lang.System.exit(0) ;
    }

    public void Savegame(TETile[][] world,GameControl player){
        try {
            FileOutputStream file = new FileOutputStream("saved.txt");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(world);
            out.close();
            file.close();
            FileOutputStream file2 = new FileOutputStream("player.txt");
            ObjectOutputStream out2 = new ObjectOutputStream(file2);
            out2.writeObject(player);
            out2.close();
            file.close();
            System.out.println("Saved");
        } catch (IOException i){
            i.printStackTrace();
        }
    }


    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        playwithstring = true;
        String steps = stringprocess(input);
        TETile[][] world = null;
        GameControl player = null;
        long seed = Seedmaker(input);
        if (input.charAt(0)=='L'||input.charAt(0)=='l'){
            player = Loadgame();
        } else {
            world = new TETile[WIDTH][HEIGHT];
            MapGenerator.MapGenerator(world, seed);
            int[] a = Room.player();
            player = new GameControl(a[0],a[1],a[2],a[3]);
        }
        for (int i = 0;i<steps.length();i++){
            switch (steps.charAt(i)){
                    case 'w':
                        player.moveup();
                        break;
                    case 's':
                        player.movedown();
                        break;
                    case 'a':
                        player.moveleft();
                        break;
                    case 'd':
                        player.moveright();
                        break;
            }
        }
        if (input.contains(":q")){
            Savegame(MapGenerator.world,player);
        }
        return MapGenerator.world;
    }

    private long Seedmaker(String a){
        String string = "";
        long seed = 0;
        for (int i = 0;i<a.length();i++){
            if ((int)a.charAt(i)<=57&&(int)a.charAt(i)>=48){
                string += a.charAt(i);
            }
        }
        if (!string.isEmpty()){
            seed = Long.parseLong(string);
        }
        return seed;
    }

    private String stringprocess(String a){
        a = a.toLowerCase();
        String result = "";
        for(int i = 0;i<a.length();i++){
            switch (a.charAt(i)){
                    case 'w':
                        result += a.charAt(i);
                        break;
                    case 's':
                        result += a.charAt(i);
                        break;
                    case 'a':
                        result += a.charAt(i);
                        break;
                    case 'd':
                        result += a.charAt(i);
                        break;
                }
        }
        return result;
    }

}
