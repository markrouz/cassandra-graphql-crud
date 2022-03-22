package org.mgerman.cgc.utils;

import com.datastax.oss.protocol.internal.util.Bytes;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;

public class JpgUtils {

  private JpgUtils() {
    throw new IllegalStateException("Utility class");
  }

  public static ByteBuffer convertImageToByteBuffer(String pathToImage) throws IOException {
    BufferedImage bImage = ImageIO.read(new File(pathToImage));
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ImageIO.write(bImage, "jpg", bos);
    return Bytes.fromHexString(Bytes.toHexString(bos.toByteArray()));
  }

}
