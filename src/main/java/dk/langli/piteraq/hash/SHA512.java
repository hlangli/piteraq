package dk.langli.piteraq.hash;

import org.pitaya.security.Digests;

public class SHA512 extends InstantiableDigest {
	public SHA512() {
		super(Digests.sha512());
	}
}
