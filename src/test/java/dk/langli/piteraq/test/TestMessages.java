package dk.langli.piteraq.test;

import java.io.IOException;
import java.lang.reflect.Type;

import javax.crypto.BadPaddingException;

import org.junit.Test;

import com.google.gson.reflect.TypeToken;

import dk.langli.piteraq.CryptographicException;
import dk.langli.piteraq.message.RSABlind;
import dk.langli.piteraq.message.RSABlindSignature;
import dk.langli.piteraq.message.Signature;
import dk.langli.piteraq.rsa.RSAPublicKey;

public class TestMessages extends CryptographicTestCase {
	@Override
	protected void setUp() throws Exception {
	}

	@Test
	public void testSigning() throws BadPaddingException, IOException, CryptographicException {
		log.info("m: " + MSG);
		Signature<RSAPublicKey> signature = new Signature<RSAPublicKey>(MSG, bob, bob.getPublicKey(), digest);
		String signatureStr = json.toJson(signature);
		info(Signature.class, signatureStr);
		assert signature.verify();
		Type signatureType = new TypeToken<Signature<RSAPublicKey>>() {
      }.getType();
		Signature<RSAPublicKey> signature2 = json.fromJson(signatureStr, signatureType);
		assert signature2.verify();
		assert signature.equals(signature2);
	}
	
	@Test
	public void testBlinding() throws BadPaddingException, CryptographicException, IOException {
		log.info("m: " + MSG);
		RSABlind blind = new RSABlind(MSG, bob, alice.getPublicKey(), digest);
		String blindStr = json.toJson(blind);
		info(RSABlind.class, blindStr);
		RSABlind blind2 = json.fromJson(blindStr, RSABlind.class);
		assert blind2.getBlindingFactor() == null;
		assert blind.equals(blind2);
		RSABlindSignature blindSignature = new RSABlindSignature(blind2, alice);
		info(RSABlindSignature.class, json.toJson(blindSignature));
		assert blindSignature.verify(MSG, blind.getBlindingFactor(), digest);
		assert !blindSignature.verify(MSG+"A", blind.getBlindingFactor(), digest);
	}
}
