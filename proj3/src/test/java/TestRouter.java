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
                -122.24333588589685, 37.83260352773598,
                -122.29916664337156, 37.873889756810954);
        System.out.println("[53055671, 53055669, 2932394312, 2930069056, 3695372193, 2932394319, 2932394322, 2932394300, 53134475, 957600270, 53112641, 694066101, 694066100, 694066099, 694066098, 694066097, 694066096, 694066094, 694066093, 694066092, 694066091, 694066090, 694066089, 694066079, 694066088, 694066087, 4168192172, 694066085, 694066084, 694066083, 694066082, 1003274388, 4168192174, 694066185, 694066184, 694066156, 4168192176, 694066183, 694066182, 694066153, 4168192178, 694066181, 694066149, 694066180, 694066179, 694066178, 694066177, 694066176, 694066163, 4169996303, 694066160, 4169996304, 694066175, 694066174, 694066172, 694066171, 4169996306, 3127028848, 694066169, 694066168, 53099316, 506458653, 302801824, 2430670764, 506458647, 302801863, 212465293, 3969482339, 3969482340, 212465291, 682403904, 302801977, 506459075, 4918915970, 4918915965, 3969482338, 95164561, 506459086, 271855224, 92984207, 53045882, 5060959012, 5060959013, 4169996312, 4169996311, 1203807993, 280431894, 4169996314, 53079228, 266434152, 1237053620, 53126702, 266434139, 2352069432, 4169996317, 1237053577, 53092590, 1237053611, 1475224966, 95323788, 2375208693, 1237053560, 95323772, 1237053613, 95323768, 95323761, 1237053736, 1237053724, 2240045668, 53112624, 2240045667, 430992572, 53090197, 430992573, 240469672, 53085437, 53085434, 1237053582, 92951507, 53085433, 1237053638, 92951634, 53041552, 4559569769, 53085432, 4559569771, 53085430, 53085428, 53085427, 1994957632, 302803293, 53050644, 4235206796, 53050642, 4235206798, 4235206794, 53126424, 4235206793, 4235206789, 53077537, 4235205788, 53066471, 53056112, 4235205783, 53126421, 1237062765, 53126420, 283261522, 1237062769, 283261516, 4168260357, 283261515, 4235206800, 283261510, 4235206806, 4168260359, 283261580, 4235206819, 1612672431, 283261579, 2644106909, 4213425298, 2644106915, 4213425301, 243673080, 4213425304, 53097108, 4213425308, 53117370, 4213425310, 4168227716, 669535463, 553994860, 687179394, 448416526, 5035371477, 4168227715, 53055544, 2421788591, 53039509, 4218967477, 4927340035, 4218967474, 53039510, 4218967475, 4168260371, 53131108, 4218967480, 4168260373, 4927340027, 3701567472, 4927335918, 4927340067, 4168260375, 4218967483, 53108745, 4218967486, 4168260378, 3701830693, 3701831183, 3701829993, 4218967694, 4218967695, 53093979, 4218967698, 53100802, 53079160, 53131106, 4218967716, 4168148255, 4218967717, 53085634, 4218967720, 4168148257, 53115063, 4168148261, 4218827190, 53020923, 260577520, 4168148262, 4218827204, 53029741, 260577515, 260577513, 4218827218, 53029740, 4218827221, 4218827243, 53029737, 4218827246, 4168148269, 4218827257, 53029736, 4218827260, 4168148270, 53029733, 4322651866, 1490086207, 4322651867, 2811333703, 4217221754, 240448872, 53029731, 4217221763, 4248077708, 4168148277, 4168148283, 4217112419, 53029730, 4217112422, 4168148287, 1979210321, 53029729, 1979210320, 4168148399, 4168148403, 4217112450, 53029728, 4217112452, 4168148406, 4168148410]>");
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
