package com.venky.core.io;

import java.io.IOException;
import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

public class ByteArrayOutputStreamTest {

	@Test
	public void test() {
		SeekableByteArrayOutputStream os = new SeekableByteArrayOutputStream();
		try {
			os.write(new byte[]{14,15,16,17});
			os.seek(2);
			os.write(new byte[]{18,19,20,21});
			Assert.assertTrue(Arrays.equals(os.toByteArray(), new byte[]{14,15,18,19,20,21}));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
	}

}
