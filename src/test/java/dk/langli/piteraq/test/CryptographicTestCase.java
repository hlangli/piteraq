package dk.langli.piteraq.test;

import junit.framework.TestCase;

import org.pitaya.security.Digest;
import org.pitaya.security.Digests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.langli.piteraq.message.json.Json;
import dk.langli.piteraq.rsa.RSAPrivateKey;

public abstract class CryptographicTestCase extends TestCase {
	protected Logger log = LoggerFactory.getLogger(getClass());
	protected static final int KEY_LENGTH = 2048;
	protected Json json = new Json();
	protected static RSAPrivateKey bob = new RSAPrivateKey(KEY_LENGTH);
	protected static RSAPrivateKey alice = new RSAPrivateKey(KEY_LENGTH);
	protected static String MSG = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
	protected static String MSG_SHA1 = "19afa2a4a37462c7b940a6c4c61363d49c3a35f4";
	protected static String MSG_SHA_256 = "2c7c3d5f244f1a40069a32224215e0cf9b42485c99d80f357d76f006359c7a18";
	protected static String MSG_SHA_512 = "f41d92bc9fc1157a0d1387e67f3d0893b70f7039d3d46d8115b5079d45ad601159398c79c281681e2da09bf7d9f8c23b41d1a0a3c5b528a7f2735933a4353194";
	protected static Digest digest = Digests.keccak512();
	
	protected void info(Class<?> clazz, String msg) {
		log.info(clazz.getSimpleName() + " " + msg);
	}
}
