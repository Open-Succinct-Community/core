package com.venky.core.io;

import java.io.IOException;

public class ByteArrayInputStream extends java.io.ByteArrayInputStream{


	public ByteArrayInputStream(byte[] buf) {
		super(buf);
	}

	@Override
	public void close() throws IOException {
		this.mark = 0;
		reset();
	}

	
}
