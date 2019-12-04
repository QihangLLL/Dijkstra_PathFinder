package map;

import java.util.ArrayList;
import java.util.Objects;

import map.Edge;
/**
 * @author Jeffrey Chan, Youhan Xia, Phuc Chu
 * RMIT Algorithms & Analysis, 2019 semester 1
 * <p>
 * Class representing a coordinate.
 */
public class Coordinate{
    /**
     * row
     */
    protected int r;

    /**
     * column
     */
    protected int c;

    /**
     * Whether coordinate is impassable or not.
     */
    protected boolean isImpassable;

    /**
     * Terrain cost.
     */
    protected int terrainCost;
    
    protected int distance ; 
    
    protected Coordinate lastCoordinate;
  
 //   protected Boolean visited;
    
    private ArrayList<Edge> edges;
    




	public int getDistance() {
		return distance;
	}


	public void setDistance(int distance) {
		this.distance = distance;
	}


	public Coordinate getLastCoordinate() {
		return lastCoordinate;
	}


	public void setLastCoordinate(Coordinate lastCoordinate) {
		this.lastCoordinate = lastCoordinate;
	}

	
    public void addEdge(Edge edge) {
    	edges.add(edge);
    }
    
	public Edge getEdge(int index){
		return edges.get(index);
	}
	
    public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}
	
	public ArrayList<Edge> getEdges(){
		return edges;
	}

	
	/**
     * Construct coordinate (r, c).
     *
     * @param r Row coordinate
     * @param c Column coordinate
     */
    public Coordinate(int r, int c) {
        this(r, c, false);
    } // end of Coordinate()


    /**
     * Construct coordinate (r,c).
     *
     * @param r Row coordinate
     * @param c Column coordinate
     * @param b Whether coordiante is impassable.
     */
    public Coordinate(int r, int c, boolean b)  {
        this.r = r;
        this.c = c;
        this.isImpassable = b;
        this.terrainCost = 1;
        this.edges = new ArrayList<Edge>();
        this.distance = Integer.MAX_VALUE;
    } // end of Coordinate()


    /**
     * Default constructor.
     */
    public Coordinate() {
        this(0, 0);
     //   this.edges = new ArrayList<Edge>();
    } // end of Coordinate()


    //
    // Getters and Setters
    //

    public int getRow() { return r; }

    public int getColumn() { return c; }


    public void setImpassable(boolean impassable) {
        isImpassable = impassable;
    }

    public boolean getImpassable() { return isImpassable; }

    public void setTerrainCost(int cost) {
        terrainCost = cost;
    }

    public int getTerrainCost() { return terrainCost; }
    


    


    //
    // Override equals(), hashCode() and toString()
    //

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Coordinate coord = (Coordinate) o;
        return r == coord.getRow() && c == coord.getColumn();
    } // end of equals()


    @Override
    public int hashCode() {
        return Objects.hash(r, c);
    } // end of hashCode()


    @Override
    public String toString() {
        return "(" + r + "," + c + "), " + isImpassable + ", " + terrainCost;
    } // end of toString()
    
    
}

// end of class Coordinate
