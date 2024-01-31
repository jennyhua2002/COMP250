package finalproject;

import java.util.ArrayList;
import java.util.HashSet;

import finalproject.system.Tile;
import finalproject.system.TileType;
import finalproject.tiles.*;

public class Graph {
	// TODO level 2: Add fields that can help you implement this data type
    private ArrayList<Tile> vertices;
    private ArrayList<Edge> edges;

    // TODO level 2: initialize and assign all variables inside the constructor
	public Graph(ArrayList<Tile> vertices) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();
	}
	
    // TODO level 2: add an edge to the graph
    public void addEdge(Tile origin, Tile destination, double weight){
        if (!vertices.contains(origin)) {
            vertices.add(origin);
        }
        if (!vertices.contains(destination)) {
            vertices.add(destination);
        }
        Edge newEdge = new Edge(origin, destination, weight);
        edges.add(newEdge);
    }
    
    // TODO level 2: return a list of all edges in the graph
	public ArrayList<Edge> getAllEdges() {
        return new ArrayList<>(edges);
	}
  
	// TODO level 2: return list of tiles adjacent to t
	public ArrayList<Tile> getNeighbors(Tile t) {
        ArrayList<Tile> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.origin.equals(t)) {
                neighbors.add(edge.destination);
            }
        }
        return neighbors;
    }
	
	// TODO level 2: return total cost for the input path
	public double computePathCost(ArrayList<Tile> path) {
        double totalCost = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            Tile start = path.get(i);
            Tile end = path.get(i + 1);
            for (Edge edge : edges) {
                if (edge.origin.equals(start) && edge.destination.equals(end)) {
                    totalCost += edge.weight;
                    break;
                }
            }
        }
        return totalCost;
	}
	
   
    public static class Edge{
    	Tile origin;
    	Tile destination;
    	double weight;

        // TODO level 2: initialize appropriate fields
        public Edge(Tile s, Tile d, double cost){
            this.origin = s;
            this.destination = d;
            this.weight = cost;
        }
        
        // TODO level 2: getter function 1
        public Tile getStart(){
            return origin;
        }

        
        // TODO level 2: getter function 2
        public Tile getEnd() {
            return destination;
        }
        
    }

    public ArrayList<Tile> getAllTiles() {
        return new ArrayList<>(vertices);
    }

    public double getEdgeWeight(Tile start, Tile end) {
        for (Edge edge : edges) {
            if (edge.origin.equals(start) && edge.destination.equals(end)) {
                return edge.weight;
            }
        }
        return Double.MAX_VALUE;
    }

    public void updateEdgeWeight(Tile start, Tile end, double newWeight) {
        for (Edge edge : edges) {
            if (edge.origin.equals(start) && edge.destination.equals(end)) {
                edge.weight = newWeight;
                return;
            }
        }
    }

}