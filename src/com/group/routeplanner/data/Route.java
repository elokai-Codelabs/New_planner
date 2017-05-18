package com.group.routeplanner.data;

import java.util.ArrayList;

/**
 * <h1>Class Route</h1>
 * 
 * Creates a route leading to the destination.
 * 
 * @version 1.0
 * @since 06-05-2014
 *
 */
public class Route 
{
	private ArrayList<Station> pairedStations = new ArrayList<Station>();
	private int count;
	/**
	 * Gets the shortest route determined by the engine.
	 * 
	 * @param engine the <CODE>engine</CODE> object will execute on the destination
	 * @param end the destination, or point of interest
	 */
	public void getDijkstraRoute(DijkstraEngine engine, Station end)
	{
		ArrayList<Station> stations = new ArrayList<Station>();
		
		stations.add(end);
		Station station=engine.getPredecessor(end);
		while(station!=null)
		{
			stations.add(station);
			station=engine.getPredecessor(station);
		}	
		
		translateRouteForView(stations);
	}
	
	/**
	 * Stores all shortest routes in an <CODE>ArrayList</CODE>
	 * which can then be used by the view.
	 * 
	 * @param stations list of current stations in map.
	 */
	public void translateRouteForView(ArrayList<Station> stations) 
	{
		String line="";
		
		for(int i=stations.size()-1; i>=0; i--)
		{
			Station currentStation = new Station(stations.get(i).getName());
			if(i!=0)
			{
				line=findCurrentLine(stations.get(i),stations.get(i-1));			
				currentStation.addLine(line);
			}
			else
				currentStation.addLine(line);
			pairedStations.add(currentStation);
		}
		countTransfer();
	}
	
	/**
	 * Gets the current line that the point in the shortest
	 * path is on.
	 * 
	 * @param current current station in the path
	 * @param next the station after the current station in the shortest path
	 * @return <CODE>String</CODE>
	 */
	public String findCurrentLine(Station current,Station next)
	{
		String currentLine="";
		for(String line:current.getLines())
			for(String switchLine:next.getLines())
				if(line.equals(switchLine))
				{
					currentLine=line; 
					break;
				}
		return currentLine;
	}
	
	/**
	 * Gets the specific transfer count of the current path.
	 * 
	 * @return <CODE>int</CODE>
	 */
	public void countTransfer()
	{			
		for(int i=0;i<pairedStations.size()-1;i++)
			if(!pairedStations.get(i).getLines().equals(pairedStations.get(i+1).getLines()))
				count++;		
	}

	public ArrayList<Station> getPairedStations() 
	{
		return pairedStations;
	}

	public void setPairedStations(ArrayList<Station> pairedStations) 
	{
		this.pairedStations = pairedStations;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}	
}