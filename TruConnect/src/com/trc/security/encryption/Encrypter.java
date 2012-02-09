package com.trc.security.encryption;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface Encrypter {

  public String encryptUrlSafe(String input) throws UnsupportedEncodingException;

  public String decryptUrlSafe(String input) throws IOException;

  public String encryptIntUrlSafe(int input) throws UnsupportedEncodingException;

  public int decryptIntUrlSafe(String input) throws IOException, NumberFormatException;

}
