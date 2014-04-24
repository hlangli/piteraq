package dk.langli.piteraq.trash;

import dk.langli.piteraq.PrivateKey;
import dk.langli.piteraq.PublicKey;

public interface KeySerializer<P extends PrivateKey<?>, U extends PublicKey> {
	public String serializePrivateKey(P privateKey);
	public String serializePublicKey(U publicKey);
	public P deserializePrivateKey(String privateKey);
	public U deserializePublicKey(String publicKey);
}
