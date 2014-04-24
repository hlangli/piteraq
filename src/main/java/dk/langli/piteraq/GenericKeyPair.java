package dk.langli.piteraq;

import java.security.UnrecoverableKeyException;

public class GenericKeyPair<P, U> {
	private P privateKey = null;
	private U publicKey = null;
	
	public GenericKeyPair(P privateKey, U publicKey) throws UnrecoverableKeyException {
		init(privateKey, publicKey);
	}
	
	private void init(P privateKey, U publicKey) throws UnrecoverableKeyException {
		if(privateKey != null) {
			if(publicKey != null) {
				this.privateKey = privateKey;
				this.publicKey = publicKey;
			}
			else {
				throw new UnrecoverableKeyException("The public key is null");
			}
		}
		else {
			throw new UnrecoverableKeyException("The private key is null");
		}
	}
	
	public P getPrivate() {
		return privateKey;
	}

	public U getPublic() {
		return publicKey;
	}
}
