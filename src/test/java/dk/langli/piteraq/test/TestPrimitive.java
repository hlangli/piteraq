package dk.langli.piteraq.test;

import java.io.IOException;
import java.math.BigInteger;

import javax.crypto.BadPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import dk.langli.piteraq.Digester;

public class TestPrimitive extends CryptographicTestCase {
	protected static String MSG = "Word up!";
	
	@Override
	protected void setUp() throws Exception {
	}

	@Test
	public void testEncryption() throws BadPaddingException {
		log.info("m: " + MSG);
		BigInteger m = new BigInteger(1, MSG.getBytes());
		BigInteger c = bob.getPublicKey().encrypt(m);
		log.info("c(m): " + toString(c));
		BigInteger m2 = bob.decrypt(c);
		log.info("f(c(m)): " + new String(m2.toByteArray()));
		assert m.equals(m2): "Decryption of cipher-text does not yield the message";
	}

	@Test
	public void testSigning() throws BadPaddingException {
		log.info("m: " + MSG);
		BigInteger m = new BigInteger(1, MSG.getBytes());
		BigInteger s = bob.sign(m);
		log.info("s(m): " + toString(s));
		assert bob.getPublicKey().verify(m, s): "Signature does not verify against bob's public key";
	}

	@Test
	public void testBlinding() throws BadPaddingException, IOException {
		log.info("m: " + MSG);
		BigInteger h = Digester.digest(MSG, digest);
		BigInteger r = alice.getPublicKey().newBlindingFactor();
		BigInteger b = alice.getPublicKey().blind(h, r);
		log.info("b(m): " + toString(b));
		BigInteger s = bob.sign(b);
		log.info("s(b(m)): " + toString(s));
		assert bob.getPublicKey().verify(b, s): "Blinder signature does not verify against bob's public key";
		BigInteger bs = alice.sign(b);
		log.info("bs(s(b(m))): " + toString(bs));
		assert alice.getPublicKey().verify(b, bs): "Signer signature does not verify against alice's public key";
		BigInteger ub = alice.getPublicKey().unblind(bs, r);
		log.info("ub(bs(s(b(m)))): " + toString(ub));
		assert alice.getPublicKey().verify(h, ub): "Unblinded signature does not verify against alice's public key";
	}

	private String toString(BigInteger i) {
		return Base64.encodeBase64String(i.toByteArray());
	}
}
