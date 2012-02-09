package com.trc.security.encryption;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RsaEncoder {
  protected static final String Algorithm = "RSA";

  public RsaEncoder() {

  }

  public static KeyPair generateKey() throws NoSuchAlgorithmException {
    KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance(Algorithm);
    keyGenerator.initialize(512);
    KeyPair key = keyGenerator.generateKeyPair();
    return key;
  }

  public static String encrypt(String text, PublicKey key) throws Exception {
    String encryptedText = null;
    try {
      byte[] cipherText = encrypt(text.getBytes("UTF8"), key);
      encryptedText = encodeBASE64(cipherText);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return encryptedText;
  }

  public static String decrypt(String text, PrivateKey key) throws Exception {
    String result = null;
    try {
      byte[] decryptedText = decrypt(decodeBASE64(text), key);
      result = new String(decryptedText, "UTF8");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  public static byte[] encrypt(byte[] text, PublicKey key) throws Exception {
    byte[] cipherText = null;
    try {
      Cipher cipher = Cipher.getInstance(Algorithm);
      cipher.init(Cipher.ENCRYPT_MODE, key);
      cipherText = cipher.doFinal(text);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return cipherText;
  }

  public static byte[] decrypt(byte[] text, PrivateKey key) throws Exception {
    byte[] decryptedText = null;
    try {
      Cipher cipher = Cipher.getInstance(Algorithm);
      cipher.init(Cipher.DECRYPT_MODE, key);
      decryptedText = cipher.doFinal(text);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return decryptedText;
  }

  private static String encodeBASE64(byte[] bytes) {
    BASE64Encoder b64 = new BASE64Encoder();
    return b64.encode(bytes);
  }

  private static byte[] decodeBASE64(String text) throws IOException {
    BASE64Decoder b64 = new BASE64Decoder();
    return b64.decodeBuffer(text);
  }

  // public static void main(String[] args) {
  // String message = "secretPassword!";
  // String encryptedMessage = "";
  // String decryptedMessage = "";
  //
  // KeyPair kp = null;
  // try {
  // kp = generateKey();
  // System.out.println(kp.getPublic().toString());
  // System.out.println(kp.getPrivate().toString());
  // } catch (NoSuchAlgorithmException e) {
  // e.printStackTrace();
  // }
  //
  // try {
  // encryptedMessage = encrypt(message, kp.getPublic());
  // decryptedMessage = decrypt(encryptedMessage, kp.getPrivate());
  // } catch (Exception e) {
  // e.printStackTrace();
  // }
  //
  // System.out.println("\nOriginal Message:");
  // System.out.println("------------------");
  // System.out.println(message);
  // System.out.println("\nEncrypted Message:");
  // System.out.println("------------------");
  // System.out.println(encryptedMessage);
  // System.out.println("\nDecrypted Message:");
  // System.out.println("------------------");
  // System.out.println(decryptedMessage);
  // }
}
