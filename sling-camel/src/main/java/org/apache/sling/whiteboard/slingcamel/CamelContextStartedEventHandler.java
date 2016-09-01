package org.apache.sling.whiteboard.slingcamel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.ProxyBuilder;
import org.apache.camel.management.event.CamelContextStartedEvent;
import org.apache.camel.support.EventNotifierSupport;

import java.util.EventObject;

/**
 * @author Ionut-Maxim Margelatu (imargela@adobe.com)
 */
public class CamelContextStartedEventHandler extends EventNotifierSupport {

  public void notify(EventObject event) throws Exception {
    if (event instanceof CamelContextStartedEvent) {
      CamelContextStartedEvent sent = (CamelContextStartedEvent) event;
      CamelContext camelContext = sent.getContext();
      FileProvider fileProvider = new ProxyBuilder(camelContext).endpoint("direct:loadFile").build(FileProvider.class);
    }
  }

  public boolean isEnabled(EventObject event) {
    return event instanceof CamelContextStartedEvent;
  }

  protected void doStart() throws Exception {
    // noop
  }

  protected void doStop() throws Exception {
    // noop
  }

}
