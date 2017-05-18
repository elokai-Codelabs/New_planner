package com.group.routeplanner.data;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.group.routeplanner.data.Station;
import com.group.routeplanner.data.StationBuilder;

public class StationBuilderTest {
	
	StationBuilder stationBuilder;
	Station mockStation1,mockStation2;
		
	@Before
	public void setUp(){
		stationBuilder=new StationBuilder();
	}

	@Test
	public void testGetStations_IsNotNull_returnTrue() {		
		ArrayList<Station> stations=stationBuilder.getStations();
		assertTrue(stations != null);
	}
	
	@Test
	public void testBuilderStation_ReturnTrue_BuildNewStation() {		
		ArrayList<Station> stations=new ArrayList<Station>();
		stationBuilder.buildStation(stations, "station1", "line1");	
		assertTrue(stations.get(0).getName()=="station1"
				&&stations.get(0).getLines().iterator().next()=="line1");
	}

	@Test
	public void testBuilderStation_ReturnTrue_NoBuildExistStation() {		
		ArrayList<Station> stations=new ArrayList<Station>();
		stationBuilder.buildStation(stations, "station1", "line1");	
		stationBuilder.buildStation(stations, "station1", "line1");	
		assertTrue(stations.size()==1);
	}
	
	@Test
	public void testBuilderStation_ReturnTrue_AddNewLine() {		
		ArrayList<Station> stations=new ArrayList<Station>();
		stationBuilder.buildStation(stations, "station1", "line1");	
		stationBuilder.buildStation(stations, "station1", "line2");	
		assertTrue(stations.get(0).getLines().size()==2);
	}
	
	@Test
	public void testBuilderStation_ReturnTrue_NoAddExistLine() {		
		ArrayList<Station> stations=new ArrayList<Station>();
		stationBuilder.buildStation(stations, "station1", "line1");	
		stationBuilder.buildStation(stations, "station1", "line1");	
		assertTrue(stations.get(0).getLines().size()==1);
	}
	
	@Test
	public void testGetStation_ReturnTrue_CorrectStation() {		
		ArrayList<Station> stations=new ArrayList<Station>();
		stations.add(new Station("station1"));	
		Station gotStation=stationBuilder.getStation(stations, "station1");	
		assertTrue(gotStation.equals(stations.get(0)));
	}
	
	@Test(expected = NullPointerException.class)
	public void testGetStation_ReturnFalse_WrongStation() {		
		ArrayList<Station> stations=new ArrayList<Station>();
		stations.add(new Station("station1"));	
		Station gotStation=stationBuilder.getStation(stations, "station2");	
		assertFalse(gotStation.equals(stations.get(0)));
	}
	
	@Test
	public void testAssignNeibors_ReturnTrue() {		
		ArrayList<Station> stations=stationBuilder.getStations();
		boolean b=true;
		for(Station s:stations)
			for(Station neibor:s.getNeighbors().keySet())
				if(neibor == null)
					b=false;
		assertTrue(b);
	}
}
