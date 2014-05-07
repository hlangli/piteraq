package dk.langli.piteraq;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigInteger;

import javax.crypto.BadPaddingException;

import org.apache.commons.codec.DecoderException;
import org.pitaya.security.Digest;

import com.google.gson.reflect.TypeToken;

import dk.langli.piteraq.hash.HashFunction;
import dk.langli.piteraq.message.Hash;
import dk.langli.piteraq.message.Signature;
import dk.langli.piteraq.message.json.Json;
import dk.langli.piteraq.rsa.RSAPrivateKey;
import dk.langli.piteraq.rsa.RSAPublicKey;
import dk.langli.piteraq.stream.HashInputStream;

public class Piteraq {
	private static final String LF = System.getProperty("line.separator");

	public static void main(String[] args) throws IOException, BadPaddingException, CryptographicException, DecoderException {
		Json json = new Json(false);
		switch(args[0]) {
			case "-s": {
				RSAPrivateKey privateKey = json.fromJson(args[1], RSAPrivateKey.class);
				String hStr = read();
				Digest digest = null;
				BigInteger h = null;
				try {
					Hash hash = json.fromJson(hStr, Hash.class);
					if(hash.getValue() == null) {
						throw new Exception("Message is not a valid hash");
					}
					h = hash.getValue();
					digest = hash.getDigest();
				}
				catch(Exception e) {
					try {
						Type signatureType = new TypeToken<Signature<RSAPublicKey>>() {
				      }.getType();
						Signature<RSAPublicKey> signature = json.fromJson(hStr, signatureType);
						digest = signature.getDigest();
						h = signature.getMessage();
					}
					catch(Exception e1) {
						h = NumberUtil.toBigInteger(hStr);
					}
				}
				BigInteger s = privateKey.sign(h);
				Signature<RSAPublicKey> signature = new Signature<RSAPublicKey>(h, digest, s, privateKey.getPublicKey());
				System.out.println(json.toJson(signature));
				break;
			}
			case "-v": {
				String hStr = read();
				BigInteger m = null;
				try {
					Hash hash = json.fromJson(hStr, Hash.class);
					if(hash.getValue() == null) {
						throw new Exception("Message is not a valid hash");
					}
					m = hash.getValue();
				}
				catch(Exception e) {
					m = NumberUtil.toBigInteger(hStr);
				}
				Type signatureType = new TypeToken<Signature<RSAPublicKey>>() {
		      }.getType();
				Signature<RSAPublicKey> signature = json.fromJson(args[1], signatureType);
				boolean verifies = signature.getMessage().equals(m);
				if(verifies) {
					verifies = signature.verify();
				}
				if(verifies) {
					System.out.println("OK");
				}
				else {
					System.out.println("FAIL");
					System.exit(1);
				}
				break;
			}
			case "-d": {
				Digest digest = getDigest(args[1]);
				@SuppressWarnings("resource")
				HashInputStream hashIn = new HashInputStream(digest, System.in);
				Hash hash = new Hash(hashIn.digest(), digest);
				System.out.println(json.toJson(hash));
				break;
			}
			case "-g": {
				RSAPrivateKey privateKey = new RSAPrivateKey(Integer.parseInt(args[1]));
				System.out.println(json.toJson(privateKey));
				break;
			}
			case "-p": {
				RSAPrivateKey privateKey = json.fromJson(args[1], RSAPrivateKey.class);
				System.out.println(json.toJson(privateKey.getPublicKey()));
				break;
			}
			case "-r": {
				RSAPublicKey publicKey = json.fromJson(args[1], RSAPublicKey.class);
				System.out.println(NumberUtil.toBase64(publicKey.newBlindingFactor()));
				break;
			}
			case "-b": {
				RSAPublicKey publicKey = json.fromJson(args[1], RSAPublicKey.class);
				BigInteger r = NumberUtil.toBigInteger(args[2]);
				String hStr = read();
				Hash hash = json.fromJson(hStr, Hash.class);
				BigInteger h = hash.getValue();
				BigInteger b = publicKey.blind(h, r);
				System.out.println(NumberUtil.toBase64(b));
				break;
			}
			case "-u": {
				String hStr = args[3];
				BigInteger m = null;
				try {
					Hash hash = json.fromJson(hStr, Hash.class);
					if(hash.getValue() == null) {
						throw new Exception("Message is not a valid hash");
					}
					m = hash.getValue();
				}
				catch(Exception e) {
					m = NumberUtil.toBigInteger(hStr);
				}
				RSAPublicKey publicKey = json.fromJson(args[1], RSAPublicKey.class);
				BigInteger r = NumberUtil.toBigInteger(args[2]);
				String bsStr = read();
				Type signatureType = new TypeToken<Signature<RSAPublicKey>>() {
		      }.getType();
				Signature<RSAPublicKey> bs = json.fromJson(bsStr, signatureType);
				BigInteger ub = publicKey.unblind(bs.getSignature(), r);
				Signature<RSAPublicKey> ubs = new Signature<RSAPublicKey>(m, ub, bs.getVerificationKey());
				System.out.println(json.toJson(ubs));
				break;
			}
			default: {
				help(0);
				break;
			}
		}
	}
	
	private static String read() throws IOException {
		byte[] buf = new byte[8192];
		int len = -1;
		String str = "";
		while((len = System.in.read(buf)) != -1) {
			str += new String(buf, 0, len);
		}
		return str;
	}
	
	private static Digest getDigest(String name) {
		HashFunction hashFunction = HashFunction.find(name);
		return hashFunction != null ? hashFunction.newDigest() : null;
	}

	private static void help(int status) {
		String classname = Piteraq.class.getSimpleName();
		String str = "Usage: " + classname + " [-h|-d <hash>|-s <privkey>|-v <signature>|-r <pubkey>|-b <factor> <pubkey>|-u <pubkey> <factor> <message>|-g <length>|-p <privkey>]" + LF;
		str += LF;
		str += "-h     Help" + LF;
		str += "-d     Digest input and output the hash" + LF;
		str += "-s     Sign input with the private key" + LF;
		str += "-v     Verify input with the signature" + LF;
		str += "-r     Generate random factor for blinding with the public key" + LF;
		str += "-b     Blind input with the public key and the random factor" + LF;
		str += "-u     Unblind input with the public key and the random factor and replace the blinded message with the real one" + LF;
		str += "-g     Generate private key of length" + LF;
		str += "-p     Get public key from private key" + LF;
		System.err.println(str);
		System.exit(status);
	}
}
