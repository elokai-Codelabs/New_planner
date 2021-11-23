package com.group.servlets;

import com.group.manager.RouteCalculator;
import com.group.routeplanner.data.Station;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.util.ArrayList;
import java.util.Collections;

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
