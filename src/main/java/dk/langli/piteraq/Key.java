package dk.langli.piteraq;

import java.io.IOException;
import java.math.BigInteger;

import javax.crypto.BadPaddingException;

import org.pitaya.security.Digest;

public abstract class Key {
	protected enum KeyType { RSA }
	private KeyType type = null;
	
	public Key(KeyType type) {
		this.type = type;
	}
	
	protected void checkLength(BigInteger m, BigInteger n) throws BadPaddingException {
		if(m.compareTo(n) >= 0) {
			throw new BadPaddingException("Message ("+m+") is larger than modulus ("+n+")");
		}
	}
	
	public KeyType getType() {
		return type;
	}
	
	protected BigInteger digest(String message, Digest digest) throws IOException {
		return Digester.digest(message, digest);
	}
}
