package dk.langli.piteraq.test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigInteger;

import javax.crypto.BadPaddingException;

import org.junit.Test;

import com.google.gson.reflect.TypeToken;

import dk.langli.piteraq.CryptographicException;
import dk.langli.piteraq.NumberUtil;
import dk.langli.piteraq.message.RSABlind;
import dk.langli.piteraq.message.RSABlindSignature;
import dk.langli.piteraq.message.Signature;
import dk.langli.piteraq.rsa.RSAPublicKey;
import dk.langli.piteraq.stream.HashOutputStream;
import dk.langli.piteraq.stream.SignatureOutputStream;

public class TestMessages extends CryptographicTestCase {
	@Override
	protected void setUp() throws Exception {
	}

	@Test
	public void testSigning() throws BadPaddingException, IOException, CryptographicException {
		log.info("m: " + MSG);
		@SuppressWarnings("resource")
		SignatureOutputStream signOut = new SignatureOutputStream(new HashOutputStream(digest));
		signOut.write(MSG.getBytes());
		BigInteger s = signOut.sign(bob);
		Signature<RSAPublicKey> signature = new Signature<RSAPublicKey>(signOut.getHash(), digest, s, bob.getPublicKey());
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
		BigInteger r = alice.getPublicKey().newBlindingFactor();
		BigInteger h = NumberUtil.toBigInteger(digest.digest(MSG.getBytes()));
		BigInteger b = alice.getPublicKey().blind(h, r);
		BigInteger s = bob.sign(b);
		RSABlind blind = new RSABlind(b, s, bob.getPublicKey(), alice.getPublicKey());
		String blindStr = json.toJson(blind);
		info(RSABlind.class, blindStr);
		RSABlind blind2 = json.fromJson(blindStr, RSABlind.class);
		assert blind2.getBlindingFactor() == null;
		assert blind.equals(blind2);
		assert blind2.getBlinderPublicKey().verify(blind2.getBlind(), blind2.getBlinderSignature());
		BigInteger bs = alice.sign(blind2.getBlind());
		RSABlindSignature blindSignature = new RSABlindSignature(blind2, bs);
		String blindSignatureStr = json.toJson(blindSignature);
		info(RSABlindSignature.class, blindSignatureStr);
		RSABlindSignature blindSignature2 = json.fromJson(blindSignatureStr, RSABlindSignature.class);
		info(RSABlindSignature.class, json.toJson(blindSignature2));
		assert blindSignature2.verify(h, r);
		assert !blindSignature2.verify(h.add(BigInteger.ONE), r);
	}
}
