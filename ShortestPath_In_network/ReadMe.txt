Algorithms and Data Structures---Project 2 |
--------------------------------------------

*************************
Name: Kiran Korey		*
Student ID:801045301 	*
*************************

Program Design:
---------------
The program consists of 4 java file Graph.java, Vertex.java, Edge.java, MinHeap.java.

The Vertex.java represents a vertex and it contains details of the vertex such as the name distance,
 whether up or no and the list of edges to it.

The Edge.java represents an edge and it contains details of the vertex such as the name, whether up or down, 
from vertex and the to vertex of the edge.

The MinHeap.java is the implementation of minimum priority queue. 
It handles various functionalities like adding a vertex, removing the vertex with the minimum distance and so on.

The Graph.java is the main file where it

1. Reads a file containing edges (supplied as a command-line parameter);
  The data file is a sequence of lines of the format <source> <destination> <weight>

2. Forms the graph;

3. Repeatedly prompts for the query. The query can be one of the below
	a. print  --> it prints the  current details of the graph
	b. addedge <source> <destination> <weight>  --> adds the edge from source to destination of the given weight
	c. deleteedge <source> <destination>  --> deletes the edge from source to destination
	d. edgeup <source> <destination>  --> marks the edge up from source to destination
	e. edgedown <source> <destination> --> marks the edge from source to destinationas down
	f. vertexdown <vertexname> --> marks the vertex down
	g. vertexup <vertexbname> --> marks the vertex up
	i. path <source> <distance> --> finds the shortest distance from source to destination 
		using Dijkstra algorithm implmented using Min heap priority queue
	j. reachable --> finds all the reachable vertexes from all the vertexes
	h. quit --> quits the application



Data Structure used:
--------------------

For keeping the track of vertexes and edges to those vertexes Hashmap is used. 
Hashmap is used to keep the track vertexes in the graph and also to maintain the details of the edges in the graph.  
To sort the Hashmap while printing the reachables or the details of the graph Treemap is used as it sorts the map based on key automatically.
Arraylist is used while implementing the priority queue using the Min heap to keep the track of vertexes. 
LinkedList is used to get the details of reachable vertexes from a given vertex

Testing:
--------

The given queries.txt file is tested with the program and got the exact same result as given in the sample output.txt file with no issues. 

Programming language used: Java and version is 1.8.0_161

How to run the program?
-----------------------
Need to compile Graph.java file using the javac comamand as 
javac Graph.java 
(This in turn compiles all the other java files)

After compiling run the below command to execute the program

If queries are given one by one then use the below format
java Graph <file name along with the path>
Example: java Graph network.txt

If queries are given from a file and expected output to a file then use the below format
java Graph file_name_along_with_path <queries_file >Output_file_name
Example: java Graph network.txt < queries.txt > output.txt
