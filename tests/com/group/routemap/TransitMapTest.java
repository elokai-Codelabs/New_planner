package com.group.routemap;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.group.routemap.TransitMap;
import com.group.routeplanner.data.Station;

public class TransitMapTest {

	TransitMap testmap;
	Station station, fulton, chambers;
	
	
	@Before
	public void setUp() throws Exception {
		testmap = new TransitMap();
		station = new Station("Wall");
		fulton = new Station("Fulton");
		chambers = new Station("Chambers");
		
	}
	
	@After
	public void cleanUp() {
		testmap = null;
		station = null;
		fulton = null;
		chambers = null;
		System.gc();
	}

	@Test
	public void addStation_succeedsIfStationListDoesNotContainStation()
	{
		testmap.addStation(station);
		assertTrue(testmap.getStations().contains(station));
	}
	
	@Test
	public void addStation_failsIfStationListDoesContainStation()
	{
		testmap.addStation(station);
		testmap.addStation(station);
		assertTrue(testmap.getStations().size() == 1);
	}
	
	@Test
	public void addDirectRoute_worksIfStationNotNull()
	{
		testmap.addStation(station);
		testmap.addDirectRoute(station, fulton, 1);
		assertTrue(station.getNeighbors().containsKey(fulton));
	}
	
	@Test
	public void addDirectRoute_succeedsInUpdatingDistanceIfKeyExists() 
	{
		testmap.addStation(station);
		testmap.addDirectRoute(station, fulton, 1);
		testmap.addDirectRoute(station, fulton, 2);
		testmap.addDirectRoute(station, fulton, 4);
		assertTrue(station.getNeighbors().size() == 1);
	}
	
	@Test 
	public void addDirectRoute_doesNotAddIfStartAndEndAreTheSame() 
	{
		testmap.addStation(fulton);
		testmap.addDirectRoute(fulton, fulton, 1);
	}
	
	@Test
	public void getDistance_SucceedsIfReturnsInteger()
	{
		testmap.addStation(station);
		testmap.addDirectRoute(station, fulton, 1);
		int check = testmap.getDistance(station, fulton);
		assertTrue(check==1);
	}
	
	@Test
	public void getDistance_failsIfEndDoesNotExistAsNeighbor(){
		testmap.addStation(station);
		int check = testmap.getDistance(station, fulton);
		assertTrue(check==0);
	}
	
	@Test
	public void getPredecessors_SucceedsIfReturnsBothStations() {
		testmap.addStation(station);
		testmap.addDirectRoute(station, fulton, 1);
		testmap.addDirectRoute(station, chambers, 1);
		List<Station> pred = testmap.getPredecessors(station);
		assertTrue(pred.size()==2);
	}
	
	

}
