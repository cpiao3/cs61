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
                -122.24005830775027, 37.8281503452548,
                -122.29352208059518, 37.87137785541728);
        System.out.println("2835287468, 53041668, 53076250, 260707354, 2834093713, 53076247, 53076246, 4671608605, 53076245, 4671608602, 53076244, 4671608604, 687156431, 53075606, 4671608607, 4168192169, 201496240, 206093625, 311881848, 4671608609, 4671608612, 53147548, 247141146, 899953916, 4671608614, 247141147, 4671608616, 53038057, 3695372188, 899950046, 2932394317, 2932394321, 2932394322, 2932394300, 53134475, 957600270, 53112641, 694066101, 694066100, 694066099, 694066098, 694066097, 694066096, 694066094, 694066093, 694066092, 694066091, 694066090, 694066089, 694066079, 694066088, 694066087, 4168192172, 694066085, 694066084, 694066083, 694066082, 1003274388, 4168192174, 694066185, 694066184, 694066156, 4168192176, 694066183, 694066182, 694066153, 4168192178, 694066181, 694066149, 694066180, 694066179, 694066178, 694066177, 694066176, 694066163, 4169996303, 694066160, 4169996304, 694066175, 694066174, 694066172, 694066171, 4169996306, 3127028848, 694066169, 694066168, 53099316, 506458653, 302801824, 2430670764, 506458647, 302801863, 212465293, 3969482339, 3969482340, 212465291, 682403904, 302801977, 506459075, 4918915970, 4918915965, 3969482338, 95164561, 506459086, 271855224, 92984207, 53045882, 5060959012, 5060959013, 4169996312, 4169996311, 1203807993, 280431894, 4169996314, 53079228, 266434152, 1237053620, 53126702, 266434139, 2352069432, 4169996317, 1237053577, 53092590, 1237053611, 1475224966, 95323788, 2375208693, 1237053560, 95323772, 1237053613, 95323768, 95323761, 1237053736, 1237053724, 2240045668, 53112624, 2240045667, 430992572, 53090197, 430992573, 240469672, 53085437, 53085434, 1237053582, 92951507, 53085433, 1237053638, 92951634, 53041552, 4559569769, 53085432, 4559569771, 53085430, 53085428, 53085427, 1994957632, 302803293, 53050644, 4235206796, 53050642, 4235206798, 4235206794, 53126424, 4235206793, 4235206789, 53077537, 4235205788, 53066471, 53056112, 4235205783, 53126421, 1237062765, 53126420, 1237062767, 53092791, 53064275, 4235206803, 53043845, 4235206807, 53130811, 53130814, 4235206820, 1612672411, 283261509, 2644106900, 4168260361, 2644106912, 4213425299, 2644106917, 4213425302, 53109969, 4213425305, 243673081, 4168260368, 4213425309, 243673082, 4213425313, 1613874217, 1613874246, 4213425315, 243673084, 4213378290, 243673085, 4168260379, 4213378292, 243673087, 4213378295, 4213378299, 243673088, 4168260384, 4213378302, 243673089, 4213378305, 243673091, 1745072095, 243673093, 243673096, 243673098, 4213378310, 4213378313, 243673100, 4213378316, 4168148260, 4213378319, 243670956, 4213378322, 4213378324, 243673103, 4467802979, 4168148263, 4213378327, 243673105, 4213378331, 4213378332, 4213378334, 243673107, 4213378338, 4213378341, 243673110, 4168148267, 4213378346, 243673112, 4213378350, 4544254687, 4544260190, 4168148271, 4213378352, 243673115, 4213378356, 1671396836, 1670810118, 4213378364, 243673117, 4168148273, 4213378367, 243673120, 4213378370, 243673123, 3720819936, 4168148280, 4213378373, 243673125, 243673129, 4213378379, 1669800003, 1669807797, 4168148392, 4213378385, 243670962, 53055171, 4213378388, 4168148397, 4213378490, 53093336, 4213378492");
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
