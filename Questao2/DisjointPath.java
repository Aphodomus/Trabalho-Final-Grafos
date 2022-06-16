import java.util.ArrayList;
import java.util.List;

class Edge {
    private Vertex next;
    private double weight;

    Edge() {
        this.next = null;
    }

    Edge(Vertex destination, double weight) {
        this.weight = weight;
        this.next = destination;
    }

    Edge(Vertex destination) {
        this.next = destination;
    }

    Edge(double weight) {
        this.weight = weight;
        this.next = null;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Vertex getNext() {
        return this.next;
    }

    public void setNext(Vertex destination) {
        this.next = destination;
    }
}

class Vertex {
    private int label;
    private int degree;
    private boolean visited;
    private List<Edge> edges;
    private Vertex predecessor;

    Vertex (int label) {
        this.label = label;
        this.edges = new ArrayList<Edge>();
        this.degree = 0;
        this.visited = false;
        this.predecessor = null;
    }

    public int getLabel() {
        return this.label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public List<Edge> getEdges() {
        return this.edges;
    }

    public void increaseDegree() {
        this.degree++;
    }

    public int getDegree() {
        return this.degree;
    }

    public boolean getVisited() {
        return this.visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setPredecessor(Vertex v1) {
        this.predecessor = v1;
    }

    public Vertex getPredecessor() {
        return this.predecessor;
    }
}

class Graph {
    private List<Vertex> vertexes;
    private int numberOfVertex;
    private int numberOfEdges;
    private boolean directed;
    private boolean weighted;

    Graph(boolean directed, boolean weighted) {
        this.vertexes = new ArrayList<Vertex>();
        this.numberOfVertex = 0;
        this.numberOfEdges = 0;
        this.directed = directed;
        this.weighted = weighted;
    }

    public int getnumberOfVertex() {
        return this.numberOfVertex;
    }

    public int getnumberOfEdges() {
        return this.numberOfEdges;
    }

    public List<Integer> getLabels() {
        List<Integer> v = new ArrayList<Integer>();
        
        for (int i = 0; i < this.vertexes.size(); i++) {
            v.add(this.vertexes.get(i).getLabel());
        }

        return v;
    }

    public void createVertex(int v1) {
        if (!this.getLabels().contains(v1)) {
            Vertex vertex = new Vertex(v1);
            this.vertexes.add(vertex);
            numberOfVertex++;
        }
    }

    public void connect(int v1, int v2, double peso) {
        if (this.weighted) {
            Vertex aux1 = this.searchVertex(v1);
            Vertex aux2 = this.searchVertex(v2);

            this.connectVertex(aux1, aux2, peso);
        } else {
            System.out.println("[ERROR]: Unweighted graph, it's not necessary to pass a weight");
        }
    }

    public void connect(int v1, int v2) {
        if (!this.weighted) {
            Vertex aux1 = this.searchVertex(v1);
            Vertex aux2 = this.searchVertex(v2);

            this.connectVertex(aux1, aux2);
        } else {
            System.out.println("[ERROR]: Weighted graph, it's necessary to pass a weight!");
        }
    }

    public Vertex searchVertex(int v1) {
        for (int i = 0; i < this.vertexes.size(); i++) {
            if (this.vertexes.get(i).getLabel() == v1) {
                return this.vertexes.get(i);
            }
        }

        Vertex vertex = new Vertex(v1);
        this.vertexes.add(vertex);
        numberOfVertex++;

        return vertex;
    }

    public void connectVertex(Vertex v1, Vertex v2, double peso) {
        if (this.directed) {
            if (!this.existAdjacent(v1, v2, peso)) {
                v1.getEdges().add(new Edge(v2, peso));
                v1.increaseDegree();
                this.numberOfEdges++;
            }
        } else {
            if (!this.existAdjacent(v1, v2, peso)) {
                v1.getEdges().add(new Edge(v2, peso));
                v2.getEdges().add(new Edge(v1, peso));
                v1.increaseDegree();
                this.numberOfEdges++;
            }
        }
    }

    public void connectVertex(Vertex v1, Vertex v2) {
        if (this.directed) {
            if (!this.existAdjacent(v1, v2)) {
                v1.getEdges().add(new Edge(v2));
                v1.increaseDegree();
                this.numberOfEdges++;
            }
        } else {
            if (!this.existAdjacent(v1, v2)) {
                v1.getEdges().add(new Edge(v2));
                v2.getEdges().add(new Edge(v1));
                v1.increaseDegree();
                this.numberOfEdges++;
            }
        }
    }

    public boolean existAdjacent(Vertex v1, Vertex v2) {
        for (int i = 0; i < this.vertexes.size(); i++) {
            if (this.vertexes.get(i).getLabel() == v1.getLabel()) {
                for (int j = 0; j < this.vertexes.get(i).getEdges().size(); j++) {
                    if (this.vertexes.get(i).getEdges().get(j).getNext().getLabel() == v2.getLabel()) {
                        return true;
                    }
                }
                break;
            }
        }

        return false;
    }

    public boolean existAdjacent(Vertex v1, Vertex v2, double peso) {
        for (int i = 0; i < this.vertexes.size(); i++) {
            if (this.vertexes.get(i).getLabel() == v1.getLabel()) {
                for (int j = 0; j < this.vertexes.get(i).getEdges().size(); j++) {
                    if (this.vertexes.get(i).getEdges().get(j).getNext().getLabel() == v2.getLabel()) {
                        if (this.vertexes.get(i).getEdges().get(j).getWeight() == peso) {
                            return true;
                        }
                    }
                }
                break;
            }
        }

        return false;
    }

    public void print() {
        System.out.println("\n====== Graph ======\n");

        if (this.weighted == true) {
            for (Vertex v: vertexes) {
                System.out.print("Vertex: " + v.getLabel() + " -> ");
                for (Edge e: v.getEdges()) {
                    System.out.print("[" + e.getNext().getLabel() + ", " + e.getWeight() + "]   ");
                }
                System.out.println("\n");
            }
        } else {
            for (Vertex v: vertexes) {
                System.out.print("Vertex: " + v.getLabel() + " -> ");
                for (Edge e: v.getEdges()) {
                    System.out.print("[" + e.getNext().getLabel() + "]   ");
                }
                System.out.println("\n");
            }
        }

        System.out.println("====== End ======\n");
    }

    public void resetVisited() {
        for (int i = 0; i < this.vertexes.size(); i++) {
            this.vertexes.get(i).setVisited(false);
        }
    }

    public Edge getEdge(int init, int end) {
        for (int i = 0; i < this.vertexes.size(); i++) {
            if (this.vertexes.get(i).getLabel() == init) {
                for (int j = 0; j < this.vertexes.get(i).getEdges().size(); j++) {
                    if (this.vertexes.get(i).getEdges().get(j).getNext().getLabel() == end) {
                        return this.vertexes.get(i).getEdges().get(j);
                    }
                }
            }
        }
        return null;
    }

    public void fordFulkerson(int source, int sink) {
        Vertex sinkVertex = this.searchVertex(sink);
        int numberPath = 0;
        boolean tryAgain = true;

        if (this.getLabels().contains(source) && this.getLabels().contains(sink)) {
            while (tryAgain) {
                tryAgain = false;
                List<Vertex> fila = new ArrayList<Vertex>();
                Vertex initial = this.searchVertex(source);
                initial.setVisited(true);
                fila.add(initial);

                while (fila.size() > 0) {
                    Vertex currently = fila.get(0);

                    for (int i = 0; i < currently.getEdges().size(); i++) {
                        Vertex adjacent = currently.getEdges().get(i).getNext();
                        double weight = currently.getEdges().get(i).getWeight();

                        if (weight == 1.0) {
                            if (adjacent.getLabel() == sinkVertex.getLabel()) {
                                numberPath++;
                                tryAgain = true;
                                List<Integer> path = new ArrayList<Integer>();
                                path.add(sinkVertex.getLabel());
                                adjacent.setPredecessor(currently);
                                Vertex predecessor = sinkVertex.getPredecessor();
                                Edge edge = this.getEdge(predecessor.getLabel(), sinkVertex.getLabel());
                                edge.setWeight(0);

                                while (predecessor != null) {
                                    if (predecessor.getPredecessor() != null && predecessor != null) {
                                        edge = this.getEdge(predecessor.getPredecessor().getLabel(), predecessor.getLabel());
                                        edge.setWeight(0);
                                    }
                                    path.add(predecessor.getLabel());
                                    predecessor = predecessor.getPredecessor();
                                }
                                System.out.println("Disjoint path " + (numberPath) + ":");
                                for (int p = path.size() - 1; p >= 0; p--) {
                                    System.out.print(path.get(p) + " ");
                                }
                                System.out.println();
                                this.resetVisited();
                                fila.clear();
                                break;
                            } else {
                                if (adjacent.getVisited() == false) {
                                    adjacent.setPredecessor(currently);
                                    adjacent.setVisited(true);
                                    fila.add(adjacent);
                                }
                            }
                        }
                    }
                    if (fila.size() != 0) {
                        fila.remove(0);
                    }
                }
            }
        }

        System.out.println("\nNumber of disjoint paths in the graph: " + numberPath);
    }
}

public class DisjointPath {
    public static void main(String[] args) {
        Graph graph = new Graph(true, true);

        graph.connect(1, 2, 1);
        graph.connect(1, 3, 1);
        graph.connect(1, 4, 1);
        graph.connect(2, 3, 1);
        graph.connect(3, 4, 1);
        graph.connect(3, 7, 1);
        graph.connect(4, 7, 1);
        graph.connect(5, 3, 1);
        graph.connect(5, 8, 1);
        graph.connect(6, 5, 1);
        graph.connect(6, 2, 1);
        graph.connect(6, 8, 1);
        graph.connect(7, 6, 1);
        graph.connect(7, 8, 1);

        graph.fordFulkerson(1, 8);
    }
}
