package dk.langli.piteraq.hash;

import org.pitaya.security.Digests;

public class Keccak384 extends InstantiableDigest {
	public Keccak384() {
		super(Digests.keccak384());
	}
}
