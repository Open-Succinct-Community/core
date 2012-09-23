/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.venky.core.generic;

import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author venky
 */
public class GenericsTest {
    
    public GenericsTest() {
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
    
    @Test
    public void testGenericType(){ 
        List<String> s = new ArrayList<String>();
        TypeVariable<?> tv = s.getClass().getTypeParameters()[0];
        System.out.println(tv.getName());
    }
}
