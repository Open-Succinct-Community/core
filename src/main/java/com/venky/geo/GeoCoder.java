/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.venky.geo;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import com.venky.xml.XMLDocument;
import com.venky.xml.XMLElement;

/**
 *
 * @author venky
 */
public class GeoCoder {
    
    private static final Map<String,GeoSP> availableSps = new HashMap<String,GeoSP>();
    static { 
    	registerGeoSP("yahoo",new Yahoo());
    	registerGeoSP("google",new Google());
    	registerGeoSP("openstreetmap",new Nominatim());
    }
    
    private static void registerGeoSP(String sp,GeoSP geoSP){
    	availableSps.put(sp, geoSP);
    }

    private GeoSP preferredServiceProvider = null;
    public GeoCoder(String preferedSP){
    	preferredServiceProvider = availableSps.get(preferedSP);
    }
    public GeoCoder(){
    	this(null);
    }
    
    public void fillGeoInfo(String address,GeoLocation location){
    	GeoLocation result = getLocation(address);
    	if (result != null){
	    	location.setLatitude(result.getLatitude());
	    	location.setLongitude(result.getLongitude());
    	}
    }
    
    private static final GeoLocationBuilder<GeoLocation> builder = new DefaultGeoLocationBuilder();
	Collection<GeoSP> sps = null ;
	public GeoLocation getLocation(String address){
    	if (preferredServiceProvider == null ){
    		sps = Arrays.asList(availableSps.get("yahoo"),availableSps.get("google"),availableSps.get("openstreetmap"));
    	}else {
    		sps = Arrays.asList(preferredServiceProvider); 
    	}
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
    private static class Yahoo implements GeoSP {
    	private static final String WSURL = "http://where.yahooapis.com/geocode?appid=vvNzzZ_V34HjikIGzQZ2Q6.ErIvyP7F7UOVVcbzmWH.2G84oCDRwE8_7cunqsBnjYY1x&q=";

		@Override
		public GeoLocation getLocation(String address) {
			try {
	            String url = WSURL + URLEncoder.encode(address,"UTF-8");
	            URL u = new URL(url);
	            URLConnection connection = u.openConnection();
	            XMLDocument doc = XMLDocument.getDocumentFor(connection.getInputStream());
	            String status = doc.getDocumentRoot().getChildElement("Error").getNodeValue();
	            if ( "0".equals(status)){
	            	Logger.getLogger(getClass().getName()).info("URL:" + url);
	            	XMLElement result = doc.getDocumentRoot().getChildElement("Result");
		            String radius = result.getChildElement("radius").getNodeValue(); 
			        if (Double.valueOf(radius) < 5000){    	
		            	String latitude = result.getChildElement("latitude").getNodeValue();
		            	String longitude = result.getChildElement("longitude").getNodeValue();
		            	return builder.create(Float.valueOf(latitude), Float.valueOf(longitude));
			        }
	            }
			}catch (IOException ex){
				Logger.getLogger(getClass().getName()).warning(ex.getMessage());
			}
            return null;
		}
    	
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
	            	Logger.getLogger(getClass().getName()).info("URL:" + url);
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
