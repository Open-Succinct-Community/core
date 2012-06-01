package com.venky.core.io;

import java.io.IOException;

public class StringReader extends java.io.StringReader{

	public StringReader(String s) {
		super(s);
	}

	@Override
	public boolean markSupported() {
		return false;
	}

	@Override
	public void mark(int readAheadLimit) throws IOException {
		throw new IOException("mark not supported");
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
