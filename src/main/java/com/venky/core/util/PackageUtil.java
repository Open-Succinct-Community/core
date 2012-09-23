/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.venky.core.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Stack;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *
 * @author venky
 */
public class PackageUtil {
    
    public static List<String> getClasses(URL url, String packagePath) {
        List<String> classes  = new ArrayList<String>();
        
        String path = packagePath;//.replace('.', '/');
        try {
            if (url.getProtocol().equals("jar")){
                File f = new File(url.getFile().substring("file:".length(), url.getFile().lastIndexOf("!")));
                JarFile jf = new JarFile(f); 
                Enumeration<JarEntry> jes =jf.entries();
                while (jes.hasMoreElements()){
                    JarEntry je = jes.nextElement();
                    if (je.getName().startsWith(path)){
                        addClassName(classes, je.getName());
                    }
                }
                jf.close();
            }else if (url.getProtocol().equals("file")) {
                File root = new File(url.getPath());
                Stack<File> sFiles = new Stack<File>();
                sFiles.push(root);
                while (!sFiles.isEmpty()){
                    File f = sFiles.pop();
                    String pathRelativeToRoot = f.getPath().length() > root.getPath().length() ? 
                            f.getPath().substring(root.getPath().length()+1) : "";
                    if (f.isDirectory()){
                        sFiles.addAll(Arrays.asList(f.listFiles()));
                    }else if (pathRelativeToRoot.startsWith(path)){
                        addClassName(classes, pathRelativeToRoot);
                    }
                }
            }
        }catch (IOException ex){
            throw new RuntimeException(ex);
        }
        return classes;
    }
    
    private static void addClassName(List<String> classes , String fileName){
        if (fileName.endsWith(".class")){
            classes.add(fileName.substring(0,fileName.length() - ".class".length()).replace('/', '.'));
        }
    }
}
