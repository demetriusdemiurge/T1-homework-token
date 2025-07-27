package com.demetriusdemiurge.t1_homework_token.util;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

public class RsaKeyGenerator {

    public static void main(String[] args) throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();

        String privateKeyPem = "-----BEGIN PRIVATE KEY-----\n" +
                Base64.getMimeEncoder(64, "\n".getBytes()).encodeToString(keyPair.getPrivate().getEncoded()) +
                "\n-----END PRIVATE KEY-----";

        String publicKeyPem = "-----BEGIN PUBLIC KEY-----\n" +
                Base64.getMimeEncoder(64, "\n".getBytes()).encodeToString(keyPair.getPublic().getEncoded()) +
                "\n-----END PUBLIC KEY-----";

        try (Writer privateOut = new OutputStreamWriter(new FileOutputStream("src/main/resources/keys/private_key.pem"), StandardCharsets.UTF_8);
             Writer publicOut = new OutputStreamWriter(new FileOutputStream("src/main/resources/keys/public_key.pem"), StandardCharsets.UTF_8)) {
            privateOut.write(privateKeyPem);
            publicOut.write(publicKeyPem);
        }

        System.out.println("RSA ключи сохранены в resources/keys/");
    }
}
