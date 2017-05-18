package com.group.manager;


import static org.junit.Assert.*;

import org.junit.Test;

import com.group.routeplanner.data.TransitXMLParser;


public class TransitXMLParserTest {

	TransitXMLParser reader = new TransitXMLParser();
		
	@Test
	public void testGetNetworkData_() 
	{				
		assertTrue(reader.getNetworkData()!=null);
	}
}