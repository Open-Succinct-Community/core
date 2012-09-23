/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.venky.geo;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.logging.Logger;

import com.venky.xml.XMLDocument;
import com.venky.xml.XMLElement;

/**
 *
 * @author venky
 */
public class GeoCoder {
    private static final GeoSP[] sps = { new Nominatim() , new Google()};
    public static void fillGeoInfo(String address,GeoLocation location){
    	GeoLocation result = getLocation(address);
    	if (result != null){
	    	location.setLatitude(result.getLatitude());
	    	location.setLongitude(result.getLongitude());
    	}
    }
    
    private static final GeoLocationBuilder<GeoLocation> builder = new DefaultGeoLocationBuilder();
    public static GeoLocation getLocation(String address){
    	for (GeoSP sp : sps){
           GeoLocation loc = sp.getLocation(address);
           if (loc != null){
        	   Logger.getLogger(GeoCoder.class.getName()).info("Lat,Lon found using " + sp.getClass().getSimpleName());
        	   return loc;
           }
    	}
    	return null;
    }
    private static interface GeoSP {
    	public GeoLocation getLocation(String address);
    }
    private static class Google implements GeoSP {
    	private static final String WSURL = "http://maps.googleapis.com/maps/api/geocode/xml?sensor=false&address=";
		public GeoLocation getLocation(String address) {
        	try {
	            String url = WSURL + URLEncoder.encode(address,"UTF-8");
	            URL u = new URL(url);
	            URLConnection connection = u.openConnection();
	            XMLDocument doc = XMLDocument.getDocumentFor(connection.getInputStream());
	            XMLElement status = doc.getDocumentRoot().getChildElement("status");
	            if ("OK".equals(status.getNodeValue())){
	                XMLElement location = doc.getDocumentRoot().getChildElement("result").getChildElement("geometry").getChildElement("location");
	                float lat=-1; 
	                float lng=-1 ;
	                for (Iterator<XMLElement> nodeIterator =location.getChildElements() ; nodeIterator.hasNext();){
	                    XMLElement node = nodeIterator.next();
	                    if (node.getNodeName().equals("lat")){
	                        lat = Float.valueOf(node.getChildren().next().getNodeValue());
	                    }else if (node.getNodeName().equals("lng")){
	                        lng = Float.valueOf(node.getChildren().next().getNodeValue());
	                    }
	                }
	            	Logger.getLogger(getClass().getName()).info("URL:" + url);
	                return builder.create(lat,lng);
	            }
	        } catch (IOException e) {
	           Logger.getLogger(getClass().getName()).warning(e.getMessage());
	        }
        	return null;
		}
    	
    }
    private static class Nominatim implements GeoSP {
    	private static final String WSURL = "http://nominatim.openstreetmap.org/search?format=xml&polygon=0&q=";
		public GeoLocation getLocation(String address) {
			try {
	            String url = WSURL + URLEncoder.encode(address,"UTF-8");
	            URL u = new URL(url);
	            URLConnection connection = u.openConnection();
	            XMLDocument doc = XMLDocument.getDocumentFor(connection.getInputStream());
	            XMLElement place = doc.getDocumentRoot().getChildElement("place");
	            
	            if (place != null){
	            	Logger.getLogger(getClass().getName()).info("URL:" + url);
	                float lat= Float.valueOf(place.getAttribute("lat")); 
	                float lng= Float.valueOf(place.getAttribute("lon")) ;
	                return builder.create(lat,lng);
	            }
	        } catch (IOException e) {
	           Logger.getLogger(getClass().getName()).warning(e.getMessage());
	        }
        	return null;		
    	}
    }

}
