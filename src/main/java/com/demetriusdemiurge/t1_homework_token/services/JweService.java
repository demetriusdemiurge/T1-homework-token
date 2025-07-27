package com.demetriusdemiurge.t1_homework_token.services;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jwt.SignedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

@Service
public class JweService {

    private KeyPair rsaKeyPair;

    @PostConstruct
    public void init() throws Exception {
        // Сгенерировать RSA-ключи при запуске
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        rsaKeyPair = keyGen.generateKeyPair();
    }

    public String encrypt(String jwt) throws Exception {
        JWEHeader header = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM)
                .contentType("JWT") // JWT внутри JWE
                .build();

        Payload payload = new Payload(jwt);
        JWEObject jweObject = new JWEObject(header, payload);

        jweObject.encrypt(new RSAEncrypter((RSAPublicKey) rsaKeyPair.getPublic()));
        return jweObject.serialize();
    }

    public String decrypt(String jweToken) throws Exception {
        JWEObject jweObject = JWEObject.parse(jweToken);
        jweObject.decrypt(new RSADecrypter((RSAPrivateKey) rsaKeyPair.getPrivate()));

        return jweObject.getPayload().toString(); // это строка обычного JWT
    }

    public boolean isEncryptedJwtValid(String jweToken, String username) {
        try {
            String decryptedJwt = decrypt(jweToken);
            SignedJWT signedJWT = SignedJWT.parse(decryptedJwt);
            return signedJWT.getJWTClaimsSet().getSubject().equals(username);
        } catch (Exception e) {
            return false;
        }
    }
}

