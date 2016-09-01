package org.apache.sling.whiteboard.slingcamel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * @author Ionut-Maxim Margelatu (imargela@adobe.com)
 */
public class StringToUppercaseProcessor implements Processor {

  public void process(Exchange exchange) throws Exception {
    final String payload = exchange.getIn().getBody(String.class);
    exchange.getOut().setBody(payload.toUpperCase());
  }

}
