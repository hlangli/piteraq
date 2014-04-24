package dk.langli.piteraq.trash;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;

import org.apache.commons.codec.binary.Base64;

import dk.langli.piteraq.rsa.RSAPrivateKey;
import dk.langli.piteraq.rsa.RSAPublicKey;

public class SshRsaKeySerializer implements KeySerializer<RSAPrivateKey, RSAPublicKey> {
	private static final byte[] KEY_TYPE = "ssh-rsa".getBytes();
	private static final int UINT32_LENGTH = 4;

	@Override
	public String serializePrivateKey(RSAPrivateKey privateKey) {
		// TODO DER encoded ASN.1. Seriously?  Let's not go there.
		return null;
	}

	/*
	* 00 00 00 07             The length in bytes of the next field
	* 73 73 68 2d 72 73 61    The key type (ASCII encoding of "ssh-rsa")
	* 00 00 00 03             The length in bytes of the public exponent
	* 01 00 01                The public exponent (usually 65537, as here)
	* 00 00 01 01             The length in bytes of the modulus (here, 257)
	* 00 c3 a3...             The modulus
	*/
	@Override
	public String serializePublicKey(RSAPublicKey publicKey) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			out.write(uint32(BigInteger.valueOf(KEY_TYPE.length)));
			out.write(KEY_TYPE);
			BigInteger e = publicKey.getExponent();
			byte[] data = e.toByteArray();
			out.write(uint32(BigInteger.valueOf(data.length)));
			out.write(data);
			BigInteger m = publicKey.getModulus();
			data = m.toByteArray();
			out.write(uint32(BigInteger.valueOf(data.length)));
			out.write(data);
		}
		catch(IOException e) {
			out = null;
		}
		return out != null ? Base64.encodeBase64String(out.toByteArray()) : null;
	}

	@Override
	public RSAPrivateKey deserializePrivateKey(String privateKey) {
		// TODO DER encoded ASN.1. Seriously?  Let's not go there.
		return null;
	}

	@Override
	public RSAPublicKey deserializePublicKey(String publicKey) {
		byte[] data = Base64.decodeBase64(publicKey);
		int keyTypeLength = uint32(data).intValue();
		int pos = UINT32_LENGTH;
		pos += keyTypeLength;
		int exponentLength = uint32(data, pos).intValue();
		pos += UINT32_LENGTH;
		byte[] exponent = fragment(data, pos, exponentLength);
		pos += exponentLength;
		int modulusLength = uint32(data, pos).intValue();
		pos += UINT32_LENGTH;
		byte[] modulus = fragment(data, pos, modulusLength);
		return new RSAPublicKey(new BigInteger(modulus), new BigInteger(exponent));
	}
	
	private byte[] fragment(byte[] b, int srcPos, int length) {
		byte[] fragment = new byte[length];
		System.arraycopy(b, srcPos, fragment, 0, length);
		return fragment;
	}
	
	private byte[] uint32(BigInteger i) {
		return pad(i.toByteArray(), UINT32_LENGTH);
	}
	
	private BigInteger uint32(byte[] b) {
		return uint32(b, 0);
	}

	private BigInteger uint32(byte[] b, int pos) {
		return new BigInteger(fragment(b, pos, UINT32_LENGTH));
	}
	
	private byte[] pad(byte[] b, int newLength) {
		byte[] padb = b;
		int blen = b.length;
		if(blen < newLength) {
			padb = new byte[newLength];
			System.arraycopy(b, 0, padb, newLength-blen, blen);
			for(int i=0; i<newLength-blen; i++) {
				padb[i] = 0;
			}
		}
		return padb;
	}
}
