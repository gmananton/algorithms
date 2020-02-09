import java.util.*;
import java.util.stream.Collectors;

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
    private static Set<String> nodeNames = costs.keySet();
    private static Set<String> processed = new HashSet<>();


    public static void main(String[] args) {

        while (unprocessedNodesRemain()) {
            String currentNode = getClosestNode(costs);
            Double currentCost = costs.get(currentNode);
            WeightedNodes children = graph.get(currentNode);

            for (String nodeName : children.keySet()) {
                Double newSumCost = children.get(nodeName) + currentCost;
                Double initialCost = costs.get(nodeName);
                if (newSumCost < initialCost) {
                    costs.put(nodeName, newSumCost);
                    parents.put(nodeName, currentNode);
                }
            }

            processed.add(currentNode);
        }

        System.out.println("Minimal total cost: " + costs.get(NODE_FINISH));
        Stack<String> order = new Stack<>();
        String current = NODE_FINISH;

        do {
            order.push(current);
            current = parents.get(current);
        } while (current != null);


        StringBuilder sb = new StringBuilder("Path: ");

        int size = order.size();
        for (int i = 0; i < size; i++) {
            sb.append(order.pop());
            if (i != size - 1) {
                sb.append(" -> ");
            }
        }

        System.out.println(sb.toString());

    }


    private static String getClosestNode(Map<String, Double> costs) {
        return costs.entrySet().stream()
                .filter(entry -> !processed.contains(entry.getKey()))
                .min(Comparator.comparing(Map.Entry::getValue))
                .get()
                .getKey();
    }


    private static boolean unprocessedNodesRemain() {
        return !processed.containsAll(costs.keySet().stream().filter(k -> !NODE_START.equals(k) && !NODE_FINISH.equals(k)).collect(Collectors.toSet()));
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
    public static class WeightedGraph extends HashMap<String, WeightedNodes> {
        public WeightedNodes appendChild(String parentName, String childName, Double childWeight) {
            WeightedNodes nodes = containsKey(parentName) ? get(parentName) : new WeightedNodes();
            nodes.put(childName, childWeight);
            return put(parentName, nodes);
        }
    }

    //Узлы с весом
    public static class WeightedNodes extends HashMap<String, Double> {
    }
}
