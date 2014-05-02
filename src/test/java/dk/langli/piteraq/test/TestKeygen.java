package dk.langli.piteraq.test;

import org.junit.Test;

import dk.langli.piteraq.rsa.RSAPrivateKey;
import dk.langli.piteraq.rsa.RSAPublicKey;

public class TestKeygen extends CryptographicTestCase {
	@Override
	protected void setUp() throws Exception {
	}

//	@Test
//	public void testLength() {
//		log.info("BOB: "+bob.length());
//		assert bob.length() == KEY_LENGTH;
//		log.info("ALICE: "+bob.length());
//		assert alice.length() == KEY_LENGTH;
//		log.info("BOB PUBLIC: "+bob.length());
//		assert bob.getPublicKey().length() == KEY_LENGTH;
//		log.info("ALICE PUBLIC: "+bob.length());
//		assert alice.getPublicKey().length() == KEY_LENGTH;
//	}

	@Test
	public void testJsonRsaPublicKeySerialization() {
		RSAPublicKey bobPublic = bob.getPublicKey();
		String publicKeyStr = json.toJson(bobPublic);
		info(RSAPublicKey.class, publicKeyStr);
		RSAPublicKey publicKey = json.fromJson(publicKeyStr, RSAPublicKey.class);
		assert publicKey.equals(bobPublic);
		assert !publicKey.equals(alice.getPublicKey());
	}

	@Test
	public void testJsonRsaPrivateKeySerialization() {
		String privateKeyStr = json.toJson(bob);
		info(RSAPrivateKey.class, privateKeyStr);
		RSAPrivateKey privateKey = json.fromJson(privateKeyStr, RSAPrivateKey.class);
		assert privateKey.equals(bob);
		assert !privateKey.equals(alice);
	}

//	@Test
//	public void testSshRsaPublicKeySerialization() {
//		SshRsaKeySerializer serializer = new SshRsaKeySerializer();
//		RSAPublicKey bobPublic = bob.getPublicKey();
//		String publicKeyStr = serializer.serializePublicKey(bobPublic);
//		log.info("ssh-rsa " + publicKeyStr);
//		RSAPublicKey publicKey = serializer.deserializePublicKey(publicKeyStr);
//		assert publicKey.equals(bobPublic);
//		assert !publicKey.equals(alice.getPublicKey());
//	}
}
