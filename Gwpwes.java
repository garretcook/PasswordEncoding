/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.apache.commons.codec.binary.Base64
 */
package com.ricoh.mdm.dc.net.protocol.soap;
 
import org.apache.commons.codec.binary.Base64;
//import org.apache.commons.*;
 
public class GwpwesCharacterEncoding {
    private static final int PRECODE = 9;
    private static final int MAX_ENCODED_SIZE = 144;
    private static final int GW_CRYPT_SCR_DATA_MIN = 16;
    private static final int RAND_MAX = 32678;
    private static final int GW_CRYPT_SCR_ROTATION = 2;
    private static final byte GW_CRYPT_SCR_XOR_CHAR = 114;
    public static final String GW_PASSWORD_ENCODING_SCHEMA_000 = "gwpwes000";
    public static final String GW_PASSWORD_ENCODING_SCHEMA_001 = "gwpwes001";
    public static final String GW_PASSWORD_ENCODING_SCHEMA_002 = "gwpwes002";
    public static final String GW_PASSWORD_ENCODING_SCHEMA_003 = "gwpwes003";
    public static final String GW_PASSWORD_ENCODING_SCHEMA_004 = "gwpwes004";
    public static final String GW_PASSWORD_ENCODING_SCHEMA_005 = "gwpwes005";
    public static final String GW_PASSWORD_ENCODING_SCHEMA_006 = "gwpwes006";
    public static final String GW_PASSWORD_ENCODING_SCHEMA_007 = "gwpwes007";
    public static final String GW_PASSWORD_ENCODING_SCHEMA_008 = "gwpwes008";
    public static final String GW_PASSWORD_ENCODING_SCHEMA_009 = "gwpwes009";
    public static final String GW_PASSWORD_ENCODING_SCHEMA_010 = "gwpwes010";
    public static final String GW_PASSWORD_ENCODING_SCHEMA_011 = "gwpwes011";
    public static final String GW_PASSWORD_ENCODING_SCHEMA_012 = "gwpwes012";
    public static final String GW_PASSWORD_ENCODING_SCHEMA_013 = "gwpwes013";
    public static final String GW_PASSWORD_ENCODING_SCHEMA_OMITTED = "omitted";
 
    public static String encodeGwpwes(String type, String code) {
        String result = null;
        if (code == null || type == null) {
            return result;
        }
        if ("gwpwes000".equals(type)) {
            result = GwpwesCharacterEncoding.encodeGwpwes000(code);
        } else if ("gwpwes002".equals(type)) {
            result = GwpwesCharacterEncoding.encodeGwpwes002(code);
        } else if ("gwpwes003".equals(type)) {
            result = GwpwesCharacterEncoding.encodeGwpwes003(code);
        } else if ("omitted".equals(type)) {
            result = code;
        }
        return result;
    }
 
    public static String decodeGwpwes(String type, String code) {
        String result = null;
        if (code == null || type == null) {
            return result;
        }
        if ("gwpwes000".equals(type)) {
            result = GwpwesCharacterEncoding.decodeGwpwes000(code);
        } else if ("gwpwes002".equals(type)) {
            result = GwpwesCharacterEncoding.decodeGwpwes002(code);
        } else if ("gwpwes003".equals(type)) {
            result = GwpwesCharacterEncoding.decodeGwpwes003(code);
        } else if ("omitted".equals(type)) {
            result = code;
        }
        return result;
    }
 
    private static String encodeGwpwes000(String code) {
        byte[] protectCode;
        try {
            protectCode = code.getBytes("UTF-8");
        }
        catch (Throwable e) {
            return null;
        }
        return new String(Base64.encodeBase64((byte[])protectCode));
    }
 
    private static String encodeGwpwes002(String code) {
        return GwpwesCharacterEncoding.encodeGwpwes002(code, 0);
    }
 
    private static String encodeGwpwes002(String code, int codeSize) {
        byte[] protectCode;
        try {
            protectCode = code.getBytes("UTF-8");
        }
        catch (Throwable e) {
            return null;
        }
        int encodeSize = codeSize;
        if (protectCode.length >= codeSize) {
            encodeSize = protectCode.length + 9;
        }
        byte[] simple = new byte[encodeSize];
        int diffuseCnt = 0;
        int simpleCnt = 0;
        if (protectCode.length < encodeSize - 1) {
            for (diffuseCnt = 0; diffuseCnt < encodeSize - 1 - protectCode.length; ++diffuseCnt) {
                simple[diffuseCnt] = (byte)(Math.random() * 25.0 + 97.0);
            }
        }
        simple[diffuseCnt++] = 122;
        for (simpleCnt = diffuseCnt; simpleCnt < protectCode.length + diffuseCnt; ++simpleCnt) {
            simple[simpleCnt] = protectCode[simpleCnt - diffuseCnt];
        }
        byte[] encrypt = new byte[simpleCnt];
        for (int i = 0; i < simpleCnt; ++i) {
            byte work = 0;
            work = (byte)((simple[i] & 192) >>> 6 | (simple[i] & 63) << 2);
            encrypt[i] = (byte)((work & 240) >>> 4 | (work & 15) << 4);
        }
        return new String(Base64.encodeBase64((byte[])encrypt));
    }
 
    private static String encodeGwpwes003(String code) {
        byte[] protectCode;
        int i;
        try {
            protectCode = code.getBytes("UTF-8");
        }
        catch (Throwable e) {
            return null;
        }
        if (144 < protectCode.length + 16) {
            return null;
        }
        int len_rand = -1;
        len_rand = (int)(16.0 + (double)(128 - protectCode.length) * Math.random() - 1.0);
        byte[] data_out = new byte[145];
        for (i = 0; i < len_rand; ++i) {
            int r = (int)(Math.random() * 10.0);
            data_out[i] = r < 3 ? (byte)(Math.random() * 24.0 + 97.0) : (r < 6 ? (byte)(Math.random() * 25.0 + 65.0) : (byte)(Math.random() * 9.0 + 48.0));
        }
        if (len_rand >= 0) {
            data_out[len_rand] = 122;
        }
        System.arraycopy(protectCode, 0, data_out, ++len_rand, protectCode.length);
        for (i = 0; i < (len_rand += protectCode.length); ++i) {
            byte work = 0;
            work = (byte)((data_out[i] & 192) >>> 6 | (data_out[i] & 63) << 2);
            data_out[i] = (byte)((work & 240) >>> 4 | (work & 15) << 4);
            data_out[i] = (byte)(data_out[i] ^ 114);
        }
        byte[] encrypt = new byte[len_rand];
        System.arraycopy(data_out, 0, encrypt, 0, len_rand);
        return new String(Base64.encodeBase64((byte[])encrypt));
    }
 
    private static String decodeGwpwes000(String code) {
        String ret;
        try {
            ret = new String(Base64.decodeBase64((byte[])code.getBytes()));
        }
        catch (Throwable e) {
            ret = null;
        }
        return ret;
    }
 
    private static String decodeGwpwes002(String code) {
        String ret = null;
        Object base64decode = null;
        try {
            base64decode = Base64.decodeBase64((byte[])code.getBytes());
        }
        catch (Throwable e) {
            return ret;
        }
        if (null != base64decode && 0 != base64decode.length) {
            int offset;
            byte[] simple = new byte[base64decode.length];
            for (int i = 0; i < base64decode.length; ++i) {
                byte work = 0;
                work = (byte)((base64decode[i] & 240) >>> 4 | (base64decode[i] & 15) << 4);
                simple[i] = (byte)((work & 3) << 6 | (work & 252) >>> 2);
            }
            int count = simple.length;
            for (offset = 0; offset < count && simple[offset] != 122; ++offset) {
            }
            if (++offset <= count) {
                char[] data = new char[count];
                for (int i2 = 0; i2 < count; ++i2) {
                    data[i2] = simple[i2];
                }
                ret = String.copyValueOf(data, offset, count - offset);
            }
        }
        return ret;
    }
 
    private static String decodeGwpwes003(String code) {
        return null;
    }
}
