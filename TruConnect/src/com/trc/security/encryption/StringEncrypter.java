package com.trc.security.encryption;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class StringEncrypter implements Encrypter {
  private static final String encoding = "UTF-8";
  Cipher ecipher;
  Cipher dcipher;

  public StringEncrypter(String password) {
    // 8-bytes Salt
    byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56, (byte) 0x34, (byte) 0xE3,
        (byte) 0x03 };
    // Iteration count
    int iterationCount = 19;

    try {
      KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterationCount);
      SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
      ecipher = Cipher.getInstance(key.getAlgorithm());
      dcipher = Cipher.getInstance(key.getAlgorithm());
      // Prepare the parameters to the cipthers
      AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
      ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
      dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
    } catch (InvalidAlgorithmParameterException e) {
      System.out.println("EXCEPTION: InvalidAlgorithmParameterException");
    } catch (InvalidKeySpecException e) {
      System.out.println("EXCEPTION: InvalidKeySpecException");
    } catch (NoSuchPaddingException e) {
      System.out.println("EXCEPTION: NoSuchPaddingException");
    } catch (NoSuchAlgorithmException e) {
      System.out.println("EXCEPTION: NoSuchAlgorithmException");
    } catch (InvalidKeyException e) {
      System.out.println("EXCEPTION: InvalidKeyException");
    }
  }

  /**
   * Takes a single String as an argument and returns an Encrypted version of
   * that String.
   * 
   * @param str
   * @return <code>String</code> Encrypted version of the provided String
   */
  public byte[] encrypt(String str) {
    try {
      // Encode the string into bytes using utf-8
      byte[] utf8 = str.getBytes("UTF8");
      // Encrypt
      byte[] enc = ecipher.doFinal(utf8);
      // Encode bytes to base64 to get a string
      // return new sun.misc.BASE64Encoder().encode(enc);
      return enc;
    } catch (BadPaddingException e) {
    } catch (IllegalBlockSizeException e) {
    } catch (UnsupportedEncodingException e) {
    }
    return null;
  }

  /**
   * Takes a encrypted String as an argument, decrypts and returns the decrypted
   * String.
   * 
   * @param str
   * @return <code>String</code> Decrypted version of the provided String
   */
  public String decrypt(byte[] dec) {
    try {
      // Decode base64 to get bytes
      // byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
      // byte[] dec = Base64Coder.decode(str);
      // Decrypt
      byte[] utf8 = dcipher.doFinal(dec);
      // Decode using utf-8
      return new String(utf8, "UTF8");
    } catch (BadPaddingException e) {
    } catch (IllegalBlockSizeException e) {
    } catch (UnsupportedEncodingException e) {
    }
    return null;
  }

  @Override
  public String encryptUrlSafe(String input) throws UnsupportedEncodingException {
    byte[] encodedBytes = encrypt(input);
    BASE64Encoder base64Encoder = new BASE64Encoder();
    String encodedString = base64Encoder.encodeBuffer(encodedBytes);
    String firstPass = URLEncoder.encode(encodedString, encoding);
    String urlEncodedString = firstPass;
    String hex = convertStringToHex(urlEncodedString);
    return hex;
  }

  @Override
  public String decryptUrlSafe(String urlEncodedString) throws IOException {
    String ascii = convertHexToString(urlEncodedString);
    String firstPass = URLDecoder.decode(ascii, encoding);
    String encodedString = firstPass;
    BASE64Decoder base64Decoder = new BASE64Decoder();
    byte[] encodedBytes = base64Decoder.decodeBuffer(encodedString);
    String recoveredString = decrypt(encodedBytes);
    return recoveredString;
  }

  public String encryptIntUrlSafe(int input) throws UnsupportedEncodingException {
    return encryptUrlSafe(Integer.toString(input));
  }

  public int decryptIntUrlSafe(String input) throws IOException, NumberFormatException {
    String decryptedString = decryptUrlSafe(input);
    return Integer.parseInt(decryptedString);
  }

  public String convertStringToHex(String str) {
    char[] chars = str.toCharArray();
    StringBuffer hex = new StringBuffer();
    for (int i = 0; i < chars.length; i++) {
      hex.append(Integer.toHexString((int) chars[i]));
    }
    return hex.toString();
  }

  public String convertHexToString(String hex) {
    StringBuilder sb = new StringBuilder();
    // StringBuilder temp = new StringBuilder();
    // 49204c6f7665204a617661 split into two characters 49, 20, 4c...
    for (int i = 0; i < hex.length() - 1; i += 2) {
      // grab the hex in pairs
      String output = hex.substring(i, (i + 2));
      // convert hex to decimal
      int decimal = Integer.parseInt(output, 16);
      // convert the decimal to character
      sb.append((char) decimal);
      // temp.append(decimal);
    }
    // System.out.println("Decimal : " + temp.toString());
    return sb.toString();
  }

  public static void main(String... args) throws IOException {
    StringEncrypter se = new StringEncrypter("asdfasfdfdfa");
    String ascii = "98110112901121077390745489375168374868374865";
    String firstPass = URLDecoder.decode(ascii, encoding);
    String encodedString = firstPass;
    BASE64Decoder base64Decoder = new BASE64Decoder();
    byte[] encodedBytes = base64Decoder.decodeBuffer(encodedString);
    String recoveredString = se.decrypt(encodedBytes);
    System.out.println(recoveredString);
  }
}