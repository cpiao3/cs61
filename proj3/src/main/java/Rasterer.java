import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    private double QueryLonDPP;
    private double Width;
    private double Length;
    public Rasterer() {
        Width = MapServer.ROOT_LRLON - MapServer.ROOT_ULLON;
        Length = MapServer.ROOT_LRLAT - MapServer.ROOT_ULLAT;
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        Map<String, Object> results = new HashMap<>();
        if( params.get("lrlon")>MapServer.ROOT_LRLON || params.get("lrlat")<MapServer.ROOT_LRLAT ||params.get("ullon")<MapServer.ROOT_ULLON||params.get("ullat")>MapServer.ROOT_ULLAT){
            results.put("query_success",false);
        } else if (params.get("ullon")>params.get("lrlon")||params.get("ullat")<params.get("lrlat")) {
            results.put("query_success", false);
        }
         else {
            results.put("query_success", true);
        }
        QueryLonDPP = (params.get("lrlon") - params.get("ullon")) / params.get("w");
        int depth = getdepth();
        int x = (int)Math.floor((params.get("ullon") - MapServer.ROOT_ULLON) / (Width/ Math.pow(2,depth)));
        int y = (int)Math.floor((params.get("ullat") - MapServer.ROOT_ULLAT) / (Length/ Math.pow(2,depth)));
        int xlow = (int)Math.ceil((params.get("lrlon") - MapServer.ROOT_ULLON) / (Width/ Math.pow(2,depth)));
        int ylow = (int)Math.ceil((params.get("lrlat") - MapServer.ROOT_ULLAT) / (Length/ Math.pow(2,depth)));
        results.put("raster_ul_lon",MapServer.ROOT_ULLON+(Width/ Math.pow(2,depth))*x);
        results.put("raster_ul_lat",MapServer.ROOT_ULLAT+(Length/ Math.pow(2,depth))*y);
        results.put("raster_lr_lon",MapServer.ROOT_ULLON+(Width/ Math.pow(2,depth))*xlow);
        results.put("raster_lr_lat",MapServer.ROOT_ULLAT+(Length/ Math.pow(2,depth))*ylow);
        results.put("depth",depth);
        String[][] render = render_grid(x,y,xlow,ylow,depth);
        results.put("render_grid",render);
        return results;
    }

    private String[][] render_grid(int x,int y,int xlow,int ylow,int depth){
        String[][] render = new String[ylow - y][xlow - x];
        for (int i = 0;i<render.length;i++){
            for (int b = 0;b<render[0].length;b++){
                render[i][b] = "d"+String.valueOf(depth)+"_x"+String.valueOf(x+b)+"_y"+String.valueOf(y+i)+".png";
            }
        }
        return render;
    }

    private int getdepth(){
        double Wid = Width;
        double imgLonDPP = Wid/256;
        int depth = 0;
        while (imgLonDPP > QueryLonDPP){
            Wid = Wid/2;
            imgLonDPP = Wid/256;
            depth++;
        }
        if (depth > 7){
            depth = 7;
        }
        return depth;
    }


}
