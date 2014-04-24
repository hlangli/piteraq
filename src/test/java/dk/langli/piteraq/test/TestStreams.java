package dk.langli.piteraq.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;

import javax.crypto.BadPaddingException;

import org.junit.Test;

import dk.langli.piteraq.CryptographicException;
import dk.langli.piteraq.Digester;
import dk.langli.piteraq.stream.HashInputStream;

public class TestStreams extends CryptographicTestCase {
	@Override
	protected void setUp() throws Exception {
//		for(int i=0; i<2; i++) {
//			MSG += " - "+MSG;
//		}
	}

	@Test
	public void testInputHashing() throws BadPaddingException, IOException, CryptographicException {
		ByteArrayInputStream in = new ByteArrayInputStream(MSG.getBytes());
		@SuppressWarnings("resource")
		HashInputStream hashIn = new HashInputStream(digest, in);
		BigInteger h = hashIn.digest();
		assert h.equals(Digester.digest(MSG, digest));
	}

//	@Test
//	public void testSigning() throws BadPaddingException, IOException, CryptographicException {
//		log.info("m: " + MSG);
//		Signature<RSAPublicKey> signature = new Signature<RSAPublicKey>(MSG, bob, bob.getPublicKey(), digest);
//		String signatureStr = json.toJson(signature);
//		log.info(Signature.class.getSimpleName() + " " + signatureStr);
//		assert signature.verify();
//		Type signatureType = new TypeToken<Signature<RSAPublicKey>>() {
//      }.getType();
//		Signature<RSAPublicKey> signature2 = json.fromJson(signatureStr, signatureType);
//		assert signature2.verify();
//		assert signature.equals(signature2);
//	}
//	
//	@Test
//	public void testBlinding() throws BadPaddingException, CryptographicException, IOException {
//		log.info("m: " + MSG);
//		RSABlind blind = new RSABlind(MSG, bob, alice.getPublicKey(), digest);
//		String blindStr = json.toJson(blind);
//		log.info(RSABlind.class.getSimpleName() + " " + blindStr);
//		RSABlind blind2 = json.fromJson(blindStr, RSABlind.class);
//		assert blind2.getBlindingFactor() == null;
//		assert blind.equals(blind2);
//		RSABlindSignature blindSignature = new RSABlindSignature(blind2, alice);
//		log.info(RSABlindSignature.class.getSimpleName() + " " + json.toJson(blindSignature));
//		assert blindSignature.verify(MSG, blind.getBlindingFactor(), digest);
//		assert !blindSignature.verify(MSG+"A", blind.getBlindingFactor(), digest);
//	}
}
