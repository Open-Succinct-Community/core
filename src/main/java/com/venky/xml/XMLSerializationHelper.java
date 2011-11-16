package com.venky.xml;

//import com.sun.org.apache.xerces.internal.parsers.DOMParser;
//import com.sun.org.apache.xml.internal.serialize.OutputFormat;
//import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

public abstract class XMLSerializationHelper {
	private static DOMImplementationRegistry registry = null ; 
	private static DOMImplementationLS impl = null; 
	static {
		try {
			registry = DOMImplementationRegistry.newInstance();
			if (registry != null){
				impl = (DOMImplementationLS)registry.getDOMImplementation("LS");
			}
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}


	public final static int NONE = -1;
	public final static int UTF32BE = 0;
	public final static int UTF32LE = 1;
	public final static int UTF16BE = 2;
	public final static int UTF16LE = 3;
	public final static int UTF8 = 4;

	public final static byte[] UTF32BEBOMBYTES = new byte[] { (byte) 0x00,
			(byte) 0x00, (byte) 0xFE, (byte) 0xFF, };
	public final static byte[] UTF32LEBOMBYTES = new byte[] { (byte) 0xFF,
			(byte) 0xFE, (byte) 0x00, (byte) 0x00, };
	public final static byte[] UTF16BEBOMBYTES = new byte[] { (byte) 0xFE,
			(byte) 0xFF, };
	public final static byte[] UTF16LEBOMBYTES = new byte[] { (byte) 0xFF,
			(byte) 0xFE, };
	public final static byte[] UTF8BOMBYTES = new byte[] { (byte) 0xEF,
			(byte) 0xBB, (byte) 0xBF, };

	public final static byte[][] BOMBYTES = new byte[][] { UTF32BEBOMBYTES,
			UTF32LEBOMBYTES, UTF16BEBOMBYTES, UTF16LEBOMBYTES, UTF8BOMBYTES, };

	public final static int MAXBOMBYTES = 4;// no bom sequence is longer than 4
											// byte

	public static int getBOMType(byte[] _bomBytes) {
		return getBOMType(_bomBytes, _bomBytes.length);
	}

	public static int getBOMType(byte[] _bomBytes, int _length) {
		for (int i = 0; i < BOMBYTES.length; i++) {
			for (int j = 0; j < _length && j < BOMBYTES[i].length; j++) {
				if (_bomBytes[j] != BOMBYTES[i][j])
					break;
				if (_bomBytes[j] == BOMBYTES[i][j]
						&& j == BOMBYTES[i].length - 1)
					return i;
			}
		}
		return NONE;
	}

	public static int getBOMType(File _f) throws IOException {
		FileInputStream fIn = new FileInputStream(_f);
		byte[] buff = new byte[MAXBOMBYTES];
		int read = fIn.read(buff);
		int BOMType = getBOMType(buff, read);
		fIn.close();
		return BOMType;
	}

	public static int getSkipBytes(int BOMType) {
		if (BOMType < 0 || BOMType >= BOMBYTES.length)
			return 0;
		return BOMBYTES[BOMType].length;
	}

	/**
	 * Just reads necessary bytes from the stream
	 * 
	 * @param _fIn
	 */
	public static Reader getReader(File _f) throws IOException {
		int BOMType = getBOMType(_f);
		int skipBytes = getSkipBytes(BOMType);
		FileInputStream fIn = new FileInputStream(_f);
		fIn.skip(skipBytes);
		String e = "UTF-8";
		if (BOMType == UTF16BE || BOMType == UTF16LE) {
			e = "UTF-16";
		} else if (BOMType == UTF32BE || BOMType == UTF32BE) {
			e = "UTF-32";
		}
		Reader reader = new InputStreamReader(fIn, e);
		return reader;
	}

	public static Document getDocument(File _f) {
		try {
			DOMParser parser = new DOMParser();
			parser.parse(new InputSource(getReader(_f)));
			return parser.getDocument();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Document getDocument(Reader reader) {
		try {
			DOMParser parser = new DOMParser();
			parser.parse(new InputSource(reader));
			return parser.getDocument();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void serialize(Node target, File file) {
		try {
			serialize(target, new FileWriter(file));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static void serialize(Node target, Writer wr) {
		try {
			LSSerializer serializer = impl.createLSSerializer();
			DOMConfiguration domConfiguration = serializer.getDomConfig();
			boolean canPrettyPrint = domConfiguration.canSetParameter("format-pretty-print", Boolean.TRUE);
			if (canPrettyPrint){
				domConfiguration.setParameter("format-pretty-print", Boolean.TRUE);
			}
			
			LSOutput lsOutput = impl.createLSOutput();
			lsOutput.setEncoding("UTF-8");
			lsOutput.setCharacterStream(wr);
			serializer.write(target, lsOutput);
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

}
