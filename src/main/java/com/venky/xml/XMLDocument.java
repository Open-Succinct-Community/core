package com.venky.xml;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

public class XMLDocument {

    Document document = null;
    XMLElement documentRoot = null;
    static DocumentBuilder builder = null; 
    static {
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }catch (ParserConfigurationException ex){
            throw new RuntimeException(ex);
        }
    }
                
    public XMLDocument(Document document) {
        this.document = document;
        this.documentRoot = new XMLElement(this, document.getDocumentElement());
    }

    public XMLDocument(String rootName){
        document = builder.newDocument();
        documentRoot = createElement(rootName);
        document.appendChild(documentRoot.getInner());
    }
    
    public final XMLElement createElement(String tagName) {
        return new XMLElement(this, document.createElement(tagName));
    }

    public Document getDocument() {
        return document;
    }

    public XMLElement getDocumentRoot() {
        return documentRoot;
    }

    public void serialize(File f) {
        XMLSerializationHelper.serialize(getDocument(), f);
    }

    public void serialize(OutputStream os) {
        serialize(new PrintWriter(os));
    }

    public void serialize(Writer w) {
        XMLSerializationHelper.serialize(getDocument(), w);
    }

    public static XMLDocument getDocumentFor(File f) {
        return new XMLDocument(XMLSerializationHelper.getDocument(f));
    }

    public static XMLDocument getDocumentFor(InputStream is) {
       return new XMLDocument(XMLSerializationHelper.getDocument(is));
    }
    
    public static XMLDocument getDocumentFor(String s){
        StringReader r = new StringReader(s);
        return getDocumentFor(r);
    }
    public static XMLDocument getDocumentFor(Reader r){
    	return new XMLDocument(XMLSerializationHelper.getDocument(r));
    }
    

    @Override
    public String toString() {
        Writer w = new StringWriter();
        XMLSerializationHelper.serialize(getDocument(), w);
        return w.toString();
    }
}
