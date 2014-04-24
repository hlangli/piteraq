package dk.langli.piteraq.stream;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

import org.pitaya.security.Digest;

public class HashInputStream extends FilterInputStream {
	private Digest digest = null;
	
	public HashInputStream(Digest digest, InputStream in) {
		super(in);
		this.digest = digest;
	}

	@Override
	public int read() throws IOException {
		int b = super.read();
		digest.update(new Integer(b).byteValue());
		return b;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int size = super.read(b, off, len);
		digest.update(b, off, size);
		return size;
	}

	public BigInteger digest() throws IOException {
		byte[] buf = new byte[8192];
		while(read(buf) != -1);
		close();
		BigInteger hash = new BigInteger(1, digest.digest());
		return hash;
	}
}
