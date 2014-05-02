package dk.langli.piteraq;

import java.io.IOException;
import java.math.BigInteger;

import javax.crypto.BadPaddingException;

import org.pitaya.security.Digest;

public interface SigningKey {
	public BigInteger sign(BigInteger message) throws BadPaddingException;
	public BigInteger sign(String message, Digest digest) throws BadPaddingException, CryptographicException, IOException;
}
