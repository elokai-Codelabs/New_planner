package com.group.routeplanner.data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <h1>Class TransitXMLParser</h1>
 * 
 * Reads in XML files and formats data
 * to a <CODE>String</CODE> for use with
 * <CODE>StationBuilder</CODE>.
 *
 */
public class TransitXMLParser {
	
	File file = null;
	
	public Map<String, List<String>> getNetworkData() throws IOException 
	{		
		ClassLoader loader = TransitXMLParser.class.getClassLoader();
        URL path = loader.getResource("");
		
		file = new File(path+"LondonUnderground.xml");
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			return getNetworkData(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private Map<String, List<String>> getNetworkData(Document doc) 
	{		
			Map<String, List<String>> stations = new HashMap<String, List<String>>();
			NodeList lineList = doc.getElementsByTagName("line");

			for (int temp = 0; temp < lineList.getLength(); temp++) {
				Node lineNode = lineList.item(temp);
				Element eElement = (Element) lineNode;
				List<String> currentLineStations = new ArrayList<String>();
				NodeList stationList = eElement.getElementsByTagName("station");
				for (int i = 0; i < stationList.getLength(); i++) {
					currentLineStations.add(stationList.item(i).getTextContent());
				}
				stations.put(eElement.getAttribute("name"), currentLineStations);
			}

			return stations;
	
	}

}


