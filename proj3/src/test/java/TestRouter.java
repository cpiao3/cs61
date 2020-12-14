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

@Test
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
                -122.27920958817269, 37.86133160701604,
                -122.29117515783712, 37.829891956112334);
        System.out.println("[4233844036, 4233489872, 240469785, 4233489870, 240469693, 240469694, 4233489863, 240469619, 4233489859, 4233489853, 240469512, 4233489848, 4233489841, 240469696, 4233489836, 4168148256, 4233489833, 240469515, 4233489829, 4233489810, 240469624, 4233489807, 4233489804, 240469698, 4233489799, 4168260383, 4233489790, 240469627, 4233488286, 4168260380, 240469629, 2422457034, 4233488271, 240469700, 4233488264, 4168260374, 1994957627, 53039454, 4235206879, 240469701, 4235206873, 240469703, 4235206859, 283296748, 4168260363, 4235206848, 240469705, 4235206842, 4168260360, 4235206816, 240469707, 4235206810, 4235206802, 240469532, 4235206797, 240469487, 240469783, 240469483, 53085428, 1994957633, 283296744, 2184578208, 53088321, 2356904753, 53085979, 2356904754, 53096356, 53096359, 4419821451, 53096361, 4168227709, 53124565, 53113346, 53124566, 1237060520, 4607053146, 4607053154, 92951682, 4168227704, 1237060523, 53124567, 283261585, 283261586, 1237060564, 4168227702, 1237060562, 280699442, 280699449, 280699448, 53102030, 53111590, 53124570, 53124572, 4923455149, 4602705885, 280698106, 53124574, 2386673037, 2386673028, 2386673022, 5049820823, 5049820822, 5049820821, 5049820820, 2386673011, 2386673007, 52984802, 2386672987, 52984798, 52984795, 3231927622, 53149464, 53149462, 4923455202, 53144738, 53149459, 4923455099, 53129209, 53063399, 53063400, 53054869, 53054868, 53073997]");
         System.out.println(actual);


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
