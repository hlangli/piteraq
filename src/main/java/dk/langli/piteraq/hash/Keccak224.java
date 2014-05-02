package dk.langli.piteraq.hash;

import org.pitaya.security.Digests;

public class Keccak224 extends InstantiableDigest {
	public Keccak224() {
		super(Digests.keccak224());
	}
}
