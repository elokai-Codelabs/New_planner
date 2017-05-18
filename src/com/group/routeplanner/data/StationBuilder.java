package com.group.routeplanner.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <h1>Class StationBuilder</h1>
 * 
 * Responsible for instantiating 
 * {@link com.group.routeplanner.data.Station Station} objects
 * based on the information received from the 
 * {@link com.group.routeplanner.data.TransitXMLParser TransitXMLParser}
 * and populating a local zone of each station.
 * 
 * @version 1.0
 * @since 06-03-2014
 *
 */
public class StationBuilder {
	
	private TransitXMLParser transitXML;
	private Map<String, List<String>> allStations;
	
	/**
	 * Default Constructor.
	 * 
	 * The default behavior of this constructor:
	 * <ul>
	 * <li>
	 * <ul>
	 * <h3>Instantiates</h3>
	 * <li>{@link com.group.routeplanner.data.TransitXMLParser TransitMap}</li>
	 * </ul>
	 * <ul>
	 * <h3>Invokes</h3>
	 * <li>{@link com.group.routeplanner.data.TransitXMLParser#getNetworkData(String) getNetworkData()}</li>
	 * </ul>
	 * </li>
	 * </ul>				
	 * @throws IOException 
	 * 
	 */
	public StationBuilder()
	{
		transitXML=new TransitXMLParser();

		try {
			this.allStations=transitXML.getNetworkData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates stations using the returned <CODE>Set</CODE> 
	 * from {@link com.group.routeplanner.data.StationBuilder#buildStation(List, String, String) buildStation()}
	 * 
	 * @return <CODE>ArrayList</CODE>
	 */
	public ArrayList<Station> getStations()
	{
		ArrayList<Station> stations = new ArrayList<Station>();
		for(String line:allStations.keySet())
		{
			for(int i=0;i<allStations.get(line).size();i++)
			{	
				buildStation(stations,allStations.get(line).get(i),line);				
			}			
		}
		assignNeighbors(stations);
		return stations;
	}
	
	/**
	 * Populates a <CODE>List</CODE> of <CODE>Station</CODE>
	 * objects upon an initial account of stations,
	 * or appends the set to realize newly added stations.
	 * 
	 * @param list list of stations to be appended 
	 * @param name name of station to be built
	 * @param line line of station to be built
	 */
	public void buildStation(List<Station> list,String name,String line)
	{		
		boolean b=false;
		for(Station station:list)
		{
			if(station.getName().equals(name))	
			{
				int index=list.indexOf(station);				
				station.addLine(line);
				list.set(index, station);
				b=true;
				break;
			}
		}
		
		if(!b)
		{
			Station station=new Station(name);
			station.addLine(line);
			list.add(station);
		}
		
				
	}
	
	/**
	 * Assigns a neighbor to a <CODE>Station</CODE> object
	 * and assigns the distance apart.
	 * 
	 * @param currentStation
	 * @param neighbor
	 * @param distance
	 */
	public void assignNeighbors(Station currentStation,Station neighbor,int distance)
	{
		currentStation.addNeighbor(neighbor, distance);
	}
	
	/**
	 * Assigns all <CODE>Station</CODE> objects in a
	 * <CODE>List</CODE> to their appropriate neighbors,
	 * based on the each station's position relative to
	 * other stations in the set.
	 * 
	 * @param list list of stations in map
	 */
	public void assignNeighbors(List<Station> list)
	{
		String currentStation,nextStation;
		for (String line:allStations.keySet())
		{
			for(int i=0;i<allStations.get(line).size();i++)
			{	
				if(i+1<allStations.get(line).size())
				{
					currentStation=allStations.get(line).get(i);
					nextStation=allStations.get(line).get(i+1);	
					assignNeighbors(getStation(list,currentStation),getStation(list,nextStation),1);
					assignNeighbors(getStation(list,nextStation),getStation(list,currentStation),1);
				}				
			}
		}
	}
	 
	public Station getStation(List<Station> list,String name)
	{
		for(Station station:list)
			if(station.getName().equals(name))
				return station;
		return null;
	}
	
	public TransitXMLParser getTransitXML() {
		return transitXML;
	}

	public void setTransitXML(TransitXMLParser transitXML) {
		this.transitXML = transitXML;
	}

}
