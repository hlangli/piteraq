package dk.langli.piteraq.padding;

public class OAEP {
//	  private final static boolean RSA_NOPAD_USED = true;
//	// 200 is maximum value required if RSA2048bits OAEP is used
//	  private final static short MAX_MASK_ARRAY_LENGTH = (short) 200;
//	  public static final short OAEP_DECODE_FAIL                   = (short) 0x6003;
//
//	    //RSAEngine                       engine;
//	    Cipher                       engine;
//	    private byte[]               defHash;
//	    private byte[]               tempHash;
//	    private byte[]               C;
//	    byte[]                       maskSource;
//
//	    //private Digest                  hash;
//	    //private Digest                  mgf1Hash;
//	    private MessageDigest        hash;
//	    private MessageDigest        mgf1Hash;
//	    private boolean              forEncryption;
//
//	    public void init(
////	        boolean             forEncryption,
//	        Cipher              engine,
//	        MessageDigest       hash,
//	        byte[]              encodingParams,
//	        byte[]              externalMaskSourceArray)
//	    {
//	        this.engine = engine;
//	        this.hash = hash;
//	        this.mgf1Hash = hash;
////	        this.forEncryption = forEncryption;
//	        tempHash = new byte[hash.getLength()];
//	        //tempHash = JCSystem.makeTransientByteArray(hash.getLength(), JCSystem.CLEAR_ON_RESET);
//	        defHash = new byte[hash.getLength()];
//	        //defHash = JCSystem.makeTransientByteArray(hash.getLength(), JCSystem.CLEAR_ON_RESET);
//	        C = new byte[4];
//	        //C = JCSystem.makeTransientByteArray((byte) 4, JCSystem.CLEAR_ON_RESET);
//	        if (externalMaskSourceArray == null) maskSource = JCSystem.makeTransientByteArray(MAX_MASK_ARRAY_LENGTH, JCSystem.CLEAR_ON_RESET);
//	        else maskSource = externalMaskSourceArray;
//	        if (encodingParams != null) {
//	            hash.doFinal(encodingParams, (short) 0, (short) encodingParams.length, defHash, (short) 0);
//	        }
//	        else hash.doFinal(encodingParams, (short) 0, (short) 0, defHash, (short) 0);
//	    }
//	/**/
//	/* // Use this function for pure-Java debugging (outside card, no javaCard)
//	    public void init(
//	        boolean             forEncryption,
//	        RSAEngine           engine,
//	        Digest              hash,
//	        byte[]              encodingParams)
//	    {
//	        this.engine = engine;
//	        this.hash = hash;
//	        this.mgf1Hash = hash;
//	        this.forEncryption = forEncryption;
//	        defHash = new byte[hash.getDigestSize()];
//	        tempHash = new byte[hash.getDigestSize()];
//	        maskSource = new byte[200]; // todo: 200 is maximum value we will be using with our implementation of RSA2048 OAEP
//	        C = new byte[4];
//	        if (encodingParams != null)
//	        {
//	            hash.update(encodingParams, 0, encodingParams.length);
//	        }
//
//	        hash.doFinal(defHash, 0);
//	    }
//	/**/
//	    public short decodeBlock(
//	            byte[]    in,
//	            short     inOff,
//	            short     inLen)  throws ISOException
//	        {
//	            short decLen = engine.doFinal(in, inOff, inLen, in, inOff);
//
//	            // WE MUST REMOVE TRAILING ZEROES FROM DECRYPTED RESULT
//	            // IF RSA_PKCS1 IS USED, THAN IT WAS ALREADY PERFORMED
//	            // IF RSA_NOPAD IS USED, REMOVE FIRST BYTE
//	            if (RSA_NOPAD_USED) {
//	              if (in[inOff] != 0) {
//	                throw new ISOException(OAEP_DECODE_FAIL);
//	              }
//	              else {
//	                inOff++; decLen--;
//	              }
//	            }
//
//	            if (decLen < (short) ((2 * defHash.length) + 1))
//	            {
//	                throw new ISOException(OAEP_DECODE_FAIL);
//	            }
//
//
//	            //
//	            // unmask the seed.
//	            //
//	            short maskSourceLength = (short) (decLen -  (short) defHash.length);
//	            //byte[] maskSource = new byte[maskSourceLength];
//	            Util.arrayCopyNonAtomic(in, (short) (inOff + defHash.length), maskSource, (short) 0, (short) maskSourceLength);
//	            maskGeneratorFunction1(maskSource, (short) 0, maskSourceLength, (short) defHash.length, in, inOff);
//
//
//	            //
//	            // unmask the message block.
//	            //
//	            maskSourceLength = (short) defHash.length;
//	            Util.arrayCopyNonAtomic(in, inOff, maskSource, (short) 0, (short) maskSourceLength);
//	            maskGeneratorFunction1(maskSource, (short) 0, maskSourceLength, (short) (decLen - defHash.length), in, (short) (inOff + defHash.length));
//
//	            //
//	            // check the hash of the encoding params.
//	            //
//	            for (short i = 0; i != defHash.length; i++) {
//	                if (defHash[i] != in[(short) (inOff + defHash.length + i)]) {
//	                    throw new ISOException(OAEP_DECODE_FAIL);
//	                }
//	            }
//
//	            //
//	            // find the data block
//	            //
//	            short start;
//
//	            for (start = (short) (inOff + 2 * defHash.length); start < (short) (inOff + decLen); start++) {
//	                if (in[start] == 1 || in[start] != 0) break;
//	            }
//
//	            if (start >= (short) (inOff + decLen - 1)) throw new ISOException(OAEP_DECODE_FAIL);
//	            if (in[start] != 1) throw new ISOException(OAEP_DECODE_FAIL);
//
//	            start++;
//
//	            //
//	            // extract the data block
//	            //
//	            short outputLength = (short) (decLen - start);
//	            start -= inOff;
//
//	            if (RSA_NOPAD_USED) {
//	              for (short i = inOff; i < (short) (inOff + outputLength); i++) in[(short) (i - 1)] = in[(short) (start + i)];
//	              outputLength--;
//	            }
//	            else {
//	              for (short i = inOff; i < (short) (inOff + outputLength); i++) in[i] = in[(short) (start + i)];
//	            }
//	            return outputLength;
//	            /**/
//	        }
//
//	        private short maskGeneratorFunction1(
//	            byte[]  Z,
//	            short   zOff,
//	            short   zLen,
//	            short   length,
//	            byte[]  bufferToMask,
//	            short   bufferToMaskOffset)
//	        {
//	            short counter = 0;
//
//	            hash.reset();
//	            mgf1Hash.reset();
//
//	            // COPY INPUT
//
//	            // ASSUMPTION: WE WILL NOT PROCESS MORE THEN 128 BLOCKS
//	            C[0] = (byte)0;
//	            C[1] = (byte)0;
//	            C[2] = (byte)0;
//	            C[3] = (byte)-1;
//	            //for (counter = 0; counter < (short) (length / tempHash.length); counter++) {
//	            do {
//	            	C[3] = (byte) (C[3] + 1);
//
//	                mgf1Hash.update(Z, zOff, zLen);
//	                mgf1Hash.doFinal(C, (short) 0, (short) C.length, tempHash, (short) 0);
//	                //mgf1Hash.update(C, 0, C.length);
//	                //mgf1Hash.doFinal(tempHash, 0);
//
//	                // MASK/UNMASK PART OF GIVEN ARRAY
//	                for (short i = 0; i < (short) tempHash.length; i++) bufferToMask[(short) (bufferToMaskOffset + i)] ^= tempHash[i];
//	                bufferToMaskOffset += (short) (tempHash.length);
//	            }
//	            while (++counter < (short) (length / tempHash.length));
//
//	            if ((short) (counter * tempHash.length) < length)
//	            {
//	               	C[3] = (byte) (C[3] + 1);
//
//	                mgf1Hash.update(Z, zOff, zLen);
//	                mgf1Hash.doFinal(C, (short) 0, (short) C.length, tempHash, (short) 0);
//	                //mgf1Hash.update(C, 0, C.length);
//	                //mgf1Hash.doFinal(tempHash, 0);
//
//	                for (short i = 0; i < (short) tempHash.length; i++) bufferToMask[(short) (bufferToMaskOffset + i)] ^= tempHash[i];
//	                bufferToMaskOffset += (short) tempHash.length;
//	            }
//	            return (short) 0;
//	        }
	}
