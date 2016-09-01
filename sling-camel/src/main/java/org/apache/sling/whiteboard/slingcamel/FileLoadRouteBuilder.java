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

    onException(Exception.class)
      .maximumRedeliveries(3)
      .redeliveryDelay(1000);
    
    from("direct:loadFile")
      .process(loadFileProcessor).to("direct:loadFileOutput");
  }
}
