package com.venky.core.io;

import java.io.IOException;

public class StringReader extends java.io.StringReader{

	public StringReader(String s) {
		super(s);
	}

	@Override
	public void close() {
		try {
			super.reset();
		}catch(IOException ex) {
			//
		}
	}
	
}
