package com.ksaphier.trainerapp;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Base64;

public class GenerateJwtSecret {
    public static void main(String[] args) {
        // Generate a secure key for HS512
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        // Base64 encode the key
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
        // Print the base64 encoded key
        System.out.println("Base64 encoded key: " + base64Key);
    }
}