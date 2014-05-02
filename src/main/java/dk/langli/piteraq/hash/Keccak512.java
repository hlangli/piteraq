package dk.langli.piteraq.hash;

import org.pitaya.security.Digests;

public class Keccak512 extends InstantiableDigest {
	public Keccak512() {
		super(Digests.keccak512());
	}
}
