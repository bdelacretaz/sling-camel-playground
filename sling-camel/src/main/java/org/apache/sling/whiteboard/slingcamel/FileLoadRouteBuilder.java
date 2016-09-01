package org.apache.sling.whiteboard.slingcamel;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author Ionut-Maxim Margelatu (imargela@adobe.com)
 */
public class FileLoadRouteBuilder extends RouteBuilder {

  private String fileLoadDirectory;

  @Override
  public void configure() throws Exception {
    Processor loadFileProcessor = new FileLoadProcessor(fileLoadDirectory);
    from("direct:loadFile")
        .process(loadFileProcessor)
        .choice()
          .when(header("options").isEqualTo("uppercase"))
            .to("direct:loadFileOutput")
          .otherwise()
            .log("No uppercase option specified")
        .endChoice();
  }
}
