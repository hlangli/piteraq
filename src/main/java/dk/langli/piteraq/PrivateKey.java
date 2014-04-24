package dk.langli.piteraq;


public interface PrivateKey<U extends PublicKey> extends SigningKey, DecryptionKey {
	public U getPublicKey();
}
