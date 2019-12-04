package map;

/**
 * 
 * Construct edge class
 *
 */
public class Edge {

	private int cost;
	private Coordinate destination;
	private Coordinate startPoint;
	
	public Edge(Coordinate startPoint,Coordinate destination, int cost) {
		this.destination = destination;
		this.startPoint = startPoint;
		this.cost = cost;
	}

	public Coordinate getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Coordinate startPoint) {
		this.startPoint = startPoint;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public Coordinate getDestination() {
		return destination;
	}

	public void setDestination(Coordinate destination) {
		this.destination = destination;
	}
	
}
