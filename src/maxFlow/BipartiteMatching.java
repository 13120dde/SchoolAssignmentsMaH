package maxFlow;

import java.util.ArrayList;

/**This class is used to produce a maximum flow in a bipartite graph where the result is a optimal
 * matching of nodes where each node can be connected to only 1 other node. Ford-Fulkerson algorithm is
 * used  to find the maximum flow by using a depth first search on the graph.
 *
 * Created by Patrik Lind, Johan Held on 2016-11-10.
 */
public class BipartiteMatching {

    private ArrayList<ArrayList<Edge>> bipartiteGraph; //Adjacency-list graph representation
    private int V, x, y;
    private Edge[] edgeTo;
    private boolean[] marked;
    private double flow;
    private boolean quit;


    /**Instantiate this object by passing in the integer value that represents the number of nodes in
     * the bipartite-graph. The adjacency list's size will be set according to the number of Nodes in the
     * biparthite graph +2 for source and sink.
     *
     * @param V : int
     */
    public BipartiteMatching(int V, int x){
        this.V=V;
        this.x = x;
        y = V-x-2;

        bipartiteGraph = new ArrayList<>(this.V);
        for(int i = 0; i <this.V; i++){
            bipartiteGraph.add(new ArrayList<>());
        }

    }

    /**Create a new edge and add it to the graph by passing in the integer values of the edge's connecting nodes. The
     * graph representation is done with adjacency-lists which holds the edges at the list's indexes, where indexes
     * represent the nodes connected to the edge.
     *
     * @param from : int
     * @param to : int
     */
    public void addEdge(int from, int to){
        Edge edge = new Edge(from, to, 1);
        bipartiteGraph.get(from).add(edge);
        bipartiteGraph.get(to).add(edge);
    }

    /**Use this method after you have put in all v number of edges to calculate the maxflow. This method
     * will first create a flow network by adding a supersource to all X-nodes a supersink to all
     * Y-nodes in the bipartite graph. Then Ford-Fulkerson algorithm will be used to produce the results.
     *
     * @param v : int
     */
    public void runMBM(int v){
        int source = v, sink = v+1;


        for(int i = 0; i < x; i++){
            addEdge(source, i);
        }
        for(int i = x; i< x+y; i++){
            addEdge(i, sink);
        }

        fordFulkerson(source,sink);

    }


    private void fordFulkerson(int source, int sink){
        edgeTo = new Edge[V];

        while(pathFromSourcetoSink(source, sink)){
            double maxIncrease = 1;

            System.out.print("Matched Vertices: "+edgeTo[sink].otherVertex(sink)+" ");

            for(int i = sink; i!=source; i=edgeTo[i].otherVertex(i)){
                if(edgeTo[i].otherVertex(i)==source){
                    System.out.println(i +"\n");
                }
                maxIncrease = Math.min(maxIncrease, edgeTo[i].remainingCapacity(i));
            }

            for(int i = sink; i!=source; i=edgeTo[i].otherVertex(i)){
                edgeTo[i].increaseFlow(i, maxIncrease);
            }
            flow+=maxIncrease;

        }
        System.out.println("Maximum flow: "+flow);
    }

    /*Check if there is a augmenting path between the nodes passed in as argument.

     */
    private boolean pathFromSourcetoSink(int source, int sink) {
        marked = new boolean[V];
        marked[source]=true;
        quit = false;
        depthFirstSearch(source, sink);
        return marked[sink];
    }


    private void depthFirstSearch(int source, int sink) {
        if(source==sink){
            quit=true;
        }

        for(Edge e : bipartiteGraph.get(source)){
            if(quit){
                return;
            }
            int otherVertex = e.otherVertex(source);

            if(!marked[otherVertex] && e.remainingCapacity(otherVertex)>0 ){
                edgeTo[otherVertex] = e;
                marked[otherVertex] = true;
                depthFirstSearch(otherVertex, sink);
            }
        }

    }



    public class Edge{
        int fromVertex, toVertex;
        double capacity=1, flow;

        public Edge(int from, int to, double capacity){
            fromVertex=from;
            toVertex=to;
            this.capacity=capacity;
        }

        public int otherVertex(int vertex){
            if(vertex==fromVertex){
                return toVertex;
            }else{
                return fromVertex;
            }
        }



        public double flow(){
            return flow;
        }

        public double remainingCapacity(int vertex){
            if(vertex==fromVertex){
                return flow;
            }else{
                return capacity-flow;
            }
        }

        public void increaseFlow(int vertex, double amount){
            if (vertex==fromVertex){
                flow = flow-amount;
            }else{
                flow = flow+amount;
            }
        }

        public String toString(){
            return fromVertex+" - "+toVertex;
        }

    }

}
