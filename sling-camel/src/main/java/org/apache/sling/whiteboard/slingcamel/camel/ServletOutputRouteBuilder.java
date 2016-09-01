package org.apache.sling.whiteboard.slingcamel.camel;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.Writer;


import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.io.IOUtils;

class ServletOutputRouteBuilder extends RouteBuilder {

  public static final String WRITER_HEADER = "servletOutputWriter";
  @Override
  public void configure() throws Exception {
    from("direct:servletOutputWriter")
    .process(e -> {
        final Writer w = (Writer)(e.getIn().getHeader(WRITER_HEADER));
        if(w == null) {
            throw new IllegalArgumentException("Missing header " + WRITER_HEADER);
        }
        // TODO the whole thing should be streaming 
        final BufferedReader r = new BufferedReader(new StringReader(e.getIn().getBody().toString()));
        IOUtils.copy(r, w);
     });
  }
}