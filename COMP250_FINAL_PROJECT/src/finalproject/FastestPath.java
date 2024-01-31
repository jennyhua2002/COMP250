package finalproject;

import finalproject.system.Tile;
import finalproject.tiles.MetroTile;
import java.util.ArrayList;

public class FastestPath extends PathFindingService {
    //TODO level 6: find time prioritized path
    public FastestPath(Tile start) {
        super(start);
        generateGraph();
    }

	@Override
	public void generateGraph() {
		// TODO Auto-generated method stub
        this.g = new Graph(new ArrayList<>());

        ArrayList<Tile> visited = new ArrayList<>();
        ArrayList<Tile> toVisit = new ArrayList<>();

        toVisit.add(this.source);

        while (!toVisit.isEmpty()) {
            Tile current = toVisit.get(0);
            toVisit.remove(0);

            if (!visited.contains(current)) {
                for (Tile neighbor : current.neighbors) {
                    if (!visited.contains(neighbor) && neighbor.isWalkable()) {
                        toVisit.add(neighbor);

                        if (current instanceof MetroTile && neighbor instanceof MetroTile) {
                            MetroTile metroCurrent = (MetroTile) current;
                            MetroTile metroNeighbor = (MetroTile) neighbor;

                            metroCurrent.fixMetro(metroNeighbor);

                            this.g.addEdge(current, neighbor, metroNeighbor.metroTimeCost);
                            this.g.addEdge(neighbor, current, metroCurrent.metroTimeCost);

                        } else {
                            this.g.addEdge(current, neighbor, neighbor.timeCost);
                            this.g.addEdge(neighbor, current, current.timeCost);
                        }
                    }
                }
                visited.add(current);
            }
        }
    }
}
