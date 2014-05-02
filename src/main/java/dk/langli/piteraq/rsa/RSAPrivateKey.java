package dk.langli.piteraq.rsa;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.pitaya.security.Digest;

import dk.langli.piteraq.CryptographicException;
import dk.langli.piteraq.Key;
import dk.langli.piteraq.NumberUtil;
import dk.langli.piteraq.PrivateKey;

public class RSAPrivateKey extends Key implements PrivateKey<RSAPublicKey> {
	private static final BigInteger ONE = BigInteger.ONE;
	protected static final SecureRandom PRNG = new SecureRandom();
	private BigInteger e = RSAPublicKey.DEFAULT_EXPONENT;
	private BigInteger p = null;
	private BigInteger q = null;
	private transient BigInteger n = null;
	private transient BigInteger d = null;
	private transient RSAPublicKey u = null;

	public RSAPrivateKey(BigInteger p, BigInteger q) {
   	super(KeyType.RSA);
		this.p = p;
		this.q = q;
	}

	public RSAPrivateKey(int length) {
   	super(KeyType.RSA);
		p = BigInteger.probablePrime(length / 2, PRNG);
		q = BigInteger.probablePrime(length / 2, PRNG);
	}

	private BigInteger getPhi() {
		return (p.subtract(ONE)).multiply(q.subtract(ONE));
	}
	
	public int length() {
		return NumberUtil.toBytes(getModulus()).length * 8;
	}

	public BigInteger getModulus() {
		if (n == null) {
			n = p.multiply(q);
		}
		return n;
	}

	protected BigInteger getPublicExponent() {
		return e;
	}

	public BigInteger getExponent() {
		if (d == null) {
			d = e.modInverse(getPhi());
		}
		return d;
	}

	@Override
	public RSAPublicKey getPublicKey() {
		if (u == null) {
			u = new RSAPublicKey(this);
		}
		return u;
	}

	@Override
	public BigInteger sign(BigInteger message) throws BadPaddingException {
		BigInteger m = message;
		BigInteger n = getModulus();
		checkLength(m, n);
		BigInteger d = getExponent();
		BigInteger s = m.modPow(d, n);
		return s;
	}

	@Override
	public BigInteger sign(String message, Digest digest) throws BadPaddingException, CryptographicException, IOException {
		BigInteger h = digest(message, digest);
		return sign(h);
	}

	@Override
	public BigInteger decrypt(BigInteger cipherText) throws BadPaddingException {
		BigInteger c = cipherText;
		BigInteger n = getModulus();
		checkLength(c, n);
		BigInteger d = getExponent();
		BigInteger m = c.modPow(d, n);
		return m;
	}

	@Override
	public boolean equals(Object obj) {
		return hashCode() == obj.hashCode();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder(17, 37);
		return hcb.append(p).append(q).toHashCode();
	}
}
