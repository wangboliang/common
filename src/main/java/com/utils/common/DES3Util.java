package com.utils.common;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import java.lang.reflect.Constructor;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.StringTokenizer;

/**
 * <p>
 * 山东能开平台DES3加密解密工具类
 * </p>
 *
 * @author wangliang
 * @since 2018/1/17
 */
@Slf4j
public class DES3Util {

    private static final String algorithm1 = "DES";
    private static final String algorithm2 = "PBE";
    private static final String keyFilename = "DES.properties";

    /**
     * 加密
     *
     * @param bytes
     * @param algorithm
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] bytes, String algorithm) throws Exception {
        Class clazz = null;
        if (algorithm.equals("DES")) {
            clazz = Class.forName("javax.crypto.spec.DESKeySpec");
        } else if (algorithm.equals("PBE")) {
            clazz = Class.forName("javax.crypto.spec.PBEKeySpec");
        }
        Constructor constructor = clazz.getConstructor(byte[].class);

        KeySpec keySpec = (KeySpec) constructor.newInstance(PropertiesUtil.readFile(keyFilename));

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm1);

        SecretKey key = keyFactory.generateSecret(keySpec);

        Cipher cipher = Cipher.getInstance(algorithm1);

        SecureRandom sr = new SecureRandom();

        cipher.init(Cipher.ENCRYPT_MODE, key, sr);

        byte[] outBytes = cipher.doFinal(bytes);

        return outBytes;
    }

    /**
     * 解密
     *
     * @param bytes
     * @param algorithm
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] bytes, String algorithm) throws Exception {
        Class clazz = null;
        if (algorithm.equals("DES")) {
            clazz = Class.forName("javax.crypto.spec.DESKeySpec");
        } else if (algorithm.equals("PBE")) {
            clazz = Class.forName("javax.crypto.spec.PBEKeySpec");
        }
        Constructor constructor = clazz.getConstructor(byte[].class);

        KeySpec keySpec = (KeySpec) constructor.newInstance(PropertiesUtil.readFile(keyFilename));

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm1);

        SecretKey key = keyFactory.generateSecret(keySpec);

        Cipher cipher = Cipher.getInstance(algorithm1);

        SecureRandom sr = new SecureRandom();

        cipher.init(Cipher.DECRYPT_MODE, key, sr);

        byte[] outBytes = cipher.doFinal(bytes);

        return outBytes;
    }

    /**
     * des加密
     *
     * @param data
     * @return
     */
    public static String desEncrypt(String data) {
        String result = "";
        StringBuffer sb = new StringBuffer();
        try {
            //明文
            byte[] dataBytes = data.getBytes();
            //加密后的密文
            byte[] encryptBytes = encrypt(dataBytes, algorithm1);

            //密文二次处理
            sb.append(encryptBytes.length + "|");

            for (int i = 0; i < encryptBytes.length; ++i) {
                sb.append(encryptBytes[i] + "|");
            }

            result = sb.toString();
            result = result.substring(0, result.length() - 1);
        } catch (Exception e) {
            log.error("desEncrypt error : {}", e);
        }
        return result;
    }

    /**
     * pbe加密
     *
     * @param data
     * @return
     */
    public static String pbeEncrypt(String data) {
        String result = "";
        StringBuffer sb = new StringBuffer();
        try {
            //明文
            byte[] dataBytes = data.getBytes();
            //加密后的密文
            byte[] encryptBytes = encrypt(dataBytes, algorithm2);

            //密文二次处理
            sb.append(encryptBytes.length + "|");

            for (int i = 0; i < encryptBytes.length; ++i) {
                sb.append(encryptBytes[i] + "|");
            }

            result = sb.toString();
            result = result.substring(0, result.length() - 1);
        } catch (Exception e) {
            log.error("pbeEncrypt error : {}", e);
        }
        return result;
    }

    /**
     * des解密
     *
     * @param data
     * @return
     */
    public static String desDecrypt(String data) {
        String result = "";
        try {
            int byte_i = Integer.parseInt(data.substring(0, data.indexOf("|")));

            byte[] dataBytes = new byte[byte_i];

            data = data.substring(data.indexOf("|") + 1);

            StringTokenizer st = new StringTokenizer(data, "|");

            for (int i = 0; st.hasMoreTokens(); ++i) {
                byte_i = Integer.parseInt(st.nextToken());
                dataBytes[i] = (byte) byte_i;
            }

            byte[] decryptBytes = decrypt(dataBytes, algorithm1);

            result = new String(decryptBytes);
        } catch (Exception e) {
            log.error("desDecrypt error : {}", e);
        }
        return result;
    }


    /**
     * pbe解密
     *
     * @param data
     * @return
     */
    public static String pbeDecrypt(String data) {
        String result = "";
        try {
            int byte_i = Integer.parseInt(data.substring(0, data.indexOf("|")));

            byte[] dataBytes = new byte[byte_i];

            data = data.substring(data.indexOf("|") + 1);

            StringTokenizer st = new StringTokenizer(data, "|");

            for (int i = 0; st.hasMoreTokens(); ++i) {
                byte_i = Integer.parseInt(st.nextToken());
                dataBytes[i] = (byte) byte_i;
            }

            byte[] decryptBytes = decrypt(dataBytes, algorithm2);

            result = new String(decryptBytes);
        } catch (Exception e) {
            log.error("pbeDecrypt error : {}", e);
        }
        return result;
    }

}
