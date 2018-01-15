package maxFlow;

import java.util.ArrayList;
import java.util.LinkedList;
public class MaximumBipartiteMatching {
    class Edge{
        int from, to;
        double capacity, flow;

        Edge(int f, int t, double capacity){
            from = f;
            to = t;
            this.capacity = capacity;
        }

        int other(int vertex){
            if(vertex==from)return to; else return from;
        }

        double capacity(){ return capacity; }

        double flow(){ return flow; }

        double residualCapacityTo(int vertex){
            if(vertex==from)return flow; else return (capacity-flow);
        }

        void increaseFlowTo(int vertex, double delta){
            if(vertex==from)flow = flow-delta;
            else flow = flow+delta;
        }

        @Override
        public String toString(){
            return from+" - "+to;
        }
    }

    ArrayList<ArrayList<Edge>> graph;
    private int V;
    private Edge[] edgeTo;
    private boolean[] marked;
    private double flow;

    MaximumBipartiteMatching(int V){
        this.V = V;
        graph = new ArrayList<>(V);
        for(int i=0;i<V;++i)graph.add(new ArrayList<>());
    }

    public void addEdge(int from, int to, double capacity){
        Edge e = new Edge(from, to, capacity);
        graph.get(from).add(e);
        graph.get(to).add(e);
    }

    public void fordFulkerson(int s, int t){
        edgeTo = new Edge[V];
        while(augmentingPathExists(s, t)){
            double maxIncrease = 1;

            System.out.print(" Matched Vertices -> "+(edgeTo[t].other(t))+" ");
            for(int i=t;i!=s;i=edgeTo[i].other(i)){
                if(edgeTo[i].other(i)==s)System.out.print((i)+"\n");
                maxIncrease = Math.min(maxIncrease, edgeTo[i].residualCapacityTo(i));
            }

            for(int i=t;i!=s;i=edgeTo[i].other(i)){
                edgeTo[i].increaseFlowTo(i, maxIncrease);
            }
            flow+=maxIncrease;
        }
        System.out.println("Max flow = "+flow);
    }

    public boolean augmentingPathExists(int s, int t){
        marked = new boolean[V];
        marked[s] = true;
        quit = false;
        System.out.print("Augmenting Path : ");
        dfs(s, t);
        return marked[t];
    }

    boolean quit;
    public void dfs(int v, int sink){
        if(v==sink)quit = true;

        for(Edge e:graph.get(v)){
            if(quit)return;
            int other = e.other(v);
            if(!marked[other] && e.residualCapacityTo(other)>0){
                System.out.print((other)+" ");
                edgeTo[other] = e;
                marked[other] = true;
                dfs(other, sink);
            }
        }
    }

    public static void main(String[] args){
        int v = 10;
        MaximumBipartiteMatching m = new MaximumBipartiteMatching(v+2);
        int source = v, sink = v+1;
        m.addEdge(0,5,1);
        m.addEdge(0,6,1);
        m.addEdge(0,9,1);
        m.addEdge(1,5,1);
        m.addEdge(1,6,1);
        m.addEdge(2,6,1);
        m.addEdge(2,7,1);
        m.addEdge(2,8,1);
        m.addEdge(3,7,1);
        m.addEdge(4,6,1);
        m.addEdge(4,7,1);

        for(int i=0;i<v/2;++i){
            m.addEdge(source, i, 1);
            m.addEdge(v/2+i, sink, 1);
        }
        m.fordFulkerson(source, sink);
    }
}