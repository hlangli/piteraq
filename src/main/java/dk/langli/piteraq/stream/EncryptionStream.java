package dk.langli.piteraq.stream;

import java.io.FilterOutputStream;
import java.io.OutputStream;

public abstract class EncryptionStream extends FilterOutputStream {
	public EncryptionStream(OutputStream out) {
		super(out);
	}
}
