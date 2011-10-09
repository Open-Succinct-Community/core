/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.venky.geo;

import com.venky.geo.City;
import com.venky.geo.GeoCoder.Location;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author venky
 */
public class CityTest {
    
    public CityTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getLocation method, of class City.
     */
    @Test
    public void testGetLocation() {
        System.out.println("getLocation");
        City instance = new City("Bangalore");
        Location result = instance.getLocation();
        assertEquals(12.9716,result.lat,0.0001);
        assertEquals(77.5946,result.lng,0.0001);
    }
}
