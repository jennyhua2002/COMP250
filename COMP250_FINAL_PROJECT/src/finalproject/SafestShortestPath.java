package finalproject;


import java.util.ArrayList;
import java.util.LinkedList;

import finalproject.system.Tile;

public class SafestShortestPath extends ShortestPath {
	public int health;
	public Graph costGraph;
	public Graph damageGraph;
	public Graph aggregatedGraph;

	//TODO level 8: finish class for finding the safest shortest path with given health constraint
	public SafestShortestPath(Tile start, int health) {
		super(start);
		this.health = health;
		generateGraph();
	}


	public void generateGraph() {
		// TODO Auto-generated method stub
		super.generateGraph();

		costGraph = new Graph(new ArrayList<>());
		damageGraph = new Graph(new ArrayList<>());
		aggregatedGraph = new Graph(new ArrayList<>());

		ArrayList<Tile> allTiles = this.g.getAllTiles();

		for (Tile tile : allTiles) {
			if (tile.isWalkable()) {
				for (Tile neighbor : this.g.getNeighbors(tile)) {
					if (neighbor.isWalkable()) {
						double distanceCost = neighbor.distanceCost;
						double damageCost = neighbor.damageCost;

						costGraph.addEdge(tile, neighbor, distanceCost);
						damageGraph.addEdge(tile, neighbor, damageCost);
						aggregatedGraph.addEdge(tile, neighbor, damageCost);
					}
				}
			}
		}
	}

	@Override
	public ArrayList<Tile> findPath(Tile startNode, LinkedList<Tile> waypoints) {
		// Step 1: Use costGraph to find the shortest distance path
		this.g = costGraph;
		ArrayList<Tile> pc = super.findPath(startNode, waypoints);
		double pcDistanceTotal = getTotalDistanceCost(pc);

		if (pcDistanceTotal < health) {
			return pc;
		}

		// Step 2: Use damageGraph to find the path with least damage
		this.g = damageGraph;
		ArrayList<Tile> pd = super.findPath(startNode, waypoints);
		double pdDamageTotal = getTotalDamageCost(pd);

		if (pdDamageTotal > health) {
			return null;
		}

		// Step 3: Initialize λ and update aggregatedGraph
		double lambda = computeLambda(pc, pd);
		updateAggregatedGraph(lambda);

		ArrayList<Tile> pr;
		do {
			// Step 4: Use aggregatedGraph to find the path with least aggregate cost
			this.g = aggregatedGraph;
			pr = super.findPath(startNode, waypoints);
			double prAggregateCost = getTotalAggregateCost(pr, lambda);

			if (getTotalAggregateCost(pc, lambda) == prAggregateCost) {
				return pd;
			} else if (getTotalDamageCost(pr) <= health) {
				pd = pr;
			} else {
				pc = pr;
			}

			lambda = computeLambda(pc, pd);
			updateAggregatedGraph(lambda);

		} while (true);
	}


	// Helper method to calculate the total damage cost for a path
	private double getTotalDamageCost(ArrayList<Tile> path) {
		double totalDamage = 0;
		for (int i = 0; i < path.size() - 1; i++) {
			totalDamage += damageGraph.getEdgeWeight(path.get(i), path.get(i + 1));
		}
		return totalDamage;
	}
	// Helper method to calculate the total distance cost for a path
	private double getTotalDistanceCost(ArrayList<Tile> path) {
		double totalDistance = 0;
		for (int i = 0; i < path.size() - 1; i++) {
			totalDistance += costGraph.getEdgeWeight(path.get(i), path.get(i + 1));
		}
		return totalDistance;
	}

	// Helper method to compute the lambda (λ) value used in the LARAC algorithm
	private double computeLambda(ArrayList<Tile> pc, ArrayList<Tile> pd) {
		double cPc = getTotalDistanceCost(pc);
		double cPd = getTotalDistanceCost(pd);
		double dPc = getTotalDamageCost(pc);
		double dPd = getTotalDamageCost(pd);
		return (cPc - cPd) / (dPd - dPc);
	}

	// Helper method to update the aggregatedGraph based on the lambda (λ) value
	private void updateAggregatedGraph(double lambda) {
		for (Graph.Edge edge : aggregatedGraph.getAllEdges()) {
			double c = costGraph.getEdgeWeight(edge.getStart(), edge.getEnd());
			double d = damageGraph.getEdgeWeight(edge.getStart(), edge.getEnd());
			double newWeight = c + lambda * d;
			aggregatedGraph.updateEdgeWeight(edge.getStart(), edge.getEnd(), newWeight);
		}
	}

	// Helper method to calculate the total aggregated cost for a path
	private double getTotalAggregateCost(ArrayList<Tile> path, double lambda) {
		double totalCost = 0;
		for (int i = 0; i < path.size() - 1; i++) {
			totalCost += aggregatedGraph.getEdgeWeight(path.get(i), path.get(i + 1));
		}
		return totalCost;
	}
}
