package dk.langli.piteraq.stream;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;

import javax.crypto.BadPaddingException;

import dk.langli.piteraq.CryptographicException;
import dk.langli.piteraq.SigningKey;

public class SignatureStream extends FilterOutputStream {
	private HashOutputStream hashStream = null;
	
	public SignatureStream(OutputStream out) {
		super(out);
	}
	
	protected void setHashStream(HashOutputStream hashStream) {
		this.hashStream = hashStream;
	}

	/**
	 * This method closes the SignatureStream and then signs the hash
	 * 
	 * @param key The signing key
	 * @return The signature of the hash of the data that has passed through the SignatureStream
	 * @throws IOException If the SignatureStream cannot be closed
	 * @throws BadPaddingException If the signing key modulo is smaller than the length of the hash
	 * @throws CryptographicException If data has not been hashed (@see HashStream)
	 */
	public BigInteger sign(SigningKey signingKey) throws CryptographicException, IOException, BadPaddingException {
		if(hashStream == null) {
			throw new CryptographicException("Content has not been hashed");
		}
		BigInteger h = hashStream.digest();
		close();
		return signingKey.sign(h);
		
	}
}
