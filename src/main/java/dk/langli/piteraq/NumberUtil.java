package dk.langli.piteraq;

import java.math.BigInteger;

import org.apache.commons.codec.binary.Base64;

public class NumberUtil {
	public static String toBase64(BigInteger value) {
		return Base64.encodeBase64String(value.toByteArray());
	}

	public static BigInteger toBigInteger(String base64) {
		return new BigInteger(Base64.decodeBase64(base64));
	}
	
	public static BigInteger toBigInteger(byte[] bytes) {
		return new BigInteger(1, bytes);
	}

	public static byte[] toBytes(BigInteger value) {
		return value.toByteArray();
	}

	public static byte[] toBytes(BigInteger value, int length) {
		byte[] buf = toBytes(value);
		return trimNumber(buf, length);
	}
	
	public static byte[] trimNumber(byte[] value, int length) {
		byte[] bytes = new byte[length];
		System.arraycopy(value, value.length-length, bytes, 0, length);
		return bytes;
	}
}
