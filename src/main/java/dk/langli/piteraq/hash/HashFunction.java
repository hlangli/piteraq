package dk.langli.piteraq.hash;

import org.pitaya.security.Digest;

public enum HashFunction {
	MD5("MD5", MD5.class),
	SHA1("SHA1", SHA1.class),
	SHA_256("SHA-256", SHA256.class),
	SHA_512("SHA-512", SHA512.class),
	KECCAK_224("Keccak-224", Keccak224.class),
	KECCAK_256("Keccak-256", Keccak256.class),
	KECCAK_384("Keccak-384", Keccak384.class),
	KECCAK_512("Keccak-512", Keccak512.class);
	
	private final String name;
	private final Class<? extends Digest> digestClass;

	private HashFunction(String name, Class<? extends Digest> hashClass) {
		this.name = name;
		this.digestClass = hashClass;
	}

	@Override
	public String toString() {
		return name;
	}
	
	public Digest newDigest() {
		Digest digest = null;
		try {
			digest = digestClass.newInstance();
		}
		catch(InstantiationException | IllegalAccessException e) {
		}
		return digest;
	}
	
	public String getName() {
		return name;
	}
	
	public static HashFunction find(String name) {
		HashFunction[] values = values();
		for(HashFunction value: values) {
			if(value.getName().equals(name)) {
				return value;
			}
		}
		return null;
	}
}
