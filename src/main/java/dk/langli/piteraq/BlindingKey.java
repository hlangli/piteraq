package dk.langli.piteraq;

import java.io.IOException;
import java.math.BigInteger;

import javax.crypto.BadPaddingException;

import org.pitaya.security.Digest;

public interface BlindingKey {
	public BigInteger newBlindingFactor();
	public BigInteger blind(BigInteger clearText, BigInteger blindingFactor) throws BadPaddingException;
	public BigInteger blind(String message, BigInteger blindingFactor, Digest digest) throws BadPaddingException, IOException;
	public BigInteger unblind(BigInteger blindSignature, BigInteger blindingFactor);
}
