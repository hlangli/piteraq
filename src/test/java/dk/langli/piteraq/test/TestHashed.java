package dk.langli.piteraq.test;

import java.io.IOException;
import java.math.BigInteger;

import javax.crypto.BadPaddingException;

import org.junit.Test;
import org.pitaya.security.Digest;
import org.pitaya.security.Digests;

import dk.langli.piteraq.CryptographicException;

public class TestHashed extends CryptographicTestCase {
	@Override
	protected void setUp() throws Exception {
	}

	@Test
	public void testSigning() throws BadPaddingException, IOException, CryptographicException {
		log.info("m: " + MSG);
		Digest digest = Digests.keccak512();
		BigInteger s = bob.sign(MSG, digest);
		assert bob.getPublicKey().verify(MSG, s, digest);
		assert !bob.getPublicKey().verify(MSG+"A", s, digest);
	}
	
//	@Test
//	public void testBlinding() throws BadPaddingException, CryptographicException {
//		log.info("m: " + MSG);
//		RSABlind blind = new RSABlind(MSG, bob, alice.getPublicKey());
//		String blindStr = json.toJson(blind);
//		log.info(RSABlind.class.getSimpleName() + " " + blindStr);
//		RSABlind blind2 = json.fromJson(blindStr, RSABlind.class);
//		assert blind2.getBlindingFactor() == null;
//		assert blind.equals(blind2);
//		RSABlindSignature blindSignature = new RSABlindSignature(blind2, alice);
//		log.info(RSABlindSignature.class.getSimpleName() + " " + json.toJson(blindSignature));
//		assert blindSignature.verify(MSG, blind.getBlindingFactor());
//	}
}
