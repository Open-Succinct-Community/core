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
import com.venky.xml.XMLDocument;
import com.venky.xml.XMLElement;

/**
 *
 * @author venky
 */
public class GeoCoder {
    private static final String WSURL = "http://maps.googleapis.com/maps/api/geocode/xml?sensor=false&address=";
    public static class Location {
        float lat,lng; 
        public Location(float lat, float lng){
            this.lat = lat; 
            this.lng = lng;
        }
        public float lat(){
        	return lat;
        }
        public float lng(){
        	return lng;
        }
    }
    public static Location getLocation(String address){
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
                return new Location(lat,lng);
            }
            return null;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
    }
}
