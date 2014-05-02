package dk.langli.piteraq.stream;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.pitaya.security.Digest;

import dk.langli.piteraq.EncryptionKey;

public class MGF1InputStream extends FilterInputStream {
	private Digest digest;
	private int maxDataSize = -1;


	protected MGF1InputStream(InputStream in, Digest digest, EncryptionKey encryptionKey) {
		super(in);
		this.digest = digest;
//		maxDataSize = encryptionKey. - 2 - 2 * digestLen;
	}

	@Override
	public int read() throws IOException {
		// TODO Auto-generated method stub
		return super.read();
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		// TODO Auto-generated method stub
		return super.read(b, off, len);
	}

	private byte[] generateMask(byte[] seed, int length) {
		byte[] mask = new byte[length];
		byte[] C = new byte[4];
		int counter = 0;
		int hLen = digest.length();
		digest.reset();
		while(counter < (length / hLen)) {
			ItoOSP(counter, C);
			digest.update(seed);
			digest.update(C);
			System.arraycopy(digest.digest(), 0, mask, counter * hLen, hLen);
			counter++;
		}
		if((counter * hLen) < length) {
			ItoOSP(counter, C);
			digest.update(seed);
			digest.update(C);
			System.arraycopy(digest.digest(), 0, mask, counter * hLen, mask.length - (counter * hLen));
		}
		return mask;
	}

	private void ItoOSP(int i, byte[] sp) {
		sp[0] = (byte) (i >>> 24);
		sp[1] = (byte) (i >>> 16);
		sp[2] = (byte) (i >>> 8);
		sp[3] = (byte) (i >>> 0);
	}
}