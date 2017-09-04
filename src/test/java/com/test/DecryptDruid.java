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
        String decryptword = ConfigTools.decrypt("MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJHOhpqMfk2OehmTseDuiuG4bre07m74FPvK9tdTVrg5+SbpdqaOfRAwadI6r6pwvRKhwLw0rpr2JCpMFaoV73ECAwEAAQ==", "UahktVoqNaAJSgzNG6d1eCeRKtBTRzT2nx91BoIrvaLCx/fgD+KUNViUWEhxUrG1tXh0q3pO2B+/pa+lcOwzLg==");
        System.out.println(decryptword);
    }

}