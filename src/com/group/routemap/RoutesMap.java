package com.group.routemap;

import java.util.List;

import com.group.routeplanner.data.Station;

/**
 * This interface defines the object storing the graph of all routes in the
 * system.
 * 
 * @author Renaud Waldura &lt;renaud+tw@waldura.com&gt;
 * @version $Id: RoutesMap.java 2367 2007-08-20 21:47:25Z renaud $
 */

public interface RoutesMap
{
	/**
	 * Enter a new segment in the graph.
	 */
	public void addDirectRoute(Station start, Station end, int distance);
	
	/**
	 * Get the value of a segment.
	 */
	public int getDistance(Station start, Station end);
	
	/**
	 * Get the list of cities that can be reached from the given city.
	 */
	public List<Station> getDestinations(Station u); 
	
	/**
	 * Get the list of cities that lead to the given city.
	 */
	public List<Station> getPredecessors(Station station);
	
	/**
	 * @return the transposed graph of this graph, as a new RoutesMap instance.
	 */
	public RoutesMap getInverse();
}
