package com.group.routeplanner.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import com.group.routemap.RoutesMap;

/**
 * An implementation of Dijkstra's shortest path algorithm. It computes the shortest path (in distance)
 * to all cities in the map. The output of the algorithm is the shortest distance from the start Station 
 * to every other Station, and the shortest path from the start Station to every other.
 * <p>
 * Upon calling
 * {@link #execute(Station, Station)}, 
 * the results of the algorithm are made available by calling
 * {@link #getPredecessor(Station)}
 * and 
 * {@link #getShortestDistance(Station)}.
 * 
 * To get the shortest path between the Station <var>destination</var> and
 * the source Station after running the algorithm, one would do:
 * <pre>
 * ArrayList&lt;Station&gt; l = new ArrayList&lt;Station&gt;();
 *
 * for (Station Station = destination; Station != null; Station = engine.getPredecessor(Station))
 * {
 *     l.add(Station);
 * }
 *
 * Collections.reverse(l);
 *
 * return l;
 * </pre>
 * 
 * @see #execute(Station, Station)
 * 
 * @author Renaud Waldura &lt;renaud+tw@waldura.com&gt;
 * @version $Id: DijkstraEngine.java 2379 2007-08-23 19:06:29Z renaud $
 */

public class DijkstraEngine
{
    /**
     * Infinity value for distances.
     */
    public static final int INFINITE_DISTANCE = Integer.MAX_VALUE;

    /**
     * Some value to initialize the priority queue with.
     */
    private static final int INITIAL_CAPACITY = 8;
    
    /**
     * This comparator orders cities according to their shortest distances,
     * in ascending fashion. If two cities have the same shortest distance,
     * we compare the cities themselves.
     */
    private final Comparator<Station> shortestDistanceComparator = new Comparator<Station>()
        {
            public int compare(Station left, Station right)
            {
                // note that this trick doesn't work for huge distances, close to Integer.MAX_VALUE
                int result = getShortestDistance(left) - getShortestDistance(right);
                //return 0;
                return (result == 0) ? left.compareTo(right) : result;
            }
        };
    
    /**
     * The graph.
     */
    private final RoutesMap map;
    
    //alternate routes map
    //change alternateRoutes array to arrayList<alternatePathsDTO>
    private ArrayList<AltRouteDTO> alternateRoutes = new ArrayList<AltRouteDTO>();
    //private Map<Station, Station> alternateRoutes = new HashMap<Station,Station>();
    
    /**
     * The working set of cities, kept ordered by shortest distance.
     */
    private final PriorityQueue<Station> unsettledNodes = new PriorityQueue<Station>(INITIAL_CAPACITY, shortestDistanceComparator);
    
    /**
     * The set of cities for which the shortest distance to the source
     * has been found.
     */
    private final Set<Station> settledNodes = new HashSet<Station>();
    
    /**
     * The currently known shortest distance for all cities.
     */
    private final Map<Station, Integer> shortestDistances = new HashMap<Station, Integer>();

    /**
     * Predecessors list: maps a Station to its predecessor in the spanning tree of
     * shortest paths.
     */
    private final Map<Station, Station> predecessors = new HashMap<Station, Station>();
    
    /**
     * Constructor.
     */
    public DijkstraEngine(RoutesMap map)
    {
        this.map = map;
    }

    /**
     * Initialize all data structures used by the algorithm.
     * 
     * @param start the source node
     */
    private void init(Station start)
    {
        settledNodes.clear();
        unsettledNodes.clear();
        
        shortestDistances.clear();
        predecessors.clear();
        
        alternateRoutes.clear();
        // add source
        setShortestDistance(start, 0);
        //unsettledNodes.add(start);
    }
    
    /**
     * Run Dijkstra's shortest path algorithm on the map.
     * The results of the algorithm are available through
     * {@link #getPredecessor(Station)}
     * and 
     * {@link #getShortestDistance(Station)}
     * upon completion of this method.
     * 
     * @param start the starting Station
     * @param destination the destination Station. If this argument is <code>null</code>, the algorithm is
     * run on the entire graph, instead of being stopped as soon as the destination is reached.
     */
    public void execute(Station start, Station destination)
    {
        init(start);
        
        // the current node
        Station u;
        
        // extract the node with the shortest distance
        while ((u = unsettledNodes.poll()) != null)
        {
            assert !isSettled(u);
            
            // destination reached, stop
            if (u == destination) break;
            
            settledNodes.add(u);
            
            relaxNeighbors(u);
        }
    }

    /**
	 * Compute new shortest distance for neighboring nodes and update if a shorter
	 * distance is found.
	 * 
	 * @param u the node
	 */
    private void relaxNeighbors(Station u)
    {
        for (Station v : map.getDestinations(u))
        {
            // skip node already settled
            if (isSettled(v)) continue;
            
            int shortDist = getShortestDistance(u) + map.getDistance(u, v);
            
            if (shortDist < getShortestDistance(v))
            {
            	// assign new shortest distance and mark unsettled
                setShortestDistance(v, shortDist);
                
                // assign predecessor in shortest path
                setPredecessor(v, u);
                
                //empties alternate route list because it has found a better route
                alternateRoutes.remove(v);
            }
            else if( shortDist == getShortestDistance(v) )
            {
            	//make new AltRouteDTO
            	
            	//alternateRoutes.put(v, u);
            	alternateRoutes.add(  new AltRouteDTO(v, u) );
            }
        }        
    }

	/**
	 * Test a node.
	 * 
     * @param v the node to consider
     * 
     * @return whether the node is settled, ie. its shortest distance
     * has been found.
     */
    private boolean isSettled(Station v)
    {
        return settledNodes.contains(v);
    }

    /**
     * @return the shortest distance from the source to the given Station, or
     * {@link DijkstraEngine#INFINITE_DISTANCE} if there is no route to the destination.
     */    
    public int getShortestDistance(Station Station)
    {
        Integer d = shortestDistances.get(Station);
        return (d == null) ? INFINITE_DISTANCE : d;
    }

	/**
	 * Set the new shortest distance for the given node,
	 * and re-balance the queue according to new shortest distances.
	 * 
	 * @param Station the node to set
	 * @param distance new shortest distance value
	 */        
    private void setShortestDistance(Station Station, int distance)
    {
        /*
         * This crucial step ensures no duplicates are created in the queue
         * when an existing unsettled node is updated with a new shortest 
         * distance.
         * 
         * Note: this operation takes linear time. If performance is a concern,
         * consider using a TreeSet instead instead of a PriorityQueue. 
         * TreeSet.remove() performs in logarithmic time, but the PriorityQueue
         * is simpler. (An earlier version of this class used a TreeSet.)
         */
        unsettledNodes.remove(Station);

        /*
         * Update the shortest distance.
         */
        shortestDistances.put(Station, distance);
        
		/*
		 * Re-balance the queue according to the new shortest distance found
		 * (see the comparator the queue was initialized with).
		 */
		unsettledNodes.add(Station);        
    }
    
    /**
     * @return the Station leading to the given Station on the shortest path, or
     * <code>null</code> if there is no route to the destination.
     */
    public Station getPredecessor(Station Station)
    {
        return predecessors.get(Station);
    }
    
    private void setPredecessor(Station a, Station b)
    {
        predecessors.put(a, b);
    }

	public ArrayList<AltRouteDTO> getAlternateRoutes() 
	{
		return alternateRoutes;
	}

	public void setAlternateRoutes(ArrayList<AltRouteDTO> alternateRoutes) 
	{
		this.alternateRoutes = alternateRoutes;
	}

//	public Map<Station, Station> getAlternateRoutes() 
//	{
//		return alternateRoutes;
//	}
//
//	public void setAlternateRoutes(Map<Station, Station> alternateRoutes) 
//	{
//		this.alternateRoutes = alternateRoutes;
//	}
    
    
}
