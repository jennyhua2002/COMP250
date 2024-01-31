package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public abstract class PathFindingService {
	Tile source;
	Graph g;
	
	public PathFindingService(Tile start) {
    	this.source = start;
    }

	public abstract void generateGraph();
    
    //TODO level 4: Implement basic dijkstra's algorithm to find a path to the final unknown destination
    public ArrayList<Tile> findPath(Tile startNode) {
        ArrayList<Tile> path = new ArrayList<>();
        ArrayList<Tile> unvisited = new ArrayList<>(g.getAllTiles());

        for (Tile tile: g.getAllTiles()){
            tile.costEstimate = Double.MAX_VALUE;
            tile.predecessor = null;
        }

        startNode.costEstimate = 0.0;
        while (!unvisited.isEmpty()){
            Tile current = null;
            double minCost = Double.MAX_VALUE;
            for (Tile tile: unvisited){
                if (tile.costEstimate < minCost){
                    minCost = tile.costEstimate;
                    current = tile;
                }
            }

            if (current.isDestination){
                while (current != null){
                    path.add(current);
                    current = current.predecessor;
                }
                break;
            }

            unvisited.remove(current);

            for (Tile neighbor: current.neighbors){
                if (neighbor.isWalkable()) {
                    double tentativeCost = current.costEstimate + this.g.getEdgeWeight(current, neighbor);
                    if (tentativeCost < neighbor.costEstimate) {
                        neighbor.costEstimate = tentativeCost;
                        neighbor.predecessor = current;
                    }
                }
            }
        }

        ArrayList<Tile> reversedpath = new ArrayList<>();
        for (int i = path.size() - 1; i>=0; i--){
            reversedpath.add(path.get(i));
        }
        return reversedpath;
    }
    
    //TODO level 5: Implement basic dijkstra's algorithm to path find to a known destination
    public ArrayList<Tile> findPath(Tile start, Tile end) {
        ArrayList<Tile> path = new ArrayList<>();
        ArrayList<Tile> unvisited = new ArrayList<>(g.getAllTiles());

        for (Tile tile: g.getAllTiles()){
            tile.costEstimate = Double.MAX_VALUE;
            tile.predecessor = null;
        }

        start.costEstimate = 0.0;
        while (!unvisited.isEmpty()){
            Tile current = null;
            double minCost = Double.MAX_VALUE;
            for (Tile tile: unvisited){
                if (tile.costEstimate < minCost){
                    minCost = tile.costEstimate;
                    current = tile;
                }
            }

            if (current.equals(end)){
                while (current != null){
                    path.add(current);
                    current = current.predecessor;
                }
                break;
            }

            unvisited.remove(current);

            for (Tile neighbor: current.neighbors){
                if (neighbor.isWalkable()) {
                    double tentativeCost = current.costEstimate + this.g.getEdgeWeight(current, neighbor);
                    if (tentativeCost < neighbor.costEstimate) {
                        neighbor.costEstimate = tentativeCost;
                        neighbor.predecessor = current;
                    }
                }
            }
        }

        ArrayList<Tile> reversedpath = new ArrayList<>();
        for (int i = path.size() - 1; i>=0; i--){
            reversedpath.add(path.get(i));
        }
        return reversedpath;
    }

    //TODO level 5: Implement basic dijkstra's algorithm to path find to the final destination passing through given waypoints
    public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints){
        ArrayList<Tile> fullpath = new ArrayList<>();

        Tile currentStart = start;

        for (Tile waypoint : waypoints) {
            ArrayList<Tile> pathSegment = findPath(currentStart, waypoint);
            if (!pathSegment.isEmpty()) {
                pathSegment.remove(pathSegment.size() - 1);
            }
            fullpath.addAll(pathSegment);
            currentStart = waypoint;
        }

        Tile destination = findDestinationTile();
        fullpath.addAll(findPath(currentStart, destination));

        return fullpath;
    }

    private Tile findDestinationTile() {
        for (Tile tile : g.getAllTiles()) {
            if (tile.isDestination) {
                return tile;
            }
        }
        return null;
    }
}

