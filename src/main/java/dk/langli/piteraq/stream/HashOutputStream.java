package dk.langli.piteraq.stream;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;

import org.pitaya.security.Digest;

public class HashOutputStream extends FilterOutputStream {
	private Digest digest = null;
	
	public HashOutputStream(Digest digest, OutputStream out) {
		super(out);
		this.digest = digest;
		if(out instanceof SignatureStream) {
			((SignatureStream) out).setHashStream(this);
		}
	}

	@Override
	public void write(int b) throws IOException {
		digest.update(new Integer(b).byteValue());
		super.write(b);
	}

	public BigInteger digest() throws IOException {
		close();
		BigInteger hash = new BigInteger(1, digest.digest());
		return hash;
	}
}
