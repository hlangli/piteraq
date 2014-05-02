package dk.langli.piteraq.stream;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;

import org.apache.commons.io.output.NullOutputStream;
import org.pitaya.security.Digest;

public class HashOutputStream extends FilterOutputStream {
	private Digest digest = null;
	private BigInteger hash = null;
	
	public HashOutputStream(Digest digest) {
		this(digest, new NullOutputStream());
	}

	public HashOutputStream(Digest digest, OutputStream out) {
		super(out);
		this.digest = digest;
	}

	@Override
	public void write(int b) throws IOException {
		digest.update(new Integer(b).byteValue());
		super.write(b);
	}

	public BigInteger digest() throws IOException {
		if(hash == null) {
			close();
			hash = new BigInteger(1, digest.digest());
		}
		return hash;
	}
	
	public Digest getDigest() {
		return digest;
	}
}
