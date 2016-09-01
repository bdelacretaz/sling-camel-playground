package org.apache.sling.whiteboard.slingcamel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class UppercaseRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:loadFileOutput").process(new Processor() {
            public void process(Exchange exchange) throws Exception {
                final String payload = exchange.getIn().getBody(String.class);
                exchange.getOut().setBody(payload.toUpperCase());
            }
        });
    }
}
