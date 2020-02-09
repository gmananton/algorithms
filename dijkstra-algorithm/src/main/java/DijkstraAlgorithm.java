import java.util.*;

public class DijkstraAlgorithm {

    private static final String NODE_START = "start";
    private static final String NODE_FINISH = "finish";
    private static final String NODE_A = "A";
    private static final String NODE_B = "B";
    private static final String NODE_UNKNOWN = "-";

    private static WeightedGraph graph = new WeightedGraph() {{
        appendChild(NODE_START, NODE_A, 6.0);
        appendChild(NODE_START, NODE_B, 2.0);
        appendChild(NODE_B, NODE_A, 3.0);
        appendChild(NODE_B, NODE_FINISH, 5.0);
        appendChild(NODE_A, NODE_FINISH, 1.0);
    }};


    private static Map<String, Double> costs = initCosts(graph);
    private static Map<String, String> parents = initParents(graph);
    private static Set<String> nodeNames = new HashSet<>();
    private static Set<String> processed = new HashSet<>();


    public static void main(String[] args) {

        System.out.println(graph);
        System.out.println(costs);
        System.out.println(parents);

        while (unprocessedNodesRemain()) {

        }

    }

    private static String getClosestNode(Map<String, Double> costs) {
        costs.entrySet().stream().min(Comparator.comparing(Map.Entry::getValue)).get().getKey()
    }




    private static boolean unprocessedNodesRemain() {
        return !processed.containsAll(costs.keySet());
    }

    private static Map<String, Double> initCosts(WeightedGraph graph) {

        Map<String, Double> costs = new HashMap<>();

        graph.keySet().forEach(nodeName -> {
            if (NODE_START.equals(nodeName)) {
                costs.putAll(graph.get(nodeName));
            } else {
                graph.get(nodeName).keySet().forEach(k -> costs.put(k, Double.POSITIVE_INFINITY));
            }
        });

        return costs;
    }

    private static Map<String, String> initParents(WeightedGraph graph) {

        Map<String, String> parents = new HashMap<>();

        graph.keySet().forEach(nodeName -> {
            graph.get(nodeName).keySet().forEach(child -> {
                String parent = NODE_START.equals(nodeName) ? NODE_START : NODE_UNKNOWN;
                parents.put(child, parent);
            });
        });

        return parents;
    }


    //Взвешенный граф
    public static class WeightedGraph extends HashMap<String, WeightedNode> {
        public WeightedNode appendChild(String parentName, String childName, Double childWeight) {
            WeightedNode node = containsKey(parentName) ? get(parentName) : new WeightedNode();
            node.put(childName, childWeight);
            return put(parentName, node);
        }
    }

    //Узел с весом
    public static class WeightedNode extends HashMap<String, Double> {
    }
}
