/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.venky.core.util;

import java.io.IOException;
import java.net.URL;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author venky
 */
public class PackageUtilTest {
    
    public PackageUtilTest() {
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
    public void testJar() throws IOException {
        URL url = getClass().getClassLoader().getResource("java/lang/String.class");
        for (String s : PackageUtil.getClasses(url,"java/lang/String")){
            System.out.println(s);
        }
    }
    @Test
    public void testDirectory() throws IOException{
        URL url = new URL("file:/home/venky/Projects/java/oss/core/target/classes");
        for (String s : PackageUtil.getClasses(url,"com/venky/core/date")){
            System.out.println(s);
        }
    }
          
}
