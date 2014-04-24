package dk.langli.piteraq;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;

import org.pitaya.security.Digest;

import dk.langli.piteraq.stream.HashInputStream;

public class Digester {
	public static BigInteger digest(String message, Digest digest) throws IOException {
		ByteArrayInputStream in = new ByteArrayInputStream(message.getBytes());
		@SuppressWarnings("resource")
		HashInputStream hashIn = new HashInputStream(digest, in);
		return hashIn.digest();
	}
}
