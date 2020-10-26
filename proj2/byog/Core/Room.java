package byog.Core;
import byog.TileEngine.Tileset;

import java.util.Random;
public class Room extends MapGenerator {
    public boolean existence = false;
    public int xlength = 0;
    public int ylength = 0;
    public int x;
    public int y;
    public Room next;
    public static Room first;

    public Room(int a, int b) {
        x = a;
        y = b;
        next = first;
    }

    public static void roommaker(int number) {
        Random r = new Random(seed);
        for (int i = 0; i < number; i++) {
            int locationX = r.nextInt(lengthone - 8);
            int locationY = r.nextInt(lengthtwo - 8);
            Room a = new Room(locationX, locationY);
            first = a;
        }
        draw();
    }

    private static void draw() {
        Room pointer = first;
        Random r = new Random(seed);
        while (pointer != null) {
            Room compare = first;
            int e = 1 + r.nextInt(7);
            int d = 1 + r.nextInt(7);
            while (compare != null) {
                if (pointer != compare) {
                    if ((pointer.x + e >= compare.x && pointer.x + e <= compare.x + compare.xlength) && (pointer.y + d >= compare.y && pointer.y + d <= compare.y + compare.ylength)) {
                        deletetroom(pointer);
                        break;
                    } else if (((pointer.x >= compare.x) && (pointer.x <= compare.x + compare.xlength)) && ((pointer.y >= compare.y) && (pointer.y <= compare.y + compare.ylength))) {
                        deletetroom(pointer);
                        break;
                    } else if ((pointer.x <= compare.x + compare.xlength && pointer.x >= compare.x) && (compare.y <= d + pointer.y && compare.y >= pointer.y)) {
                        deletetroom(pointer);
                        break;
                    } else if ((pointer.y <= compare.y + compare.ylength && pointer.y >= compare.y) && (compare.x <= e + pointer.x && compare.x >= pointer.x)) {
                        deletetroom(pointer);
                        break;
                    } else if ((pointer.y + d >= compare.y && pointer.y + d <= compare.y + compare.ylength) && (pointer.x + e > compare.x && pointer.x <= compare.x)) {
                        deletetroom(pointer);
                        break;
                    } else if ((pointer.x + e >= compare.x && pointer.x + e <= compare.x + compare.xlength) && (pointer.y <= compare.y && pointer.y + d >= compare.y)) {
                        deletetroom(pointer);
                        break;
                    }
                }
                compare = compare.next;
                if (compare == null) {
                    drawselectroom(pointer, e, d);
                }
            }
            pointer = pointer.next;
        }
        refactor();
        roomwall(first);
        Hallway.connect(first);


    }

    private static void deletetroom(Room pointer) {
        pointer.x = 0;
        pointer.y = 0;
    }

    private static void drawselectroom(Room pointer, int e, int d) {
        pointer.xlength = e + 1;
        pointer.ylength = d + 1;
        for (int i = 0; i <= e; i++) {
            for (int b = 0; b <= d; b++) {
                world[pointer.x + i][pointer.y + b] = Tileset.FLOOR;
            }
            pointer.existence = true;
        }
    }


    private static void roomwall(Room rooms) {
        Room pointer = rooms;
        while (pointer != null) {
            drawroomwall(pointer);
            pointer = pointer.next;
        }
    }

    private static void drawroomwall(Room room) {
        for (int i = 0; i <= room.xlength; i++) {
            if (room.x + i < lengthone) {
                world[room.x + i][room.y] = Tileset.WALL;
                if (room.y + room.ylength < lengthtwo) {
                    world[room.x + i][room.y + room.ylength] = Tileset.WALL;
                } else {
                    world[room.x + i][room.y + room.ylength - 1] = Tileset.WALL;
                }
            }

        }
        for (int i = 0; i <= room.ylength; i++) {
            if (room.y + i < lengthtwo) {
                world[room.x][room.y + i] = Tileset.WALL;
                if (room.x + room.xlength < lengthone) {
                    world[room.x + room.xlength][room.y + i] = Tileset.WALL;
                } else {
                    world[room.x + room.xlength - 1][room.y + i] = Tileset.WALL;
                }
            }
        }
    }

    private static void refactor() {
        Room pointer = first;
        Room pointer2 = first;
        if (!first.existence) {
            while (!pointer.existence) {
                pointer = pointer.next;
            }
            first = pointer;
        }
            while (pointer != null) {
                if (!pointer.existence) {
                    while (!pointer.existence) {
                        pointer = pointer.next;
                        if (pointer == null){
                            break;
                        }
                    }
                    pointer2.next = pointer;
                } else {
                    pointer2 = pointer;
                    pointer = pointer.next;
                }
            }


    }

}

