package dk.langli.piteraq.message;

import java.io.IOException;
import java.math.BigInteger;

import javax.crypto.BadPaddingException;

import dk.langli.piteraq.CryptographicException;

public class RSABlindSignature {
	private RSABlind blind = null;
	private BigInteger bs = null;

	public RSABlindSignature(RSABlind blind, BigInteger blindSignature) throws BadPaddingException, CryptographicException {
		this.blind = blind;
		bs = blindSignature;
	}
	
	public BigInteger unblind(BigInteger blindingFactor) throws BadPaddingException {
		BigInteger r = blindingFactor;
		return blind.getSignerPublicKey().unblind(bs, r);
	}

	private boolean verify() throws BadPaddingException {
		return blind.getSignerPublicKey().verify(blind.getBlind(), bs);
	}

	public boolean verify(BigInteger message, BigInteger blindingFactor) throws BadPaddingException, IOException {
		BigInteger r = blindingFactor;
		return verify() && blind.getSignerPublicKey().verify(message, unblind(r));
	}
}
