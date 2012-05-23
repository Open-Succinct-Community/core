package com.venky.core.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DelayedInputStream extends InputStream{
	ByteArrayOutputStream out = null;
	ByteArrayInputStream in = null;
	public DelayedInputStream(ByteArrayOutputStream out){
		this.out = out;
	}
	
	
	@Override
	public int read() throws IOException {
		if (in == null){
			in = new ByteArrayInputStream(out.toByteArray());
		}
		return in.read();
	}

}
