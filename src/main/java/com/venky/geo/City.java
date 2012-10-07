/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.venky.geo;




/**
 *
 * @author venky
 */
public class City {
    private GeoLocation location; 
    public City(String name){
        this.location = new GeoCoder().getLocation(name);
    }

    public GeoLocation getLocation() {
        return location;
    }
    
    
}
