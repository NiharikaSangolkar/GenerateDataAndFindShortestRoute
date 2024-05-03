
package Interview;

import java.util.*;

public class GenerateDataAndFindShortestRoute {

    public static void main(String[] args) {
        // Generate random nodes and edges based on email id
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter email:");
        String email = sc.nextLine();
        List<String> nodes = generateNodes(email);
        List<Edge> edges = generateEdges(nodes);

        // Print the generated data
        System.out.println("Nodes: " + nodes);
        System.out.println("Edges: ");
        for (Edge edge : edges) {
            System.out.println(edge);
        }

        // Find the shortest route
        String startNode = "C"; // Start node
        String endNode = "F"; // End node
        List<String> shortestRoute = shortestRoute(nodes, edges, startNode, endNode);

        // Print the shortest route
        System.out.println("Shortest Route from " + startNode + " to " + endNode + ": " + shortestRoute);
    }

    // Generate nodes based on email id
    public static List<String> generateNodes(String email) {
        int seed = getSeed(email);
        Random random = new Random(seed);

        List<String> nodes = new ArrayList<>();
        int numNodes = random.nextInt(10) + 5; // Random number of nodes between 5 and 14
        for (int i = 0; i < numNodes; i++) {
            String node = generateNode(random);
            while (nodes.contains(node)) {
                node = generateNode(random);
            }
            nodes.add(node);
        }
        return nodes;
    }

    // Generate edges based on nodes
    public static List<Edge> generateEdges(List<String> nodes) {
        List<Edge> edges = new ArrayList<>();
        Random random = new Random();
        for (String from : nodes) {
            for (String to : nodes) {
                if (!from.equals(to) && random.nextBoolean()) {
                    double cost = random.nextDouble() * 10; // Random cost between 0 and 10
                    edges.add(new Edge(from, to, cost));
                }
            }
        }
        return edges;
    }

    // Generate a seed for the random number generator based on the input email address
    public static int getSeed(String email) {
        int seed = 0;
        for (int i = 0; i < email.length(); i++) {
            seed += (int) email.charAt(i);
        }
        return seed;
    }

    // Generate a random node name consisting of two uppercase letters
    public static String generateNode(Random random) {
        return String.valueOf((char) (random.nextInt(26) + 'A')) + String.valueOf((char) (random.nextInt(26) + 'A'));
    }

    // Find the shortest route
    public static List<String> shortestRoute(List<String> nodes, List<Edge> edges, String startNode, String endNode) {
        Map<String, Double> distance = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        Set<String> unvisited = new HashSet<>();

        for (String node : nodes) {
            distance.put(node, Double.MAX_VALUE);
            previous.put(node, null);
            unvisited.add(node);
        }

        distance.put(startNode, 0.0);

        while (!unvisited.isEmpty()) {
            String currentNode = null;
            double minDistance = Double.MAX_VALUE;
            for (String node : unvisited) {
                if (distance.get(node) < minDistance) {
                    minDistance = distance.get(node);
                    currentNode = node;
                }
            }

            if (currentNode == null || currentNode.equals(endNode)) {
                break;
            }

            unvisited.remove(currentNode);

            for (Edge edge : edges) {
                if (edge.from.equals(currentNode)) {
                    double newDistance = distance.get(currentNode) + edge.cost;
                    if (newDistance < distance.get(edge.to)) {
                        distance.put(edge.to, newDistance);
                        previous.put(edge.to, currentNode);
                    }
                }
            }
        }

        List<String> shortestRoute = new ArrayList<>();
        String currentNode = endNode;
        while (currentNode != null) {
            shortestRoute.add(0, currentNode);
            currentNode = previous.get(currentNode);
        }

        return shortestRoute;
    }

    // Define a class for the edges
    public static class Edge {
        String from;
        String to;
        double cost;

        public Edge(String from, String to, double cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "{\"from\": \"" + from + "\", \"to\": \"" + to + "\", \"cost\": " + cost + "}";
        }
    }
}
/* output:
Enter email:
abc@gmail.com
Nodes: [QC, RR, SO, BC, AC, OS]
Edges: 
{"from": "QC", "to": "RR", "cost": 9.38609727183181}
{"from": "QC", "to": "SO", "cost": 7.299136707544138}
{"from": "QC", "to": "OS", "cost": 3.116459285515192}
{"from": "RR", "to": "QC", "cost": 0.9065131451942665}
{"from": "RR", "to": "SO", "cost": 3.2549902191804847}
{"from": "RR", "to": "AC", "cost": 6.057666031258084}
{"from": "RR", "to": "OS", "cost": 6.215113168529497}
{"from": "SO", "to": "BC", "cost": 7.377915766001042}
{"from": "SO", "to": "AC", "cost": 2.453479837418051}
{"from": "SO", "to": "OS", "cost": 8.632693150527508}
{"from": "BC", "to": "QC", "cost": 9.211122732652822}
{"from": "AC", "to": "QC", "cost": 1.5252394360477695}
{"from": "AC", "to": "OS", "cost": 6.680459808142761}
{"from": "OS", "to": "SO", "cost": 7.123783520758073}
{"from": "OS", "to": "AC", "cost": 6.144024325277905}
Shortest Route from C to F: [F]

*/
