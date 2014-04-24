package dk.langli.piteraq.message;

import java.io.IOException;
import java.math.BigInteger;

import javax.crypto.BadPaddingException;

import org.pitaya.security.Digest;

import dk.langli.piteraq.CryptographicException;
import dk.langli.piteraq.rsa.RSAPrivateKey;
import dk.langli.piteraq.rsa.RSAPublicKey;

public class RSABlindSignature {
	private RSAPublicKey su = null;
	private BigInteger b = null;
	private BigInteger bs = null;

	public RSABlindSignature(RSABlind blind, RSAPrivateKey blindSigningKey) throws BadPaddingException, CryptographicException {
		if(!blind.getSignerPublicKey().equals(blindSigningKey.getPublicKey())) {
			throw new CryptographicException("Blinding key is different from blind-signing key");
		}
		b = blind.getBlind();
		su = blindSigningKey.getPublicKey();
		bs = blindSigningKey.sign(blind.getBlind());
	}
	
	private boolean verify() throws BadPaddingException {
		return su.verify(b, bs);
	}

	public boolean verify(String message, BigInteger blindingFactor, Digest digest) throws BadPaddingException, IOException {
		BigInteger r = blindingFactor;
		return verify() && su.verify(message, unblind(r), digest);
	}

	public BigInteger unblind(BigInteger blindingFactor) throws BadPaddingException {
		BigInteger r = blindingFactor;
		return su.unblind(bs, r);
	}
}
