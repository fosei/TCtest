package com.trc.security.encryption;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class HashSalt {
  private static final int iterations = 50;

  // public static void main(String[] args) {
  //
  // String actualPassword = "myPassword";
  // String enteredPassword = "myPassword";
  //
  // byte[] salt = getSalt();
  // byte[] digest = encode(actualPassword, salt);
  //
  // String saltString = byteToBase64(salt);
  //
  // byte[] proposedDigest = encode(enteredPassword, saltString);
  // boolean match = compare(digest, proposedDigest);
  //
  // System.out.println("key");
  // System.out.println(byteToBase64(encode("key", salt)));
  // System.out.println(byteToBase64(encode("key", salt)));
  //
  // }

  public static boolean compare(byte[] x, byte[] y) {
    return Arrays.equals(x, y);
  }

  public static byte[] getSalt() {
    byte[] salt = new byte[8];
    SecureRandom random;
    try {
      random = SecureRandom.getInstance("SHA1PRNG");
      random.nextBytes(salt);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return salt;
  }

  public static byte[] encode(String message, byte[] salt) {
    try {
      return getHash(message, salt);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static byte[] encode(String message, String salt) {
    try {
      return encode(message, base64ToByte(salt));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static byte[] getHash(String message, byte[] salt) throws NoSuchAlgorithmException,
      UnsupportedEncodingException {
    return getHash(iterations, message, salt);
  }

  public static byte[] getHash(int iterations, String message, byte[] salt) throws NoSuchAlgorithmException,
      UnsupportedEncodingException {
    MessageDigest digest = MessageDigest.getInstance("SHA-1");
    digest.reset();
    digest.update(salt);
    byte[] input = digest.digest(message.getBytes("UTF-8"));
    for (int i = 0; i < iterations; i++) {
      digest.reset();
      input = digest.digest(input);
    }
    return input;
  }

  /**
   * From a base 64 representation, returns the corresponding byte[]
   * 
   * @param data
   * @return
   * @throws IOException
   */
  public static byte[] base64ToByte(String data) throws IOException {
    BASE64Decoder decoder = new BASE64Decoder();
    return decoder.decodeBuffer(data);
  }

  /**
   * From a byte[] returns a base 64 representation
   * 
   * @param data
   * @return
   */
  public static String byteToBase64(byte[] data) {
    BASE64Encoder endecoder = new BASE64Encoder();
    return endecoder.encode(data);
  }
}
