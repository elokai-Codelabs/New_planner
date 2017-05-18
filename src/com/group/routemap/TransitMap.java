package com.group.routemap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.group.routeplanner.data.Station;

/**
 * @version 1.0
 * @since 06-02-2014
 */
public class TransitMap implements RoutesMap {
	private ArrayList<Station> stations = new ArrayList<Station>();

	public void addStation(Station station) {
		if (!stations.contains(station))
			stations.add(station);
	}

	/**
	 * Adds a new <CODE>Station</CODE> to the <CODE>TransitMap</CODE>
	 */
	@Override
	public void addDirectRoute(Station start, Station end, int distance) {
		if (!stations.contains(start))
			stations.add(start);

		if (!stations.contains(end))
			stations.add(end);

		Station startStation = stations.get(stations.indexOf(start));
		startStation.addNeighbor(end, distance);
		Station endStation = stations.get(stations.indexOf(end));
		endStation.addNeighbor(start, distance);

	}

	@Override
	public int getDistance(Station start, Station end) {
		int distance = 0;

		for (Entry<Station, Integer> entry : start.getNeighbors().entrySet()) {
			if (entry.getKey() == end) {
				distance = entry.getValue();
			}
		}

		return distance;
	}

	/**
	 * Finds the previous <CODE>Stations</CODE> object of the station point.
	 * 
	 * @param station
	 *            the point of reference to get the previous stations
	 * @return <CODE>ArrayList</CODE>
	 */
	@Override
	public List<Station> getDestinations(Station station) {
		return getPredecessors(station);
	}

	@Override
	public List<Station> getPredecessors(Station station) {
		ArrayList<Station> predecessors = new ArrayList<Station>();

		for (Station s : station.getNeighbors().keySet()) {
			predecessors.add(s);
		}

		return predecessors;
	}

	@Override
	public RoutesMap getInverse() {
		return null;
	}

	public List<Station> getStations() {
		return this.stations;
	}

	public void setStations(ArrayList<Station> stations) {
		this.stations = stations;
	}

}
