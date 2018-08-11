import java.util.HashMap;
import java.util.Map;

/***
 * Name : Kiran Korey
 * Student ID: 801045301
 */

//Vertex Class
// Represents a vertex in the graph.
class Vertex {
    private String name;   // Vertex name
    private Map<String, Edge> adj;    // Adjacent vertices
    private Vertex prev;   // Previous vertex on shortest path
    private float dist;   // Distance of path
    private boolean vUp;  // variable to check if the vertex is up
    private boolean visited; //to differentiate between if visited or not during DFS

    public Vertex(String name) {
        this.name = name;
        adj = new HashMap<String, Edge>();
        reset();
        vUp = true; //keeping the vertex up when added initially

    }

    //resets the vertex details like previous and the distance
    public void reset() {
        dist = Graph.INFINITY;
        prev = null;
        visited = false;
    }

    //below are the getter setter methods for all the variables

    public boolean getVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Edge> getAdj() {
        return adj;
    }

    public void setAdj(Map<String, Edge> adj) {
        this.adj = adj;
    }

    public Vertex getPrev() {
        return prev;
    }

    public void setPrev(Vertex prev) {
        this.prev = prev;
    }

    public float getDist() {
        return dist;
    }

    public void setDist(float dist) {
        this.dist = dist;
    }

    public boolean isVUp() {
        return vUp;
    }

    public void setVUp(boolean up) {
        this.vUp = up;
    }
}