package com.example.fileencryption;

import org.apache.commons.codec.binary.Hex;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {
    public static String GenerateMD5(String password) throws NoSuchAlgorithmException {
        if (password.equals("")) {
            return password;
        }
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());

        return (new BigInteger(1, md.digest()).toString(16));

    }

    public static String GenerateSHA256(String password) throws NoSuchAlgorithmException {
        if (password.equals("")) {
            return password;
        }
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return Hex.encodeHexString(bytes);
    }
}
