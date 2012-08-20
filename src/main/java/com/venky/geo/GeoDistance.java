package com.venky.geo;

import java.net.URL;
import java.net.URLConnection;

import com.venky.core.util.ObjectUtil;
import com.venky.xml.XMLDocument;
import com.venky.xml.XMLElement;


public class GeoDistance {
	public static int distanceKms(double lat1, double lng1, double lat2, double lng2 ){
		return (int)(6378 * Math.acos(Math.sin(lat1*Math.PI/180.0) * Math.sin(lat2*Math.PI/180.0) + Math.cos(lat1*Math.PI/180.0) * Math.cos(lat2*Math.PI/180.0) * Math.cos((lng2 - lng1)*Math.PI/180.0)));
	}
	public static int getDrivingDistanceKms(double lat1, double lng1, double lat2, double lng2){
		String url = "http://open.mapquestapi.com/directions/v1/route?outFormat=xml&unit=k&from="+lat1+","+lng1 +"&to=" + lat2 + "," + lng2 ;
		try {
			 URL u = new URL(url);
	         URLConnection connection = u.openConnection();
	         XMLDocument doc = XMLDocument.getDocumentFor(connection.getInputStream());
	         XMLElement status = doc.getDocumentRoot().getChildElement("info").getChildElement("statusCode");
	         if (ObjectUtil.equals("0",status.getNodeValue())){
	        	 XMLElement distance = doc.getDocumentRoot().getChildElement("route").getChildElement("distance");
	        	 return Double.valueOf(distance.getNodeValue()).intValue();
	         }
		}catch(Exception e){
			// Nothing to do.
		}
		return distanceKms(lat1, lng1, lat2, lng2);
	}
}
