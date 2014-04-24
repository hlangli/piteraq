package dk.langli.piteraq.message.json;

import java.io.IOException;

import org.pitaya.security.Digest;
import org.pitaya.security.Digests;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class DigestTypeAdapter extends TypeAdapter<Digest> {
	@Override
	public void write(JsonWriter out, Digest value) throws IOException {
		out.value(value.toString());
	}

	@Override
	public Digest read(JsonReader in) throws IOException {
		Digest digest = null;
		switch(in.nextString()) {
			case "MD2":
				digest = Digests.md2();
			case "MD4":
				digest = Digests.md4();
			case "MD5":
				digest = Digests.md5();
			case "SHA1":
				digest = Digests.sha1();
			case "SHA-256":
				digest = Digests.sha256();
			case "SHA-512":
				digest = Digests.sha512();
			case "Keccak-224":
				digest = Digests.keccak224();
			case "Keccak-256":
				digest = Digests.keccak256();
			case "Keccak-384":
				digest = Digests.keccak384();
			case "Keccak-512":
				digest = Digests.keccak512();
		}
		return digest;
	}
}
