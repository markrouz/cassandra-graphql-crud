package org.mgerman.cgc.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.ByteBuffer;
import org.junit.jupiter.api.Test;

public class ImageUtilsTest {

  @Test
  public void parseImageTest() throws IOException {
    ByteBuffer byteBuffer = ImageUtils.convertImageToByteBuffer(
        "src/main/resources/avatars/john.jpg");
    assertTrue(byteBuffer.hasArray());
  }

  @Test
  public void parseNonExistingImageTest() {
    assertThrows(IOException.class, () -> ImageUtils.convertImageToByteBuffer("some/path"));
  }

}
