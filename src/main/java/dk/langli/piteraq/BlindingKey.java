package dk.langli.piteraq;

import java.io.IOException;
import java.math.BigInteger;

import javax.crypto.BadPaddingException;

import dk.langli.piteraq.stream.HashInputStream;

public interface BlindingKey {
	public BigInteger newBlindingFactor();
	public BigInteger blind(BigInteger message, BigInteger blindingFactor) throws BadPaddingException;
	public BigInteger blind(HashInputStream message, BigInteger blindingFactor) throws BadPaddingException, IOException;
	public BigInteger unblind(BigInteger blindSignature, BigInteger blindingFactor);
}
