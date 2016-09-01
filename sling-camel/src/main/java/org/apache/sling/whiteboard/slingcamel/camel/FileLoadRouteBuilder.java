package org.apache.sling.whiteboard.slingcamel.camel;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author Ionut-Maxim Margelatu (imargela@adobe.com)
 */
class FileLoadRouteBuilder extends RouteBuilder {

  private String fileLoadDirectory;

  @Override
  public void configure() throws Exception {
    Processor loadFileProcessor = new FileLoadProcessor(fileLoadDirectory);
    Processor stringToUppercaseProcessor = new StringToUppercaseProcessor();

    from("direct:loadFile")
        .process(loadFileProcessor)
        .choice()
          .when(header("options").isEqualTo("uppercase"))
            .process(stringToUppercaseProcessor)
          .otherwise()
            .log("No uppercase option specified")
        .endChoice();
  }
}
