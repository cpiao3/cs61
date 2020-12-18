import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {
        private int width;
        private int height;
        private Picture picture;
        public SeamCarver(Picture picture){
                width = picture.width();
                height = picture.height();
                this.picture = new Picture(picture);
        }
        public Picture picture() { return picture;}
        public int width() { return width;}
        public int height()   { return height;}

        public  double energy(int x, int y)    {
                int energy = 0;
                boolean corner = false;
                if (x==0||x==width-1||y==0||y==height-1){
                        corner = true;
                }
                        boolean is_x = true;
                        for (int i = 0; i<2 ; i += 1){
                               energy += energycal(x,y,is_x,corner);
                               is_x = false;
                        }
                return energy;
        }
        //hor//
        private double energycal(int x, int y,boolean is_x,boolean corner){
                int a = 1; int b = 1; int c = 1; int d = 1;
                if (corner){
                        if (x == 0){
                                b = x - (width -1);
                        }
                        if (x == width -1){
                                a = -x;
                        }
                        if (y == 0){
                                d = y - (height -1);
                        }
                        if (y == height -1){
                                c = -y;
                        }
                }
                if (! is_x){ c = 0; d = 0;} else {a = 0; b = 0;}
                Color color = picture.get(x+a,y+c);
                Color color2 = picture.get(x-b,y-d);
                double red = Math.abs(color.getRed() - color2.getRed());
                double green = Math.abs(color.getGreen() - color2.getGreen());
                double blue = Math.abs(color.getBlue() - color2.getBlue());
                return red*red+green*green+blue*blue;
        }


        public   int[] findHorizontalSeam() {
                Picture newpic = new Picture(height,width);
                int x2=1;
                for (int y = 0; y < height; y++) {
                        int y2=0;
                        for (int x = 0; x < width; x++) {
                                Color color = picture.get(x,y);
                                newpic.set(height-x2,y2,color);
                                y2 += 1;
                        }
                        x2 += 1;
                }
                SeamCarver newseam = new SeamCarver(newpic);
                int[] result = newseam.findVerticalSeam();
                for (int a = 0 ; a < result.length ; a+=1){
                        result[a] = height - result[a] -1 ;
                }
                return result;
        }


        public int[] findVerticalSeam() {
                int [][] trace = new int[height][width];
                double [][] energy = new double[height][width];
                for (int i = 0 ; i < width; i+=1){
                        energy[0][i] = energy(i,0);
                }
                if (width == 1){
                        int[] special = new int[height];
                        for (int k = 0 ; k < height ; k += 1){
                                special[k] = 0;
                        }
                        return special;
                }

                for (int y = 1; y < height; y+=1){
                        for (int x = 0; x<width; x+=1){
                                if (x == 0){
                                        if (energy[y-1][x] > energy[y-1][x+1]){
                                                energy[y][x] = energy(x,y) + energy[y-1][x+1];
                                                trace[y][x] = 1;
                                        } else {
                                                energy[y][x] = energy(x,y) + energy[y-1][x];
                                                trace[y][x] = 0;
                                        }
                                } else if (x == width-1){
                                        if (energy[y-1][x] > energy[y-1][x-1]){
                                                energy[y][x] = energy(x,y) + energy[y-1][x-1];
                                                trace[y][x] = -1;
                                        } else {
                                                energy[y][x] = energy(x,y) + energy[y-1][x];
                                                trace[y][x] = 0;
                                        }
                                } else {
                                        int count = 0;
                                        double max = Double.POSITIVE_INFINITY;
                                        for (int p = -1; p < 2 ; p+=1){
                                                if(energy[y-1][x+p]<max){
                                                        max = energy[y-1][x+p];
                                                        count = p;
                                                }
                                        }
                                        energy[y][x] = energy(x,y) + energy[y-1][x+count];
                                        trace[y][x] = count;
                                }
                        }
                }

                return solution(energy, trace);
        }

        private int[] solution(double[][] energy, int[][] trace){
                double max = Double.POSITIVE_INFINITY;
                int count = 0;
                int[] result = new int[height];
                for (int i = 0 ; i < width ; i ++){
                        if (energy[height-1][i] < max){
                                max = energy[height-1][i];
                                count = i;
                        }
                }
                result[height - 1] = count;
                int move = trace[height-1][count];;
                for (int j = 1 ; j < height ; j +=1){
                        result[height-j-1] = count + move;
                        count = result[height-j-1];
                        move = trace[height-j-1][count];
                }
                return result;
        }



        public void removeHorizontalSeam(int[] seam){
                Picture newpic = new Picture(picture);
                SeamRemover.removeHorizontalSeam(newpic,seam);
        }   // remove horizontal seam from picture
        public void removeVerticalSeam(int[] seam)  {
                Picture newpic = new Picture(picture);
                SeamRemover.removeHorizontalSeam(newpic,seam);
        }   // remove vertical seam from picture


}
