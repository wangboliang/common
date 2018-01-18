package com.test;

import com.alibaba.druid.filter.config.ConfigTools;
import org.junit.Test;

/**
 * druid 连接池加密解密
 */
public class DecryptDruid {

    /**
     * 文字进行加密
     *
     * @throws Exception
     */
    @Test
    public void testEncrypt() throws Exception {
        String password = "root";
        String[] keyPair = ConfigTools.genKeyPair(512);
        //私钥
        String privateKey = keyPair[0];
        //公钥
        String publicKey = keyPair[1];
        //用私钥加密后的密文
        password = ConfigTools.encrypt(privateKey, password);
        System.out.println("privateKey is: " + privateKey);
        System.out.println("publicKey is: " + publicKey);
        System.out.println("password is: " + password);
    }

    /**
     * 对文字进行解密
     *
     * @throws Exception
     */
    @Test
    public void testDecrypt() throws Exception {
        /*String [] keyPair = ConfigTools.genKeyPair(512);
        String privateKey = keyPair[0];
        String publicKey = keyPair[1];*/
        String decryptword = ConfigTools.decrypt("MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAN9dGI7ljLxGCLgm0jzfgaYrzYaIAyhpz3v1KffP5Pvv0nKhJzjD8vbRghFWakv6xHZHPPwjgZtFVb10HiblEHECAwEAAQ==", "ckVxkqVs5Gr4lSLtJTsa301UJ9TwjHR/empatRoKLhyav2f6YSsti7OmVQfaO7xreYXoIbYPmHo6p11UbY/XJg==");
        System.out.println(decryptword);
    }

}