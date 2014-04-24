package dk.langli.piteraq.message;

import java.io.IOException;
import java.math.BigInteger;

import javax.crypto.BadPaddingException;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.pitaya.security.Digest;

import dk.langli.piteraq.rsa.RSAPrivateKey;
import dk.langli.piteraq.rsa.RSAPublicKey;

public class RSABlind {
	private transient BigInteger r = null;
	private BigInteger b = null;
	private BigInteger s = null;
	private RSAPublicKey bu = null;
	private RSAPublicKey su = null;

	public RSABlind(String message, RSAPrivateKey signingKey, RSAPublicKey blindingKey, Digest digest) throws BadPaddingException, IOException {
		r = blindingKey.newBlindingFactor();
		b = blindingKey.blind(message, r, digest);
		s = signingKey.sign(b);
		bu = signingKey.getPublicKey();
		su = blindingKey;
	}
	
	public BigInteger getBlindingFactor() {
		return r;
	}

	public BigInteger getBlind() {
		return b;
	}

	public BigInteger getBlinderSignature() {
		return s;
	}

	public RSAPublicKey getBlinderPublicKey() {
		return bu;
	}

	public RSAPublicKey getSignerPublicKey() {
		return su;
	}
	
	@Override
	public boolean equals(Object obj) {
		return hashCode() == obj.hashCode();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder(17, 77);
		return hcb.append(b).append(s).append(bu).append(su).toHashCode();
	}
}
