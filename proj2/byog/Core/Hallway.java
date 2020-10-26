package byog.Core;

import byog.TileEngine.Tileset;

public class Hallway extends MapGenerator {
    public int X;
    public int Y;
    public int length;
    public boolean horz;
    public Hallway next;
    public static Hallway first;

    public Hallway(int a, int b, int c, boolean d) {
        X = a;
        Y = b;
        length = c;
        horz = d;
        next = first;
    }

    public static void connect(Room rooms) {
        if (first != null){
            first =null;
        }
        Room pointer = rooms;
        while (pointer.next != null) {
            Xhallwaymaker(pointer, pointer.next);
            pointer = pointer.next;
        }
        drawhallway();
        hallwaywall();
    }

    private static void Xhallwaymaker(Room a, Room b) {
        int xdif = a.x - b.x;
        if (xdif > 0) {
            first = new Hallway(b.x + 1, b.y + 1, xdif, true);
            Yhallwaymaker1(a, b);
        } else {
            first = new Hallway(a.x + 1, a.y + 1, -xdif, true);
            Yhallwaymaker2(a, b);
        }

    }

    private static void Yhallwaymaker1(Room a, Room b) {
        int ydif = a.y - b.y;
        if (ydif > 0) {
            first = new Hallway(a.x + 1, b.y + 1, ydif, false);
        } else {
            first = new Hallway(a.x + 1, a.y + 1, -ydif, false);
        }
    }

    private static void Yhallwaymaker2(Room a, Room b) {
        int ydif = a.y - b.y;
        if (ydif > 0) {
            first = new Hallway(b.x + 1, b.y + 1, ydif, false);
        } else {
            first = new Hallway(b.x + 1, a.y + 1, -ydif, false);
        }
    }

    private static void drawhallway() {
        Hallway pointer = first;
        while (pointer != null) {
            if (pointer.horz) {
                drawhorz(pointer);
            } else {
                drawvert(pointer);
            }
            pointer = pointer.next;
        }
    }

    private static void drawhorz(Hallway pointer) {
        for (int i = 0; i <= pointer.length; i++) {
            world[pointer.X + i][pointer.Y] = Tileset.FLOOR;
        }
    }

    private static void drawvert(Hallway pointer) {
        for (int i = 0; i <= pointer.length; i++) {
            world[pointer.X][pointer.Y + i] = Tileset.FLOOR;
        }
    }

    private static void hallwaywall() {
        Hallway pointer = first;
        while (pointer != null) {
            if (pointer.horz) {
                drawhorzwall(pointer);
            } else {
                drawvertwall(pointer);
            }
            pointer = pointer.next;
        }
    }

    private static void drawhorzwall(Hallway pointer) {
        for (int i = 0; i <= pointer.length+1; i++) {
            if (world[pointer.X + i][pointer.Y + 1] != Tileset.FLOOR) {
                world[pointer.X + i][pointer.Y + 1] = Tileset.WALL;
            }
            if (world[pointer.X + i][pointer.Y - 1] != Tileset.FLOOR) {
                world[pointer.X + i][pointer.Y - 1] = Tileset.WALL;
            }
        }
    }

    private static void drawvertwall(Hallway pointer) {
        for (int i = 0; i <= pointer.length+1; i++) {
            if (world[pointer.X + 1][pointer.Y + i] == Tileset.NOTHING) {
                world[pointer.X + 1][pointer.Y + i] = Tileset.WALL;
            }
            if (world[pointer.X - 1][pointer.Y + i] == Tileset.NOTHING) {
                world[pointer.X - 1][pointer.Y + i] = Tileset.WALL;
            }
        }
    }
}
