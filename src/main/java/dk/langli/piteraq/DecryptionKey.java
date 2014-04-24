package dk.langli.piteraq;

import java.math.BigInteger;

import javax.crypto.BadPaddingException;

public interface DecryptionKey {
	public BigInteger decrypt(BigInteger cipherText) throws BadPaddingException;
}
