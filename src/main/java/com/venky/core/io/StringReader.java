package com.venky.core.io;

import java.io.IOException;

import com.venky.core.string.StringUtil;

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
	
	public boolean equals(Object o){
		if (o == null){
			return false;
		}
		if (!(o instanceof StringReader)){
			return false;
		}
		StringReader other = (StringReader)o;
		return StringUtil.read(this).equals(StringUtil.read(other));
	}
	
}
