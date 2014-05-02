package dk.langli.piteraq;

import java.math.BigInteger;

import javax.crypto.BadPaddingException;

public interface EncryptionKey {
	public BigInteger encrypt(BigInteger message) throws BadPaddingException;
}
