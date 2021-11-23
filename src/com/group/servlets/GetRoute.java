package com.group.servlets;

import com.google.gson.Gson;
import com.group.manager.RouteCalculator;
import com.group.routeplanner.data.Route;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class GetRoute extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GetRoute() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String station1 = request.getParameter("station1").toString();
		String station2 = request.getParameter("station2").toString();		
		RouteCalculator calculate = (RouteCalculator) getServletContext().getAttribute("calculator");
		Gson gson = new Gson();
		
		ArrayList<Route> stations = calculate.getRoutesForView(station1, station2);
		String json = gson.toJson(stations);
						
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
