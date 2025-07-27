package com.demetriusdemiurge.t1_homework_token.services;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@RequiredArgsConstructor
@Service
public class JweService {

    private final RsaKeyLoader rsaKeyLoader;

    public String encrypt(String jwt) throws Exception {
        JWEHeader header = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM)
                .contentType("JWT")
                .build();

        Payload payload = new Payload(jwt);
        JWEObject jweObject = new JWEObject(header, payload);

        jweObject.encrypt(new RSAEncrypter((RSAPublicKey) rsaKeyLoader.getPublicKey()));
        return jweObject.serialize();
    }

    public String decrypt(String jweToken) throws Exception {
        JWEObject jweObject = JWEObject.parse(jweToken);
        jweObject.decrypt(new RSADecrypter((RSAPrivateKey) rsaKeyLoader.getPrivateKey()));

        return jweObject.getPayload().toString();
    }
}


