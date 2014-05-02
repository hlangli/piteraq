package dk.langli.piteraq.test;

import org.junit.Test;

public class TestStreams extends CryptographicTestCase {
	@Override
	protected void setUp() throws Exception {
//		for(int i=0; i<2; i++) {
//			MSG += " - "+MSG;
//		}
	}

	@Test
	public void testNothing() {
		assert true;
	}
	
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
