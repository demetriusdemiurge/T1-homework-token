package com.demetriusdemiurge.t1_homework_token.services;

import lombok.Getter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.*;
import java.util.Base64;

@Component
@Getter
public class RsaKeyLoader {

    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    public RsaKeyLoader() {
        try {
            this.publicKey = loadPublicKey("keys/public_key.pem");
            this.privateKey = loadPrivateKey("keys/private_key.pem");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load RSA keys", e);
        }
    }

    private PublicKey loadPublicKey(String path) throws Exception {
        String pem = readPemFromFile(path, "-----BEGIN PUBLIC KEY-----", "-----END PUBLIC KEY-----");
        byte[] bytes = Base64.getDecoder().decode(pem);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    private PrivateKey loadPrivateKey(String path) throws Exception {
        String pem = readPemFromFile(path, "-----BEGIN PRIVATE KEY-----", "-----END PRIVATE KEY-----");
        byte[] bytes = Base64.getDecoder().decode(pem);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    private String readPemFromFile(String path, String begin, String end) throws Exception {
        InputStream is = new ClassPathResource(path).getInputStream();
        String content = new String(is.readAllBytes());
        return content
                .replace(begin, "")
                .replace(end, "")
                .replaceAll("\\s", "");
    }
}
