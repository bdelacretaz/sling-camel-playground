package org.apache.sling.whiteboard.slingcamel.camel;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.util.IOHelper;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;

/**
 * @author Ionut-Maxim Margelatu (imargela@adobe.com)
 */
public class FileStreamingRoute extends RouteBuilder {

  public static final String INPUT_ENDPOINT = "direct:streamFile";
  public static final String HEADER_STREAM = "stream";
  public static final String HEADER_STREAM_BUFFER_SIZE = "streamBufferSize";

  private String fileLoadDirectory;

  @Override
  public void configure() throws Exception {
    from(INPUT_ENDPOINT).process(this::processStreamFile);
  }

  private void processStreamFile(Exchange e) throws Exception {
    String filename = (String) e.getIn().getBody();
    InputStream inputStream = new FileInputStream(Paths.get(fileLoadDirectory, filename).toFile());
    OutputStream outputStream = (OutputStream) e.getIn().getHeader(HEADER_STREAM);
    int bufferSize = (int) e.getIn().getHeader(HEADER_STREAM_BUFFER_SIZE);

    IOHelper.copyAndCloseInput(inputStream, outputStream, bufferSize);
  }
}
