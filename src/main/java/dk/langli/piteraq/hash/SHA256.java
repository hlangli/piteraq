package dk.langli.piteraq.hash;

import org.pitaya.security.Digests;

public class SHA256 extends InstantiableDigest {
	public SHA256() {
		super(Digests.sha256());
	}
}
