package dk.langli.piteraq.stream;

import java.io.FilterInputStream;
import java.io.IOException;
import java.math.BigInteger;

import javax.crypto.BadPaddingException;

import dk.langli.piteraq.VerificationKey;

public class VerificationInputStream extends FilterInputStream {
	private HashInputStream hashStream = null;
	
	public VerificationInputStream(HashInputStream in) {
		super(in);
		this.hashStream = in;
	}

	/**
	 * This method closes the VerificationStream and then verifies the hash
	 * 
	 * @param verificationKey The verification key
	 * @param signature The signature to verify the hash of the content against
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
