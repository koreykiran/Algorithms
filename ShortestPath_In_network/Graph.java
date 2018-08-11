import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/***
 * Name: Kiran Korey
 * Stud ID: 801045301
 */

// Used to signal violations of preconditions for various shortest path algorithms.
class GraphException extends RuntimeException {
    public GraphException(String name) {
        super(name);
    }
}

//Graph Class
public class Graph {
    public static final int INFINITY = Integer.MAX_VALUE;
    private Map<String, Vertex> vertexMap = new HashMap<String, Vertex>(); //keeps the track of the vertices in the graph

    public Map<String, Vertex> getVertexMap() {
        return vertexMap;
    }

    public void setVertexMap(Map<String, Vertex> vertexMap) {
        this.vertexMap = vertexMap;
    }

    //adds the edge to the graph
    public void addEdge(String sourceName, String destName, float weight) {
        Vertex src = getVertex(sourceName);
        Vertex dest = getVertex(destName);
        src.getAdj().put(destName, new Edge(src, dest, weight));
    }

    //deletes a edge from the graph
    public void deleteEdge(String sourceName, String destName) {
        if (vertexMap.get(sourceName) != null) {
            vertexMap.get(sourceName).getAdj().remove(destName);
        } else
            System.out.println("Invalid edge to remove");
    }

    //brings the edge down
    public void edgeDown(String sourceName, String destName) {
        if (vertexMap.get(sourceName) != null) {
            if (vertexMap.get(sourceName).getAdj().get(destName) != null) {
                vertexMap.get(sourceName).getAdj().get(destName).setUp(false);
            } else
                System.out.println("Invalid edge to make it down");
        } else
            System.out.println("Invalid edge to make it down");
    }

    //brings the edge up
    public void edgeUp(String sourceName, String destName) {
        if (vertexMap.get(sourceName) != null) {
            if (vertexMap.get(sourceName).getAdj().get(destName) != null) {
                vertexMap.get(sourceName).getAdj().get(destName).setUp(true);
            } else
                System.out.println("Invalid edge to make it up");
        } else
            System.out.println("Invalid edge to make it up");
    }

    //brings the vertex down
    public void vertexDown(String sourceName) {
        if (vertexMap.get(sourceName) != null) {
            vertexMap.get(sourceName).setVUp(false);
        } else
            System.out.println("Invalid vertex to make it down");
    }

    //brings the vertex up
    public void vertexUp(String sourceName) {
        if (vertexMap.get(sourceName) != null) {
            vertexMap.get(sourceName).setVUp(true);
        } else
            System.out.println("Invalid vertex to make it up");
    }

    //implementation of the Dijkstra algorithm
    public void dijkstra(String source) {
        clearAll();

        Vertex startVertex = vertexMap.get(source); //initial vertex of the path is taken as start vertex
        if (startVertex == null) {
            throw new NoSuchElementException("Start vertex not found");
        }

        //Implemented Priority Queue (Min Heap)
        MinHeap minHeap = new MinHeap(); //creates the object of Minimum Priority Queue
        startVertex.setDist(0);//initial distance
        minHeap.addVertex(startVertex); //adds the vertex to the priority queue

        //Executes until queue is not empty
        while (!minHeap.isEmpty()) {
            Vertex vertex = minHeap.removeVertex(); //gets the vertex of minimum distance from the minimum priority queue

            if (vertex.isVUp()) //vertex is considered in the path only if the vertex is up
            {
                //test for up,down
                // for the vertex in each edge of the vertex, calculate distance
                for (Edge edge : vertex.getAdj().values()) {
                    if (edge.isEUp()) //considers the edge for the path only if the edge is up
                    {
                        Vertex v = edge.getTo();
                        float input = edge.getWeight();
                        // if distance is lesser than previously stored value, update dist and prev values
                        if (v.getDist() > vertex.getDist() + input) //checks if the path needs tobe updated in the dijkstra
                        {
                            v.setDist(vertex.getDist() + input);
                            v.setPrev(vertex);
                            minHeap.addVertex(v);// add the vertex in the priority queue
                        }
                    }
                }
            }
        }
    }


    // Finds the all the reachable vertexes from all the vertexes
    /*
     * Here DFS is used to get the details of all the reachable vertexes from every vertex
     * For each of the vertex clearAll function is called to clear the previous, distance and other details
     * and for each vertex DFS function is called taking it as source vertex and it gets all the reachable vertexes from that vertex
     *
     * As we know that the efficiency of the DFS is O(V+E) where V is the number of vertexes and E is the number of Edges
     * and we are calling this function for V times so the efficiency of this function is
     * O V(V+E)
     * */
    public void reachable() {
        // sort the vertices
        Map<String, Vertex> treeMapVertex = new TreeMap<String, Vertex>(vertexMap);

        // for every vertex DepthFirstSearch algorithm is applied by DFS method is called to get all the reachable vertices
        for (Vertex currentVertex : treeMapVertex.values()) {
            clearAll();
            // list of reachable vertices
            List<String> reachableVertices = new LinkedList<String>();

            if (currentVertex.isVUp()) {
                System.out.print(currentVertex.getName());
                System.out.println();
                // DFS
                DFS(currentVertex, reachableVertices);
            }
            // sort the reachable vertices
            Collections.sort(reachableVertices);
            for (String vertex : reachableVertices) {
                System.out.println("  " + vertex);
            }
        }
    }

    // DeapthFirstSearch begins for every vertex DFS method is called to get all the reachable vertices
    private void DFS(Vertex begin, List<String> reachableVertices) {
        // mark the vertex visited
        begin.setVisited(true);
        // for each edge, get the vertex and add it to list if not visited
        for (Edge edge : begin.getAdj().values()) {
            if (edge.isEUp()) {
                Vertex vertex = edge.getTo();
                if (vertex.isVUp()) {
                    if (vertex.getVisited() == false) {
                        reachableVertices.add(vertex.getName());
                        // recursive call to newly discovered vertex
                        DFS(vertex, reachableVertices);
                    }
                }
            }
        }
    }

    /**
     * Driver routine to print total distance.
     * It calls recursive routine to print shortest path to
     * destNode after a shortest path algorithm has run.
     */
    public void printPath(String destName) {
        Vertex w = vertexMap.get(destName);
        DecimalFormat df = new DecimalFormat("#0.##");
        if (w == null)
            throw new NoSuchElementException("Destination vertex not found");
        else if (w.getDist() == INFINITY)
            System.out.println(destName + " is unreachable");
        else {
            printPath(w);
            System.out.print(" " + df.format(w.getDist()));
            System.out.println();
        }
    }

    /**
     * Recursive routine to print shortest path to dest
     * after running shortest path algorithm.
     * The path is known to exist.
     */
    private void printPath(Vertex dest) {
        if (dest.getPrev() != null) {
            printPath(dest.getPrev());
            System.out.print(" ");
        }
        System.out.print(dest.getName());
    }

    /**
     * If vertexName is not present, add it to vertexMap.
     * In either case, return the Vertex.
     */
    private Vertex getVertex(String vertexName) {
        Vertex v = vertexMap.get(vertexName);
        if (v == null) {
            v = new Vertex(vertexName);
            vertexMap.put(vertexName, v);
        }
        return v;
    }


    /**
     * Initializes the vertex output info prior to running
     * any shortest path algorithm.
     */
    private void clearAll() {
        for (Vertex v : vertexMap.values())
            v.reset();
    }

    //prints the current graph details
    public void printGraph() {

        Map<String, Vertex> treeMapVertex = new TreeMap<String, Vertex>(vertexMap);
        DecimalFormat df = new DecimalFormat("#0.##");
        for (Vertex CurrentVertex : treeMapVertex.values()) {

            if (CurrentVertex.isVUp())
                System.out.println(CurrentVertex.getName());
            else {
                System.out.println(CurrentVertex.getName() + " DOWN");
            }

            Map<String, Edge> treeMapEdge = new TreeMap<String, Edge>(CurrentVertex.getAdj());

            for (Edge eachEdge : treeMapEdge.values()) {
                if (eachEdge.isEUp()) //checks whether edge is up
                {
                    System.out.println("  " + eachEdge.getTo().getName() + " " + df.format(eachEdge.getWeight()));
                } else {
                    System.out.println("  " + eachEdge.getTo().getName() + " " + df.format(eachEdge.getWeight()) + " DOWN");
                }
            }
        }
    }

    /**
     * A main routine that:
     * 1. Reads a file containing edges (supplied as a command-line parameter);
     * 2. Forms the graph;
     * <source> <destination> <weight>
     */
    public static void main(String[] args) {
        Graph graph = new Graph();
        try {
            FileReader fin = new FileReader(args[0]);
            Scanner graphFile = new Scanner(fin);

            // Read the edges and insert
            String line = null;
            StringTokenizer st = null;
            String source = null;
            String dest = null;
            float weight;

            while (graphFile.hasNextLine()) {
                line = graphFile.nextLine();
                st = new StringTokenizer(line);

                try {
                    if (st.countTokens() != 3) {
                        System.err.println("Skipping ill-formatted line " + line);
                        continue;
                    }
                    source = st.nextToken();
                    dest = st.nextToken();
                    weight = Float.parseFloat(st.nextToken());

                    graph.addEdge(source, dest, weight);
                    graph.addEdge(dest, source, weight);
                } catch (NumberFormatException e) {
                    System.err.println("Skipping ill-formatted line " + line);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        //Processes the requests and keeps waiting for new request
        graph.processRequest();

    }

    /**
     * Repeatedly prompts for the query. The query can be one of the below
     * a. print  --> it prints the  current details of the graph
     * b. addedge <source> <destination> <weight>  --> adds the edge from source to destination of the given weight
     * c. deleteedge <source> <destination>  --> deletes the edge from source to destination
     * d. edgeup <source> <destination>  --> marks the edge up from source to destination
     * e. edgedown <source> <destination> --> marks the edge from source to destinationas down
     * f. vertexdown <vertexname> --> marks the vertex down
     * g. vertexup <vertexbname> --> marks the vertex up
     * i. path <source> <distance> --> finds the shortest distance from source to destination using Dijkstra algorithm
     * implemented using Min heap priority queue
     * j. reachable --> finds all the reachable vertexes from all the vertexes
     * h. quit --> quits the application
     * The input file is a sequence of lines of the format
     */
    public void processRequest() {
        Scanner in = new Scanner(System.in);
        String input = null;
        while (in.hasNextLine()) {
            //receives the query
            input = in.nextLine();

            //splits the query by space to get the functionality to perform
            String inputSplit[] = input.split(" ");

            switch (inputSplit[0].toLowerCase()) {
                case "print":

                    this.printGraph();
                    System.out.println();
                    break;

                case "addedge":

                    if (inputSplit.length == 4) {
                        this.addEdge(inputSplit[1], inputSplit[2], Float.parseFloat(inputSplit[3]));
                    } else
                        System.out.println("Invalid addEdge Command");

                    break;

                case "deleteedge":

                    if (inputSplit.length == 3) {
                        this.deleteEdge(inputSplit[1], inputSplit[2]);
                    } else
                        System.out.println("Invalid deleteEdge Command");

                    break;

                case "edgedown":
                    if (inputSplit.length == 3) {
                        this.edgeDown(inputSplit[1], inputSplit[2]);
                    } else
                        System.out.println("Invalid edgeDown Command");

                    break;
                case "edgeup":

                    if (inputSplit.length == 3) {
                        this.edgeUp(inputSplit[1], inputSplit[2]);
                    } else
                        System.out.println("Invalid edgeUp Command");

                    break;

                case "vertexdown":

                    if (inputSplit.length == 2) {
                        this.vertexDown(inputSplit[1]);
                    } else
                        System.out.println("Invalid vertexDown Command");

                    break;

                case "vertexup":

                    if (inputSplit.length == 2) {
                        this.vertexUp(inputSplit[1]);
                    } else
                        System.out.println("Invalid vertexup Command");

                    break;

                case "path":

                    if (inputSplit.length == 3) {
                        String source = inputSplit[1];
                        String destination = inputSplit[2];
                        this.dijkstra(source);
                        // print the paths
                        this.printPath(destination);
                        System.out.println();
                    } else
                        System.out.println("Invalid path Command");

                    break;

                case "reachable":

                    if (inputSplit.length == 1) {
                        this.reachable();
                        System.out.println();
                    } else
                        System.out.println("Invalid reachable Command");

                    break;

                case "quit":

                    if (inputSplit.length == 1) {
                        System.exit(0);
                    } else
                        System.out.println("Invalid quit Command");

                    break;

                default:
                    System.out.println("Invalid query");
                    break;
            }
        }
    }
}
