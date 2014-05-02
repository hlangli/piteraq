package dk.langli.piteraq.hash;

import org.pitaya.security.Digests;

public class SHA1 extends InstantiableDigest {
	public SHA1() {
		super(Digests.sha1());
	}
}
