package dk.langli.piteraq.hash;

import org.pitaya.security.Digests;

public class MD5 extends InstantiableDigest {
	public MD5() {
		super(Digests.md5());
	}
}
