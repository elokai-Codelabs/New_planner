package com.group.servlets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import com.group.manager.RouteCalculator;
import com.group.routeplanner.data.Station;

public class ServletContextListener implements javax.servlet.ServletContextListener
{
	ApplicationContext context;

	public void contextDestroyed(ServletContextEvent event)
	{
		if(context != null)
			((ConfigurableApplicationContext)context).close();
	}

	public void contextInitialized(ServletContextEvent event)
	{		
		ServletContext servletContext = event.getServletContext();
		
		RouteCalculator calculate = new RouteCalculator();
		ArrayList<Station> stations = calculate.getStations();
		ArrayList<String> sortStations = new ArrayList<String>();
		for (Station station : stations)
		{
			sortStations.add(station.getName());
		}
		
		Collections.sort(sortStations);
		servletContext.setAttribute("calculator", calculate);
		servletContext.setAttribute("stations", sortStations);
	}
}
