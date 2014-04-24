package dk.langli.piteraq.message;

import java.io.IOException;
import java.math.BigInteger;

import javax.crypto.BadPaddingException;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.pitaya.security.Digest;

import dk.langli.piteraq.CryptographicException;
import dk.langli.piteraq.NumberConverter;
import dk.langli.piteraq.SigningKey;
import dk.langli.piteraq.VerificationKey;

public class Signature<V extends VerificationKey> {
	private Digest digest = null;
	private BigInteger h = null;
	private BigInteger s = null;
	private V v = null;
	
	public Signature() {
	}
	
	public Signature(String message, SigningKey signingKey, V verificationKey, Digest digest) throws BadPaddingException, CryptographicException, IOException {
		this.digest = digest;
		v = verificationKey;
		h = NumberConverter.toBigInteger(digest.digest(message.getBytes()));
		s = signingKey.sign(h);
	}
	
	public BigInteger getSignature() {
		return s;
	}

	public V getVerificationKey() {
		return v;
	}

	public Digest getDigest() {
		return digest;
	}
	
	public BigInteger getHash() {
		return h;
	}
	
	public boolean verify() throws BadPaddingException {
		return v.verify(h, s);
	}
	
	@Override
	public boolean equals(Object obj) {
		return hashCode() == obj.hashCode();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder(17, 77);
		return hcb.append(h).append(s).append(v).toHashCode();
	}
}
