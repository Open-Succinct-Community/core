/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.venky.geo;

import com.venky.geo.GeoCoder.Location;



/**
 *
 * @author venky
 */
public class City {
    private String name; 
    private Location location; 
    public City(String name){
        this.name = name; 
        this.location = GeoCoder.getLocation(name);
    }

    public Location getLocation() {
        return location;
    }
    
    
}
