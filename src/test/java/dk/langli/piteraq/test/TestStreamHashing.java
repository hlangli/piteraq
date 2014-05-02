package dk.langli.piteraq.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.crypto.BadPaddingException;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import org.pitaya.security.Digest;

import dk.langli.piteraq.CryptographicException;
import dk.langli.piteraq.NumberUtil;
import dk.langli.piteraq.hash.SHA1;
import dk.langli.piteraq.hash.SHA256;
import dk.langli.piteraq.hash.SHA512;
import dk.langli.piteraq.stream.HashInputStream;

public class TestStreamHashing extends CryptographicTestCase {
	@Test
	public void testSHA1() throws BadPaddingException, IOException, CryptographicException {
		assert hash(new SHA1()).equals(MSG_SHA1);
	}

	@Test
	public void testSHA256() throws BadPaddingException, IOException, CryptographicException {
		assert hash(new SHA256()).equals(MSG_SHA_256);
	}

	@Test
	public void testSHA512() throws BadPaddingException, IOException, CryptographicException {
		assert hash(new SHA512()).equals(MSG_SHA_512);
	}

	private String hash(Digest digest) throws BadPaddingException, IOException, CryptographicException {
		@SuppressWarnings("resource")
		HashInputStream hashIn = new HashInputStream(digest, new ByteArrayInputStream(MSG.getBytes()));
		byte[] hashBytes = NumberUtil.toBytes(hashIn.digest(), digest.length());
		String hash = Hex.encodeHexString(hashBytes);
		return hash;
	}
}
