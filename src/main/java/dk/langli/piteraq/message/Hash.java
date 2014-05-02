package dk.langli.piteraq.message;

import java.math.BigInteger;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.pitaya.security.Digest;

import dk.langli.piteraq.NumberUtil;

public class Hash {
	private BigInteger h = null;
	private Digest digest = null;
	
	public Hash(BigInteger value, Digest digest) {
		this.h = value;
		this.digest = digest;
	}
	
	public BigInteger getValue() {
		return h;
	}
	
	public Digest getDigest() {
		return digest;
	}
	
	public byte[] getBytes() {
		return NumberUtil.toBytes(h, digest.length());
	}
	
	@Override
	public String toString() {
		return Hex.encodeHexString(getBytes());
	}
	
	@Override
	public boolean equals(Object obj) {
		return hashCode() == obj.hashCode();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder(17, 77);
		return hcb.append(h).append(digest.toString()).toHashCode();
	}
}
