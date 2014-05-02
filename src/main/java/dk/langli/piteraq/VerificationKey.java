package dk.langli.piteraq;

import java.io.IOException;
import java.math.BigInteger;

import javax.crypto.BadPaddingException;

import org.pitaya.security.Digest;

public interface VerificationKey {
	public boolean verify(BigInteger message, BigInteger signature) throws BadPaddingException;
	public boolean verify(String message, BigInteger signature, Digest digest) throws BadPaddingException, IOException;
}
