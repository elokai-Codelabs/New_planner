package com.group.routeplanner.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <h1>Class Station</h1>
 * 
 * Holds a name and what train line it's on,
 * and also its direct neighbors.
 * 
 * @version 1.0
 * @since 06-02-2014
 */
public class Station 
{
	
	private Map<Station, Integer> neighbors = new HashMap<Station, Integer>();
	private String name;
	private Set<String> lines = new HashSet<String>();
	
	public Station(String name)
	{
		this.name = name;
	}
	
	public void addLine(String line)
	{
		lines.add(line);
	}
	
	public Set<String> getLines()
	{
		return this.lines;
	}
	
	public void addNeighbor(Station neighbor, int distance) 
	{
		neighbors.put(neighbor, distance);
	}
	
	public Map<Station, Integer> getNeighbors()
	{
		return this.neighbors;
	}
	
	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}	
	
	public String toString(){
		return name+" "+lines+" ";
	}
	
	public String getCurrentLine(){		
		return (String) lines.iterator().next();
	}
	
	/**
	 * Compares two stations on the assumption that
	 * they are equidistant from a point of interest,
	 * by naming conventions.
	 * 
	 * @param otherStation the other station that is the same distance from the current station
	 * @return <CODE>int</CODE>
	 */
	public int compareTo(Station otherStation){
		return this.getName().compareTo(otherStation.getName());
	}
	
	
	
}
