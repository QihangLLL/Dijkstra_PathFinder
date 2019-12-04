package map;
import java.util.Comparator;

/***
 * Construct the comparator of the PriorityQueue 
 * 
 *
 */

public class DistanceComparator implements Comparator<Coordinate> {



	@Override
	public int compare(Coordinate c1, Coordinate c2) {
		return Integer.compare(c1.distance, c2.distance);
	}

}
