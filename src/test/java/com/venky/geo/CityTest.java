/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.venky.geo;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXParseException;


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
        try {
        	City instance = new City("Bangalore");
            GeoLocation result = instance.getLocation();
            assertEquals(12.9716,result.getLatitude(),0.02);
            assertEquals(77.5946,result.getLongitude(),0.02);
        }catch (Exception ex){
        	if (ex.getCause() instanceof SAXParseException){
            	if (ex.getCause().getMessage().startsWith("Premature end of file")){
            		System.out.println("WARN: Check connection to internet!. Could not get lat lng from Google.");
            		return;
            	}
        	}
        }
    }
    
}
