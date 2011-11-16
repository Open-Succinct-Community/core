package com.venky.xml;

import java.io.StringWriter;

import org.junit.Test;

public class SerializationTest {

	@Test
	public void test() {
		XMLDocument document = new XMLDocument("Root");
		XMLElement elem = document.createElement("Child");
		document.getDocumentRoot().appendChild(elem);
		
		StringWriter w = new StringWriter();
		XMLSerializationHelper.serialize(document.getDocument(), w);
		XMLSerializationHelper.serialize(elem.getInner(), w);
		System.out.println(w.toString());
	}

}
