package dk.langli.piteraq.message;

import java.math.BigInteger;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import dk.langli.piteraq.rsa.RSAPublicKey;

public class RSABlind {
	private transient BigInteger r = null;
	private BigInteger b = null;
	private BigInteger s = null;
	private RSAPublicKey bu = null;
	private RSAPublicKey su = null;

	public RSABlind(BigInteger blind, BigInteger signedBlind, RSAPublicKey blinderKey, RSAPublicKey signerKey) {
		this(blind, signedBlind, blinderKey, signerKey, null);
	}

	public RSABlind(BigInteger blind, BigInteger signedBlind, RSAPublicKey blinderKey, RSAPublicKey signerKey, BigInteger randomFactor) {
		r = randomFactor;
		b = blind;
		s = signedBlind;
		bu = blinderKey;
		su = signerKey;
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
