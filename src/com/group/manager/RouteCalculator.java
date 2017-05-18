package com.group.manager;

import java.util.ArrayList;

import com.group.routemap.RoutesMap;
import com.group.routemap.TransitMap;
import com.group.routeplanner.data.AltRouteDTO;
import com.group.routeplanner.data.DijkstraEngine;
import com.group.routeplanner.data.Route;
import com.group.routeplanner.data.Station;
import com.group.routeplanner.data.StationBuilder;

public class RouteCalculator {
	
	private RoutesMap transitMap;
	private StationBuilder stationBuilder;
	private DijkstraEngine engine;
	private ArrayList<Station> stations;
		
	public RouteCalculator(){
		transitMap = new TransitMap();		
		stationBuilder = new StationBuilder();		
		setUpMap();
	}

	private void setUpMap() {	
		stations=stationBuilder.getStations();
		((TransitMap) transitMap).setStations(stations);
		engine = new DijkstraEngine(transitMap);
	}
	
	//alternate routes that have the same shortest distance
	public ArrayList<Route> getRoutesForView(String start, String end){
		Station startStation=stationBuilder.getStation(stations,start);
		Station endStation=stationBuilder.getStation(stations,end);
		engine.execute(startStation, endStation);
		ArrayList<Route> allRoutes = new ArrayList<Route>();		
		createRoutes(endStation, new ArrayList<Station>(), allRoutes);
		bubbleSort(allRoutes);
		return allRoutes;
	}
	
	
	public void bubbleSort(ArrayList<Route> routes)
    {
           Route tmp;
           int out,in;          
           for(out=routes.size()-1;out>0;out--)
                  for(in=0;in<out;in++)
                        if(routes.get(in).getCount()>routes.get(in+1).getCount())
                        {
                               tmp=routes.get(in);
                               routes.set(in,routes.get(in+1));
                               routes.set(in+1,tmp);
                        }
    }


	
	private void createRoutes(Station end, ArrayList<Station> currentList,
			ArrayList<Route> allRoutesList) {
		Station station = end;

		while (station != null) {

			for (AltRouteDTO entry : engine.getAlternateRoutes()) {

				if (entry.getStart() == station) {

					Station station2 = entry.getEnd();

					ArrayList<Station> tempCurrentList = new ArrayList<Station>();
					for (Station tempStation : currentList)
						tempCurrentList.add(tempStation);

					tempCurrentList.add(station);

					createRoutes(station2, tempCurrentList, allRoutesList);
				}
			}

			currentList.add(station);
			station = engine.getPredecessor(station);
		}

		Route newPath = new Route();
		newPath.translateRouteForView(currentList);
		allRoutesList.add(newPath);
	}
	
	
	public RoutesMap getTransitMap() {
		return transitMap;
	}

	public void setTransitMap(RoutesMap transitMap) {
		this.transitMap = transitMap;
	}

	public StationBuilder getStationBuilder() {
		return stationBuilder;
	}

	public void setStationBuilder(StationBuilder stationBuilder) {
		this.stationBuilder = stationBuilder;
	}

	public DijkstraEngine getEngine() {
		return engine;
	}

	public void setEngine(DijkstraEngine engine) {
		this.engine = engine;
	}

	public ArrayList<Station> getStations() {
		return stations;
	}

	public void setStations(ArrayList<Station> stations) {
		this.stations = stations;
	}
}
