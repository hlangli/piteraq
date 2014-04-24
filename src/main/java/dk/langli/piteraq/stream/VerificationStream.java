package dk.langli.piteraq.stream;

import java.io.FilterInputStream;
import java.io.IOException;
import java.math.BigInteger;

import javax.crypto.BadPaddingException;

import dk.langli.piteraq.VerificationKey;

public class VerificationStream extends FilterInputStream {
	private HashInputStream hashStream = null;
	
	public VerificationStream(HashInputStream in) {
		super(in);
		this.hashStream = in;
	}

	/**
	 * This method closes the VerificationStream and then verifies the hash
	 * 
	 * @param key The verification key
	 * @return Whether the hash of this stream verifies against the signature and the verification key
	 * @throws IOException If the VerificationStream cannot be closed
	 * @throws BadPaddingException If the verification key modulo is smaller than the length of the hash
	 */
	public boolean verify(VerificationKey verificationKey, BigInteger signature) throws IOException, BadPaddingException {
		BigInteger s = signature;
		BigInteger h = hashStream.digest();
		close();
		return verificationKey.verify(h, s);
	}
}
