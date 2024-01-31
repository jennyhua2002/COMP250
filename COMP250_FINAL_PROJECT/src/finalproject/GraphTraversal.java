package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class GraphTraversal
{


	//TODO level 1: implement BFS traversal starting from s
	public static ArrayList<Tile> BFS(Tile s) {
		ArrayList<Tile> visited = new ArrayList<>();
		ArrayList<Tile> queue = new ArrayList<>();

		visited.add(s);
		queue.add(s);

		while (!queue.isEmpty()) {
			Tile current = queue.get(0);
			queue.remove(0);

			for (Tile neighbor : current.neighbors) {
				if (!visited.contains(neighbor) && neighbor.isWalkable()) {
					visited.add(neighbor);
					queue.add(neighbor);
				}
			}
		}
		return visited;
	}


	//TODO level 1: implement DFS traversal starting from s
	public static ArrayList<Tile> DFS(Tile s) {
		ArrayList<Tile> visited = new ArrayList<>();
		dfsHelper(s, visited);
		return visited;
	}

	private static void dfsHelper(Tile current, ArrayList<Tile> visited) {
		visited.add(current);

		for (Tile neighbor : current.neighbors) {
			if (!visited.contains(neighbor) && neighbor.isWalkable()) {
				dfsHelper(neighbor, visited);
			}
		}
	}

}  