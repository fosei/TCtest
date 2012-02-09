package com.trc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public final class FileReader {
  /**
   * Reads a file to a string. Need to figure out correct relative path to get
   * to txt files.
   * 
   * @param path
   * @return
   * @throws IOException
   */
  public static String readTemplate(String path) throws IOException {
    FileInputStream stream = new FileInputStream(new File(path));
    FileChannel fc = stream.getChannel();
    try {
      MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
      Charset ISO_8859_1 = Charset.availableCharsets().get("ISO-8859-1");
      return ISO_8859_1.decode(bb).toString();
    } finally {
      stream.close();
      fc.close();
    }
  }
}
