package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.Serializable;

public class GameControl extends Room implements Serializable{
    public int playerx;
    public int playery;
    public int doorx;
    public int doory;
    public boolean gameover = false;
    public double a = 0;
    public double b = 0;
    public String record = "";

    public GameControl(int a, int b, int c, int d) {
        playerx = a;
        playery = b;
        doorx = c;
        doory = d;
    }

    public void control() {
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            key = Character.toLowerCase(key);
            record += key;
            if (record.contains(":q")){
                record = "";
                System.out.println(record);
                Savegame(world,this);
                Quit();
            }
            switch (key) {
                case 'w':
                    moveup();
                    break;
                case 's':
                    movedown();
                    break;
                case 'a':
                    moveleft();
                    break;
                case 'd':
                    moveright();
                    break;
            }
        }
    }

    public void moveup() {
        if (world[playerx][playery + 1].equals(Tileset.FLOOR)) {
            world[playerx][playery + 1] = Tileset.PLAYER;
            world[playerx][playery] = Tileset.FLOOR;
            playery += 1;
            HUD();
            ;
        }
    }

    public void movedown() {
        if (world[playerx][playery - 1].equals(Tileset.FLOOR)) {
            world[playerx][playery - 1] = Tileset.PLAYER;
            world[playerx][playery] = Tileset.FLOOR;
            playery -= 1;
            HUD();;
        }
    }

    public void moveleft() {
        if (world[playerx - 1][playery].equals(Tileset.FLOOR)) {
            world[playerx - 1][playery] = Tileset.PLAYER;
            world[playerx][playery] = Tileset.FLOOR;
            playerx -= 1;
            HUD();;
        }

    }

    public void moveright() {
        if (world[playerx + 1][playery].equals(Tileset.FLOOR)) {
            world[playerx + 1][playery] = Tileset.PLAYER;
            world[playerx][playery] = Tileset.FLOOR;
            playerx += 1;
            HUD();
            ;
        }
    }

    private void HUD() {
        if (playwithstring == false) {
            StdDraw.clear();
            ter.renderFrame(world);
            String type = blockcheck((int) Math.floor(StdDraw.mouseX()), (int) Math.floor(StdDraw.mouseY()));
            StdDraw.setPenColor(Color.white);
            StdDraw.textLeft(1, HEIGHT - 1, type);
            StdDraw.line(0, HEIGHT - 2, WIDTH, HEIGHT - 2);
            StdDraw.show();
            while (!StdDraw.hasNextKeyTyped()) {
                if (StdDraw.mouseX() > 0 && StdDraw.mouseY() > 0) {
                    if (a != StdDraw.mouseX() || b != StdDraw.mouseY()) {
                        StdDraw.clear();
                        ter.renderFrame(world);
                        StdDraw.setPenColor(Color.white);
                        String type2 = blockcheck((int) Math.floor(StdDraw.mouseX()), (int) Math.floor(StdDraw.mouseY()));
                        StdDraw.textLeft(1, HEIGHT - 1, type2);
                        StdDraw.line(0, HEIGHT - 2, WIDTH, HEIGHT - 2);
                        StdDraw.show();
                        a = StdDraw.mouseX();
                        b = StdDraw.mouseY();
                    }
                }
            }
        }
    }

    private String blockcheck(int x, int y){
        if (x>WIDTH-1){
            x=WIDTH-1;
        }
        if (y>HEIGHT-1){
            y=HEIGHT-1;
        }
        return world[x][y].description();
    }
}