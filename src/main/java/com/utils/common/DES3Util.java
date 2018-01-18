package com.utils.common;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;

/**
 * <p>
 * 3DES加密解密
 * key长度为24个字符，不足可补零
 * IV向量必须为8位
 * 使用Base64.encodeToString(byte[], Base64.NO_WRAP);来将加密后的byte[]转为String
 * 使用Base64.decode(String, Base64.NO_WRAP);将转为String的加密字符转换为byte[]
 * new String(byte[])将解密后的byte[]转为String
 * </p>
 *
 * @author wangliang
 * @since 2018/1/15
 */
@Slf4j
public class DES3Util {

    private static final byte[] key = ("f510b8737344cddbca1c8564").getBytes();
    private static final byte[] keyiv = {'f', 'o', 'a', 'o', 'c', 'u', 'e', 'n'};

    public static void main(String[] args) throws Exception {

        byte[] key = ("f510b8737344cddbca1c8564").getBytes();
        // byte[] keyiv = {0x66,0x6f,0x61,0x6f,0x63,0x75,0x65,0x6e};
        byte[] keyiv = {'f', 'o', 'a', 'o', 'c', 'u', 'e', 'n'};

        byte[] data = "中国ABCabc1234".getBytes("UTF-8");

        System.out.println("ECB加密解密");
        byte[] str3 = des3EncodeECB(key, data);
        byte[] str4 = ees3DecodeECB(key, str3);
        System.out.println(new BASE64Encoder().encode(str3));
        System.out.println(new String(str4, "UTF-8"));

        System.out.println("<=============>");

        System.out.println("CBC加密解密");
        byte[] str5 = des3EncodeCBC(key, keyiv, data);
        byte[] str6 = des3DecodeCBC(key, keyiv, str5);
        System.out.println(new BASE64Encoder().encode(str5));
        System.out.println(new String(str6, "UTF-8"));

    }

    /**
     * ECB加密,不要IV
     *
     * @param key  密钥
     * @param data 明文
     * @return Base64编码的密文
     * @throws Exception
     */
    public static byte[] des3EncodeECB(byte[] key, byte[] data)
            throws Exception {
        byte[] bOut = null;
        try {
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(key);
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");

            cipher.init(Cipher.ENCRYPT_MODE, deskey);
            bOut = cipher.doFinal(data);
        } catch (Exception e) {
            log.error("des3EncodeECB error : {}", e);
        }
        return bOut;
    }

    /**
     * ECB加密,不要IV
     *
     * @param data 明文
     * @return 密文
     * @throws Exception
     */
    public static String des3EncodeECB(String data) {
        String out = null;
        try {
            byte[] bytes = data.getBytes("UTF-8");
            out = new BASE64Encoder().encode(des3EncodeECB(key, bytes));
        } catch (Exception e) {
            log.error("des3EncodeECB error : {}", e);
        }
        return out;
    }

    /**
     * ECB解密,不要IV
     *
     * @param key  密钥
     * @param data Base64编码的密文
     * @return 明文
     * @throws Exception
     */
    public static byte[] ees3DecodeECB(byte[] key, byte[] data) {
        byte[] bOut = null;
        try {
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(key);
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");

            cipher.init(Cipher.DECRYPT_MODE, deskey);

            bOut = cipher.doFinal(data);
        } catch (Exception e) {
            log.error("ees3DecodeECB error : {}", e);
        }
        return bOut;
    }

    /**
     * CBC加密
     *
     * @param key   密钥
     * @param keyiv IV
     * @param data  明文
     * @return Base64编码的密文
     * @throws Exception
     */
    public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data) {
        byte[] bOut = null;
        try {
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(key);
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(keyiv);
            cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
            bOut = cipher.doFinal(data);
        } catch (Exception e) {
            log.error("des3EncodeCBC error : {}", e);
        }
        return bOut;
    }

    /**
     * CBC加密
     *
     * @param data 明文
     * @return 密文
     * @throws Exception
     */
    public static String des3EncodeCBC(String data) {
        String out = null;
        try {
            byte[] bytes = data.getBytes("UTF-8");
            out = new BASE64Encoder().encode(des3EncodeCBC(key, keyiv, bytes));
        } catch (Exception e) {
            log.error("des3EncodeCBC error : {}", e);
        }
        return out;
    }

    /**
     * CBC解密
     *
     * @param key   密钥
     * @param keyiv IV
     * @param data  Base64编码的密文
     * @return 明文
     * @throws Exception
     */
    public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data) {
        byte[] bOut = null;
        try {
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(key);
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(keyiv);

            cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

            bOut = cipher.doFinal(data);
        } catch (Exception e) {
            log.error("des3DecodeCBC error : {}", e);
        }
        return bOut;
    }

}
