package dk.langli.piteraq;

import java.io.IOException;
import java.math.BigInteger;

import javax.crypto.BadPaddingException;

import org.pitaya.security.Digest;

import dk.langli.piteraq.hash.Digester;

public abstract class Key {
	protected enum KeyType { RSA }
	private KeyType type = null;
	
	protected Key(KeyType type) {
		this.type = type;
	}
	
	protected void checkLength(BigInteger m, BigInteger n) throws BadPaddingException {
		if(m.compareTo(n) >= 0) {
			throw new BadPaddingException("Message ("+m+") is larger than modulus ("+n+")");
		}
	}
	
	protected KeyType getType() {
		return type;
	}
	
	protected BigInteger digest(String message, Digest digest) throws IOException {
		return Digester.digest(message, digest);
	}
}
