/***
 * Name: Kiran Korey
 * Stud ID: 801045301
 */

//Edge Class
//represents edges between the vertexes
class Edge {

    private Vertex from;  //the from vertex of the edge
    private Vertex to;  //the to vertex of the edge
    private float weight; //the weight of the edge
    private boolean eUp;  //check if the edge is up or not

    public Edge(Vertex from, Vertex to, float weight) {
        super();
        this.from = from;
        this.to = to;
        this.weight = weight;
        this.eUp = true; //keeping the intial status of the edge up
    }

    //getter and setter methods for all the variables

    public Vertex getFrom() {
        return from;
    }

    public void setFrom(Vertex from) {
        this.from = from;
    }

    public Vertex getTo() {
        return to;
    }

    public void setTo(Vertex to) {
        this.to = to;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public boolean isEUp() {
        return eUp;
    }

    public void setUp(boolean up) {
        this.eUp = up;
    }
}