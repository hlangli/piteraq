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
	protected Json json = new Json();
	protected static RSAPrivateKey bob = new RSAPrivateKey(2048);
	protected static RSAPrivateKey alice = new RSAPrivateKey(2048);
	protected static String MSG = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
	protected static Digest digest = Digests.keccak512();
	
	protected void info(Class<?> clazz, String msg) {
		log.info(clazz.getSimpleName() + " " + msg);
	}
}
