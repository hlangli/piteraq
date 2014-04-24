package dk.langli.piteraq.stream;

import java.io.IOException;
import java.io.OutputStream;

import dk.langli.piteraq.EncryptionKey;

public class RSAEncryptionStream extends EncryptionStream {
	private EncryptionKey key = null;
//	private Digest digest = null;
	
	public RSAEncryptionStream(OutputStream out, EncryptionKey key) {
		super(out);
		this.key = key;
//		digest = Digests.keccak512();
	}

	@Override
	public void close() throws IOException {
		super.close();
	}

	@Override
	public void flush() throws IOException {
		super.flush();
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
//		digest.
		
	}
}
