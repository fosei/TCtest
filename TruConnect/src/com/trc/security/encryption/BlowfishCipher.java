package com.trc.security.encryption;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import org.springframework.web.util.UriUtils;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class BlowfishCipher implements Encrypter {
  private static final String encoding = "UTF-8";
  private static KeyGenerator keyGenerator;
  private static Cipher cipher;
  private Key key;

  public BlowfishCipher() throws Exception {
    keyGenerator = getKeyGenerator();
    cipher = getCipher();
    key = getKey();
  }

  private Key getKey() {
    return keyGenerator.generateKey();
  }

  private KeyGenerator getKeyGenerator() throws NoSuchAlgorithmException {
    KeyGenerator keyGenerator = KeyGenerator.getInstance("Blowfish");
    keyGenerator.init(128);
    return keyGenerator;
  }

  private Cipher getCipher() throws NoSuchAlgorithmException, NoSuchPaddingException {
    return Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
  }

  /**
   * Encodes the string using this cipher.
   * 
   * @param message
   * @return
   */
  public byte[] encode(String message) {
    try {
      cipher.init(Cipher.ENCRYPT_MODE, key);
      return cipher.doFinal(message.getBytes(encoding));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Returns the decoded string from this cipher.
   * 
   * @param message
   * @return
   */
  public String decode(byte[] message) {
    try {
      cipher.init(Cipher.DECRYPT_MODE, key);
      message = cipher.doFinal(message);
      return new String(message, encoding);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Prints the bytes of the input.
   * 
   * @param message
   */
  public static void printBytes(byte[] message) {
    for (int i = 0; i < message.length; i++) {
      System.out.print(message[i] + " ");
    }
    System.out.println();
  }

  /**
   * From a base 64 representation, returns the corresponding byte[]
   * 
   * @param data
   * @return
   * @throws IOException
   */
  public static byte[] base64ToByte(String data) throws IOException {
    byte[] result = null;
    try {
      result = Base64.decode(data);
    } catch (Base64DecodingException e) {
      e.printStackTrace();
    }
    return result;
    // BASE64Decoder decoder = new BASE64Decoder();
    // return decoder.decodeBuffer(data);
  }

  /**
   * From a byte[] returns a base 64 representation
   * 
   * @param data
   * @return
   */
  public static String byteToBase64(byte[] data) {
    return Base64.encode(data);
    // BASE64Encoder encoder = new BASE64Encoder();
    // return encoder.encode(data);
  }

  @Override
  public String encryptUrlSafe(String message) {
    byte[] encodedBytes = encode(message);
    String base64Encoded = byteToBase64(encodedBytes);
    String urlSafe = null;
    try {
      urlSafe = UriUtils.encodePathSegment(base64Encoded, encoding);
      urlSafe = UriUtils.encodePathSegment(urlSafe, encoding);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return urlSafe;
  }

  @Override
  public String decryptUrlSafe(String message) {
    String decodedMessage = null;
    try {
      message = UriUtils.decode(message, encoding);
      message = UriUtils.decode(message, encoding);
      byte[] bytesToDecode = Base64.decode(message);
      decodedMessage = decode(bytesToDecode);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (Base64DecodingException e) {
      e.printStackTrace();
    }
    return decodedMessage;
  }

  @Override
  public String encryptIntUrlSafe(int message) throws UnsupportedEncodingException {
    return encryptUrlSafe(Integer.toString(message));
  }

  @Override
  public int decryptIntUrlSafe(String input) throws IOException, NumberFormatException {
    return Integer.parseInt(decryptUrlSafe(input));
  }
}
