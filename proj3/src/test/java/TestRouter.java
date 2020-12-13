import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestRouter {
    private static final String PARAMS_FILE = "path_params.txt";
    private static final String RESULTS_FILE = "path_results.txt";
    private static final int NUM_TESTS = 8;
    private static final String OSM_DB_PATH = "../library-sp18/data/berkeley-2018.osm.xml";
    private static GraphDB graph;
    private static boolean initialized = false;

    @Before
    public void setUp() throws Exception {
        if (initialized) {
            return;
        }
        graph = new GraphDB(OSM_DB_PATH);
        initialized = true;
    }

    
    public void testShortestPath() throws Exception {
        List<Map<String, Double>> testParams = paramsFromFile();
        List<List<Long>> expectedResults = resultsFromFile();

        for (int i = 0; i < NUM_TESTS; i++) {
            System.out.println(String.format("Running test: %d", i));
            Map<String, Double> params = testParams.get(i);
            List<Long> actual = Router.shortestPath(graph,
                    params.get("start_lon"), params.get("start_lat"),
                    params.get("end_lon"), params.get("end_lat"));
            List<Long> expected = expectedResults.get(i);
            assertEquals("Your results did not match the expected results", expected, actual);
        }

    }
    @Test
    public void testShortestPath2() throws Exception {
        List<Map<String, Double>> testParams = paramsFromFile();
        List<List<Long>> expectedResults = resultsFromFile();
        List<Long> actual = Router.shortestPath(graph,
                -122.23835855879263, 37.851119329106375,
                -122.2253041801838, 37.837500439402675);
        System.out.println(actual);
        System.out.println("[53147976, 1214416589, 1214416609, 265517616, 93129606, 1214416626, 265517614, 265517626, 53147975, 53041003, 542986465, 1214187363, 1214187371, 93129620, 1214187368, 265517455, 93129631, 247471394, 93147720, 1214187362, 1214187364, 93148507, 265517387, 93147738, 53025122, 1214187369, 265517454, 93129587, 265517230, 265517229, 93148793, 1214187367, 93129574, 1214187372, 201593224, 265517367, 265517228, 1214187373, 93129562, 265517227, 244430494, 93129547, 244430493, 53040977, 53021639, 265517181, 93129744, 265517180, 201590560, 310939817, 53040975, 244430417, 93129751, 93129761, 244430346, 265517226, 93129768, 53039682, 93147624, 244430387, 93147631, 265518434, 53040994, 265518433, 93147637, 93147639, 93147642, 265511601, 93147644, 265511599, 266449662, 265511606, 265511598, 265511597, 93186708, 53040992, 265511596, 266449867, 93186629, 661790528, 265511595, 53040990, 661790526, 265511594, 200247404, 265511434, 265511433, 200247403, 265511432, 201589776, 265511431, 53040988, 265511430, 265511429, 93186625, 265511428, 201589773, 93186621, 265511362, 265511361, 53040986, 265511360, 93186616, 265511359, 265511358, 53034090, 53040983, 265511357, 201587492, 265511355, 53040981, 206056749, 265511060, 206056750, 265511061, 206056752, 206056753, 265511062, 206056754, 265511063, 53151728, 265511064, 206056756, 206056757, 265511065, 206056758, 265511066, 53092406, 661803187, 661803188, 206054719, 265511018, 265511019, 206055480, 265511020, 661803193, 53083151, 661803195, 247969135, 206055481, 661803198, 206054721, 53024905, 53024903, 53024901, 265519857, 2402257532, 2402257537, 53083133, 2402257525, 2402257539, 200444846, 244468999, 200444848, 244469000, 53032027, 53032037, 558297760, 206052611, 206052612, 206052613, 206052614, 206052615, 244468615, 206052616, 244468653, 206052617, 206052618, 206052619, 53115146, 53042273, 956504902, 53042275, 206053817, 53042277, 956454544, 956454547, 956454334, 956454549]>");
    }

    private List<Map<String, Double>> paramsFromFile() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(PARAMS_FILE), Charset.defaultCharset());
        List<Map<String, Double>> testParams = new ArrayList<>();
        int lineIdx = 2; // ignore comment lines
        for (int i = 0; i < NUM_TESTS; i++) {
            Map<String, Double> params = new HashMap<>();
            params.put("start_lon", Double.parseDouble(lines.get(lineIdx)));
            params.put("start_lat", Double.parseDouble(lines.get(lineIdx + 1)));
            params.put("end_lon", Double.parseDouble(lines.get(lineIdx + 2)));
            params.put("end_lat", Double.parseDouble(lines.get(lineIdx + 3)));
            testParams.add(params);
            lineIdx += 4;
        }
        return testParams;
    }

    private List<List<Long>> resultsFromFile() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(RESULTS_FILE), Charset.defaultCharset());
        List<List<Long>> expected = new ArrayList<>();
        int lineIdx = 2; // ignore comment lines
        for (int i = 0; i < NUM_TESTS; i++) {
            int numVertices = Integer.parseInt(lines.get(lineIdx));
            lineIdx++;
            List<Long> path = new ArrayList<>();
            for (int j = 0; j < numVertices; j++) {
                path.add(Long.parseLong(lines.get(lineIdx)));
                lineIdx++;
            }
            expected.add(path);
        }
        return expected;
    }
}
