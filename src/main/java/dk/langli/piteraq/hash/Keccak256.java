package dk.langli.piteraq.hash;

import org.pitaya.security.Digests;

public class Keccak256 extends InstantiableDigest {
	public Keccak256() {
		super(Digests.keccak256());
	}
}
