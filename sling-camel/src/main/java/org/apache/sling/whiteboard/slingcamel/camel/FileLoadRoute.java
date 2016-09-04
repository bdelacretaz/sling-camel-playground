package org.apache.sling.whiteboard.slingcamel.camel;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Ionut-Maxim Margelatu (imargela@adobe.com)
 */
public class FileLoadRoute extends RouteBuilder {

  private String fileLoadDirectory;

  public static final String INPUT_ENDPOINT = "direct:loadFile";
  public static final String HEADER_STREAM = "stream";
  public static final String HEADER_OPTIONS = "options";
  public static final String[] HEADERS = {HEADER_STREAM, HEADER_OPTIONS};

  private void processLoadFile(Exchange e) throws Exception {
    String filename = (String) e.getIn().getBody();
    e.getOut().setBody(new String(Files.readAllBytes(Paths.get(fileLoadDirectory, filename))));
    CamelRoute.copyHeaders(e, HEADERS);
  }

  private void processUppercase(Exchange e) {
    e.getOut().setBody(e.getIn().getBody().toString().toUpperCase());
    CamelRoute.copyHeaders(e, HEADERS);
  }

  @Override
  public void configure() throws Exception {
    from(INPUT_ENDPOINT)
        .process(this::processLoadFile)
        .choice()
          .when(header(HEADER_OPTIONS).isEqualTo("uppercase"))
            .process(this::processUppercase)
          .otherwise()
            .log("No uppercase option specified")
        .end()
        .to("stream:header");
  }
}
