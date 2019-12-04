package map;

import java.util.List;

/**
 * 
 * Construct Route class for task D
 *
 */

public class Route {
	Coordinate ori;
	Coordinate des;
	Coordinate wayPointer ;
	int distance;
	public Route(Coordinate ori, Coordinate des, int distance) {
		this.ori = ori;
		this.des = des;
		this.distance = distance;
	}
	
	
	public int getDistance() {
		return distance;
	}


	public void setDistance(int distance) {
		this.distance = distance;
	}


	public Coordinate getWayPointer() {
		return wayPointer;
	}
	public void setWayPointer(Coordinate wayPointer) {
		this.wayPointer = wayPointer;
	}
	public Coordinate getOri() {
		return ori;
	}
	public void setOri(Coordinate ori) {
		this.ori = ori;
	}
	public Coordinate getDes() {
		return des;
	}
	public void setDes(Coordinate des) {
		this.des = des;
	}

}
