package dk.langli.piteraq.hash;

import org.pitaya.security.Digest;

public class InstantiableDigest implements Digest {
	private Digest digest;
	
	public InstantiableDigest(Digest digest) {
		this.digest = digest;
	}

	@Override
	public int length() {
		return digest.length();
	}

	@Override
	public Digest reset() {
		return digest.reset();
	}

	@Override
	public Digest update(byte input) {
		return digest.update(input);
	}

	@Override
	public Digest update(byte... input) {
		return digest.update(input);
	}

	@Override
	public Digest update(byte[] input, int off, int len) {
		return digest.update(input, off, len);
	}

	@Override
	public byte[] digest() {
		return digest.digest();
	}

	@Override
	public byte[] digest(byte... input) {
		return digest.digest(input);
	}

	@Override
	public byte[] digest(byte[] input, int off, int len) {
		return digest.digest(input, off, len);
	}
	
	@Override
	public String toString() {
		return digest.toString();
	}
}
