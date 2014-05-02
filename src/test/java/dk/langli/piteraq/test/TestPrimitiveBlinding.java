package dk.langli.piteraq.test;

import java.io.IOException;
import java.math.BigInteger;

import javax.crypto.BadPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import dk.langli.piteraq.hash.Digester;

public class TestPrimitiveBlinding extends CryptographicTestCase {
	protected static String MSG = "Word up!";
	
	@Override
	protected void setUp() throws Exception {
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
