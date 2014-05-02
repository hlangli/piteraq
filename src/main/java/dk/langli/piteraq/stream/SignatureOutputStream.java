package dk.langli.piteraq.stream;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.math.BigInteger;

import javax.crypto.BadPaddingException;

import dk.langli.piteraq.CryptographicException;
import dk.langli.piteraq.SigningKey;

public class SignatureOutputStream extends FilterOutputStream {
	private final HashOutputStream hashOut;
	
	public SignatureOutputStream(HashOutputStream out) {
		super(out);
		hashOut = out;
	}

	/**
	 * This method closes the SignatureStream and then signs the hash
	 * 
	 * @param signingKey The signing key
	 * @return The signature of the hash of the data that has passed through the SignatureStream
	 * @throws IOException If the SignatureStream cannot be closed
	 * @throws BadPaddingException If the signing key modulo is smaller than the length of the hash
	 * @throws CryptographicException If data has not been hashed (@see HashStream)
	 */
	public BigInteger sign(SigningKey signingKey) throws CryptographicException, IOException, BadPaddingException {
		BigInteger h = hashOut.digest();
		close();
		return signingKey.sign(h);
	}
	
	public BigInteger getHash() throws IOException {
		return hashOut.digest();
	}
}
