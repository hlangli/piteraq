package dk.langli.piteraq.message;

import java.io.IOException;
import java.math.BigInteger;

import javax.crypto.BadPaddingException;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.pitaya.security.Digest;

import dk.langli.piteraq.CryptographicException;
import dk.langli.piteraq.VerificationKey;

public class Signature<V extends VerificationKey> {
	private Digest digest = null;
	private BigInteger m = null;
	private BigInteger s = null;
	private V v = null;
	
	public Signature() {
	}
	
	public Signature(BigInteger message, BigInteger signature, V verificationKey) throws BadPaddingException, CryptographicException, IOException {
		this(message, null, signature,verificationKey);
	}

	public Signature(BigInteger message, Digest digest, BigInteger signature, V verificationKey) throws BadPaddingException, CryptographicException, IOException {
		this.m = message;
		this.digest = digest;
		this.s = signature;
		this.v = verificationKey;
	}

	public BigInteger getSignature() {
		return s;
	}

	public V getVerificationKey() {
		return v;
	}

	public BigInteger getMessage() {
		return m;
	}
	
	public Digest getDigest() {
		return digest;
	}
	
	public boolean verify() throws BadPaddingException {
		return v.verify(m, s);
	}
	
	@Override
	public boolean equals(Object obj) {
		return hashCode() == obj.hashCode();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder(17, 77);
		return hcb.append(m).append(digest).append(s).append(v).toHashCode();
	}
}
