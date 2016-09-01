package org.apache.sling.whiteboard.slingcamel.camel;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author Ionut-Maxim Margelatu (imargela@adobe.com)
 */
class FileLoadRouteBuilder extends RouteBuilder {

  private String fileLoadDirectory;
  
  private final static String [] HEADERS_TO_COPY = { "options", ServletOutputRouteBuilder.WRITER_HEADER };

  private void processLoadFile(Exchange e) throws Exception {
      String filename = (String) e.getIn().getBody();
      e.getOut().setBody(new String(Files.readAllBytes(Paths.get(fileLoadDirectory, filename))));
      CamelRoute.copyHeaders(e, HEADERS_TO_COPY);
  }
  
  private void processUppercase(Exchange e) {
      e.getOut().setBody(e.getIn().getBody().toString().toUpperCase());
      CamelRoute.copyHeaders(e, HEADERS_TO_COPY);
  }
  
  @Override
  public void configure() throws Exception {
    from("direct:loadFile")
        .process(e -> { processLoadFile(e); })
        .choice()
          .when(header("options").isEqualTo("uppercase"))
            .process(e -> { processUppercase(e); })
          .otherwise()
            .log("No uppercase option specified")
        .end()
        .to("direct:servletOutputWriter");
  }
}