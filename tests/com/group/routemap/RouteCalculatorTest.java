package com.group.routemap;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.group.manager.RouteCalculator;
import com.group.routeplanner.data.Route;
import com.group.routeplanner.data.Station;

public class RouteCalculatorTest {
	RouteCalculator rc;
	Station bank, moorgate;

	@Before
	public void setUp() {
		rc = new RouteCalculator();
		bank = new Station("bank");
		moorgate = new Station("Moorgate");
	}

	@Test(expected = NullPointerException.class)
	public void testGetRoute_WithWrongStation_ThrowsNullPointerExcetpion() {
		rc.getRoutesForView("WrongStationName", "Stockwell");
	}

	@Test
	public void testGetRoute_WithCorrectStation_ReturnANoneNullList() {
		ArrayList<Route> stations = rc.getRoutesForView("Bank", "Stockwell");
		assertTrue(stations != null);
	}

	@Test
	public void testGetRoute_WithSameStation_ReturnANoneNullList() {
		ArrayList<Route> stations = rc.getRoutesForView("Bank", "Bank");
		assertTrue(stations != null);
		assertTrue(stations.size() == 1);
	}

	@Test
	public void testGetRoute_WithSimplePath_returnOnePathOnly() {
		ArrayList<Route> stations = rc.getRoutesForView("Bank", "Moorgate");
		assertTrue(stations.size() == 1);
		ArrayList<Station> aPath = stations.get(0).getPairedStations();
		assertTrue(aPath.get(0).getName().equals("Bank"));
		assertTrue(aPath.get(1).getName().equals("Moorgate"));
	}

	@Test
	public void testGetRoute_WithCompleteTube() {
		ArrayList<Route> routes = rc.getRoutesForView("Baker Street",
				"Mile End");
		assertTrue(routes.size() >= 1); 

	}

	@Test
	public void testBubblesort() {
		ArrayList<Route> routes=rc.getRoutesForView("Bank", "Moorgate");
		rc.bubbleSort(routes);
		boolean b=true;
		Route tmp=new Route();
		for(Route route:routes)
		{
			if(tmp.getCount()>route.getCount())
				b=false;
			tmp=route;
		}
		assertTrue(b);
	}

}