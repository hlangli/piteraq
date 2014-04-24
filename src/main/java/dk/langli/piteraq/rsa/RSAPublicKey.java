package dk.langli.piteraq.rsa;

import java.io.IOException;
import java.math.BigInteger;

import javax.crypto.BadPaddingException;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.pitaya.security.Digest;

import dk.langli.piteraq.BlindingKey;
import dk.langli.piteraq.Key;
import dk.langli.piteraq.PublicKey;

public class RSAPublicKey extends Key implements PublicKey, BlindingKey {
	protected static final BigInteger DEFAULT_EXPONENT = BigInteger.valueOf(65537);
   private static final BigInteger ONE = BigInteger.ONE;
   private BigInteger e = DEFAULT_EXPONENT;
   private BigInteger n = null;

   public RSAPublicKey(BigInteger modulus, BigInteger exponent) {
   	super(KeyType.RSA);
   	n = modulus;
   	e = exponent;
   }

   public RSAPublicKey(RSAPrivateKey privateKey) {
   	super(KeyType.RSA);
   	n = privateKey.getModulus();
   	e = privateKey.getPublicExponent();
   }

   public BigInteger getModulus() {
   	return n;
   }
   
   public BigInteger getExponent() {
   	return e;
   }

	@Override
	public BigInteger encrypt(BigInteger clearText) throws BadPaddingException {
		BigInteger m = clearText;
		checkLength(m, n);
		BigInteger c = m.modPow(e, n);
		return c;
	}

	@Override
	public boolean verify(BigInteger clearText, BigInteger signature) throws BadPaddingException {
		BigInteger m = clearText;
		checkLength(m, n);
		BigInteger s = signature;
		BigInteger check = s.modPow(e, n);
		return m.equals(check);
	}

	@Override
	public boolean verify(String message, BigInteger signature, Digest digest) throws BadPaddingException, IOException {
		BigInteger h = digest(message, digest);
		return verify(h, signature);
	}

	@Override
	public BigInteger newBlindingFactor() {
		byte[] randomBytes = new byte[10];
		BigInteger r = null;
		BigInteger gcd = null;
		// check that gcd(r,n) = 1 && r < n && r > 1
		do {
			RSAPrivateKey.PRNG.nextBytes(randomBytes);
			r = new BigInteger(1, randomBytes);
			gcd = r.gcd(n);
		}
		while(!gcd.equals(ONE) || r.compareTo(n) >= 0 || r.compareTo(ONE) <= 0);
		return r;
	}

	@Override
	public BigInteger blind(BigInteger clearText, BigInteger blindingFactor) throws BadPaddingException {
		BigInteger m = clearText;
		BigInteger r = blindingFactor;
		checkLength(m, n);
		BigInteger b = ((r.modPow(e, n)).multiply(m)).mod(n);
		return b;
	}

	@Override
	public BigInteger blind(String message, BigInteger blindingFactor, Digest digest) throws BadPaddingException, IOException {
		return blind(digest(message, digest), blindingFactor);
	}

	@Override
	public BigInteger unblind(BigInteger blindedText, BigInteger blindingFactor) {
		BigInteger b = blindedText;
		BigInteger r = blindingFactor;
		BigInteger s = r.modInverse(n).multiply(b).mod(n);
		return s;
	}

	@Override
	public boolean equals(Object obj) {
		return hashCode() == obj.hashCode();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder(17, 57);
		return hcb.append(e).append(n).toHashCode();
	}
}
