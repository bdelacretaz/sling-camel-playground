package org.apache.sling.whiteboard.slingcamel.camel;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author Ionut-Maxim Margelatu (imargela@adobe.com)
 */
class FileLoadRouteBuilder extends RouteBuilder {

  private String fileLoadDirectory;

  private class FileLoadProcessor implements Processor {
      @Override
      public void process(Exchange exchange) throws Exception {
        String filename = (String) exchange.getIn().getBody();
        exchange.getOut().setBody(new String(Files.readAllBytes(Paths.get(fileLoadDirectory, filename))));
        exchange.getOut().setHeader("options", exchange.getIn().getHeader("options"));
      }
  }
  
  @Override
  public void configure() throws Exception {
    from("direct:loadFile")
        .process(new FileLoadProcessor())
        .choice()
          .when(header("options").isEqualTo("uppercase"))
            .process(e -> { e.getOut().setBody(e.getIn().getBody().toString().toUpperCase()); })
          .otherwise()
            .log("No uppercase option specified")
        .endChoice();
  }
}