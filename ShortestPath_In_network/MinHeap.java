import java.util.ArrayList;

/***
 * Name: Kiran Korey
 * Stud ID: 801045301
 */


//Implementation of Minimum priority Queue
class MinHeap {

    private ArrayList<Vertex> nodesList;
    private int heapSize;

    // MinHeap Constructor for the initialization of the variables
    public MinHeap() {
        heapSize = 0;
        nodesList = new ArrayList<Vertex>();
        nodesList.add(null);
    }

    //adding the vertex to priority queue time complexity is O(log n) where n is the size of the priority queue.
    public void addVertex(Vertex newvertex) {
        nodesList.add(newvertex);// add vertex to the nodesList
        heapSize++;              // increase the heapsize
        int tempSize = heapSize; //store new size
        while (tempSize > 1 && newvertex.getDist() < nodesList.get(tempSize / 2).getDist()) {
            nodesList.set(tempSize, nodesList.get(tempSize / 2));
            tempSize = tempSize / 2;
        }
        nodesList.set(tempSize, newvertex);
    }

    //removes the smallest element  in time complexity of O(log n) where n is the size of the priority queue.
    public Vertex removeVertex() {
        if (!isEmpty()) {
            Vertex v = nodesList.get(1);
            nodesList.set(1, nodesList.get(heapSize));
            nodesList.remove(heapSize);              // remove the element
            heapSize--;
            if (heapSize > 1) {
                // apply heap to root element
                heapify(1);
            }
            return v;
        }
        return null;
    }

    //returns queue size
    public int size() {
        return heapSize;
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    /* Time complexity is
     * O(log(size()- heapindex)) time
     * where heapindex is the index at which heapify occurs
     */
    private void heapify(int heapindex) {

        int childIndex;
        int index = heapindex;
        Vertex endVertex = nodesList.get(heapindex);
        while (2 * index <= heapSize) {
            childIndex = 2 * index; //left child index
            if (heapSize > childIndex && nodesList.get(childIndex + 1).getDist() < nodesList.get(childIndex).getDist()) {
                childIndex++;
            }
            if (nodesList.get(childIndex).getDist() >= endVertex.getDist()) {
                break;
            } else {
                nodesList.set(index, nodesList.get(childIndex));
                index = childIndex;
            }
        }
        nodesList.set(index, endVertex);
    }
}
