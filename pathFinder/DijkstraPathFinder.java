package pathFinder;

import map.*;

import java.util.*;

public class DijkstraPathFinder implements PathFinder {

	List<Coordinate> oriCells;
	List<Coordinate> desCells;
	List<Coordinate> wayPointerCells;
	List<Coordinate> currentWayPointerCells;
	int columnSize;
	int rowSize;
	int bestWay;
	PathMap map;
	Coordinate all[][];
	List<Route> routeList;
	protected Coordinate reset[][];
	private PriorityQueue<Coordinate> openList;

	public DijkstraPathFinder(PathMap map) {

		this.oriCells = map.originCells;
		this.desCells = map.destCells;
		this.columnSize = map.sizeC;
		this.rowSize = map.sizeR;
		this.map = map;
		this.all = map.cells;
		this.reset = map.cells;
		this.wayPointerCells = map.waypointCells;
		this.currentWayPointerCells = map.waypointCells;
		this.routeList = new ArrayList<Route>();
		DistanceComparator comparator = new DistanceComparator();
		openList = new PriorityQueue<Coordinate>(comparator);

	} // end of DijkstraPathFinder()

	@Override
	public List<Coordinate> findPath() {

		List<Coordinate> path = new ArrayList<Coordinate>();
		List<Coordinate> currentPath = new ArrayList<Coordinate>();
		//Add edges to all coordinates
		all = addEdge(all);
		bestWay = Integer.MAX_VALUE;
		
//Start searching for the shortest path	
		for (int i = 0; i < oriCells.size(); i++) {
			for (int j = 0; j < desCells.size(); j++) {
				// If these is no wayPointer - task A - task B -Task C
				if (wayPointerCells.size() == 0) {
					currentPath = getShortestPath(oriCells.get(i), desCells.get(j));
					if (currentPath != null) {
						path = currentPath;
					}
				}
				// Task D
				else {
					wayPointerLoop(oriCells.get(i), currentWayPointerCells);
					currentWayPointerCells.add(desCells.get(j));
					while (currentWayPointerCells.size() > 1) {
						Coordinate fristCooridnate = currentWayPointerCells.get(0);
						currentWayPointerCells.remove(0);
						wayPointerLoop(fristCooridnate, currentWayPointerCells);
					}
					currentPath = wayPointerCalculate(oriCells.get(j), desCells.get(j), routeList);
					if (currentPath != null) {
						path = currentPath;
					}
					// Reset currentWayPointerCells and routeList
					currentWayPointerCells = wayPointerCells;
					routeList = new ArrayList<Route>();
				}
				// Reset routeList;

			}
		}
		//Reverse the path list to get the path in right order(Start-End)
		Collections.reverse(path);

		return path;
	} // end of findPath()

	/**
	 * 
	 * @param all
	 * The method to add edges to all coordinates in the mao
	 */
	public Coordinate[][] addEdge(Coordinate[][] all) {
		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < columnSize; j++) {

				if (all[i][j].getImpassable() == false) {
					// top coordinate
					if (i + 1 < rowSize && all[i + 1][j].getImpassable() == false) {
						all[i][j].addEdge(new Edge(all[i][j], all[i + 1][j], all[i + 1][j].getTerrainCost()));
					}
					// bottom
					if (i - 1 >= 0 && all[i - 1][j].getImpassable() == false) {
						all[i][j].addEdge(new Edge(all[i][j], all[i - 1][j], all[i - 1][j].getTerrainCost()));
					}
					// left
					if (j - 1 >= 0 && all[i][j - 1].getImpassable() == false) {
						all[i][j].addEdge(new Edge(all[i][j], all[i][j - 1], all[i][j - 1].getTerrainCost()));
					}
					// right
					if (j + 1 < columnSize && all[i][j + 1].getImpassable() == false) {
						all[i][j].addEdge(new Edge(all[i][j], all[i][j + 1], all[i][j + 1].getTerrainCost()));

					}
				}

			}
		}
		return all;
	}
	
	/**
	 * The method to get shortest path
	 * @param oriCoordinate
	 * @param desCoordinate
	 * @return path
	 */

	public List<Coordinate> getShortestPath(Coordinate oriCoordinate, Coordinate desCoordinate) {

		List<Coordinate> path = new ArrayList<Coordinate>();

		int oriCol = oriCoordinate.getColumn();
		int oriRow = oriCoordinate.getRow();
		int desCol = desCoordinate.getColumn();
		int desRow = desCoordinate.getRow();
		Coordinate nextCoordinate;
		Coordinate current;
		// set and add start point, set distance of start
		all[oriRow][oriCol].setDistance(0);
		openList.add(all[oriRow][oriCol]);

		// need to do, nextCoordinate != all[][]
		// get the nearest node and set last coordinate
		while (!openList.isEmpty()) {
			current = openList.poll();
			for (Edge e : current.getEdges()) {
				nextCoordinate = e.getDestination();
				int distanceFromStart = current.getDistance() + e.getCost();
				if (distanceFromStart <= nextCoordinate.getDistance()) {
					openList.remove(nextCoordinate);
					nextCoordinate.setDistance(distanceFromStart);
					nextCoordinate.setTerrainCost(distanceFromStart);
					nextCoordinate.setLastCoordinate(current);
					openList.add(nextCoordinate);
				}
			}
		}
		all[oriRow][oriCol].setDistance(Integer.MAX_VALUE);

		// Set destination point
		// And get all last coordinate to the start
		// Then add to the path list
		current = all[desRow][desCol];
		while (current.getLastCoordinate() != null) {
			if (!current.equals(all[desRow][desCol])) {
				path.add(current);
				current = current.getLastCoordinate();
			} else {
				current = current.getLastCoordinate();
			}
		}
		// Add this route to route list (for taskD)
		routeList.add(new Route(oriCoordinate, desCoordinate, all[desRow][desCol].getDistance()));
		// if this way is shorter than the current best way
		if (bestWay > all[desRow][desCol].getDistance()) {
			bestWay = all[desRow][desCol].getDistance();
		}
		// if this way is shorter than the current best way
		else {
			path = null;
		}

		// Reset all coordinates after one search
		// all = reset;
		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < columnSize; j++) {
				if (all[i][j].getImpassable() == false) {
					all[i][j].setDistance(Integer.MAX_VALUE);
					all[i][j].setLastCoordinate(null);
				}
			}
		}

		return path;
	}
	
	/**
	 * The wayPointer loop
	 * @param coordinate
	 * @param currentWayPointerCells
	 */

	public void wayPointerLoop(Coordinate coordinate, List<Coordinate> currentWayPointerCells) {
		for (int i = 0; i < currentWayPointerCells.size(); i++) {
			getShortestPath(coordinate, currentWayPointerCells.get(i));
		}
	}

	/**
	 * The method to get the shortest path including wayPointers
	 * @param ori
	 * @param des
	 * @param myRouteList
	 * @return
	 */
	public List<Coordinate> wayPointerCalculate(Coordinate ori, Coordinate des, List<Route> myRouteList) {
		List<Coordinate> myPath = new ArrayList<Coordinate>();
		List<Route> myCurrentPath = new ArrayList<Route>();
		List<Route> myBestPath = new ArrayList<Route>();
		List<Coordinate> existCoordinate = new ArrayList<Coordinate>();
		Coordinate startCoordinate;
		int totalDistance = 0;
		int bestDistance = 0;
		// for every route ori coordinate is input ori(for each start point)
		for (int i = 0; i < myRouteList.size(); i++) {
			if (myRouteList.get(i).equals(ori)) {
				startCoordinate = myRouteList.get(i).getDes();
				totalDistance = myRouteList.get(i).getDistance();
				existCoordinate.add(myRouteList.get(i).getDes());
				myCurrentPath.add(myRouteList.get(i));
				while (existCoordinate.size() != wayPointerCells.size() - 1) {
					for (int j = 0; j < myRouteList.size(); i++) {

					}
				}
			}
		}
		return myPath;
	}

	@Override
	public int coordinatesExplored() {
		// TODO: Implement (optional)

		// placeholder
		return 0;
	} // end of cellsExplored()a

} // end of class DijsktraPathFinder
