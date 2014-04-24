package dk.langli.piteraq.test;

import org.junit.Test;

import dk.langli.piteraq.rsa.RSAPrivateKey;
import dk.langli.piteraq.rsa.RSAPublicKey;

public class TestKeySerialization extends CryptographicTestCase {
	@Override
	protected void setUp() throws Exception {
	}

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
