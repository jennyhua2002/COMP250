package finalproject;

import java.util.ArrayList;
import java.util.Arrays;


import finalproject.system.Tile;

public class TilePriorityQ {
	// TODO level 3: Add fields that can help you implement this data type
	private ArrayList<Tile> heap;

	// TODO level 3: implement the constructor for the priority queue
	public TilePriorityQ (ArrayList<Tile> vertices) {
		heap = new ArrayList<>();
		for (Tile vertex : vertices) {
			heap.add(vertex);
			int currentIndex = heap.size() - 1;
			while (currentIndex > 0 && heap.get(parent(currentIndex)).costEstimate > heap.get(currentIndex).costEstimate) {
				swap(currentIndex, parent(currentIndex));
				currentIndex = parent(currentIndex);
			}
		}
	}
	
	// TODO level 3: implement remove min as seen in class
	public Tile removeMin() {
		if (heap.isEmpty()) {
			return null;
		}

		Tile minTile = heap.get(0);
		Tile lastTile = heap.get(heap.size() - 1);
		heap.set(0, lastTile);
		heap.remove(heap.size() - 1);
		minHeapify(0);
		return minTile;
	}
	
	// TODO level 3: implement updateKeys as described in the pdf
	public void updateKeys(Tile t, Tile newPred, double newEstimate) {
		int index = heap.indexOf(t);
		if (index != -1) {
			heap.get(index).predecessor = newPred;
			heap.get(index).costEstimate = newEstimate;
			while (index > 0 && heap.get(parent(index)).costEstimate > heap.get(index).costEstimate) {
				swap(index, parent(index));
				index = parent(index);
			}
		}
	}

	private void minHeapify(int i) {
		int left = 2 * i + 1;
		int right = 2 * i + 2;
		int smallest = i;

		if (left < heap.size() && heap.get(left).costEstimate < heap.get(smallest).costEstimate) {
			smallest = left;
		}
		if (right < heap.size() && heap.get(right).costEstimate < heap.get(smallest).costEstimate) {
			smallest = right;
		}
		if (smallest != i) {
			swap(i, smallest);
			minHeapify(smallest);
		}
	}

	private void swap(int i, int j) {
		Tile swapTemp = heap.get(i);
		heap.set(i, heap.get(j));
		heap.set(j, swapTemp);
	}

	private int parent(int index) {
		return (index - 1) / 2;
	}

	public boolean isEmpty() {
		return heap.isEmpty();
	}
	
}
