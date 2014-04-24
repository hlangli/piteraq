package dk.langli.piteraq.message.json;

import java.lang.reflect.Type;
import java.math.BigInteger;

import org.pitaya.security.Digest;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Json {
	private Gson gson = null;
	
	public Json() {
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		builder.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY);
		builder.registerTypeAdapter(BigInteger.class, new BigIntegerTypeAdapter());
		builder.registerTypeAdapter(Digest.class, new DigestTypeAdapter());
		gson = builder.create();
	}
	
	public String toJson(Object src) {
		return gson.toJson(src);
	}

	public <T> T fromJson(String src, Class<T> classOfT) {
		return gson.fromJson(src, classOfT);
	}

	public <T> T fromJson(String src, Type typeOfT) {
		return gson.fromJson(src, typeOfT);
	}
}
