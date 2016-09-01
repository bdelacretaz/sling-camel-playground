package org.apache.sling.whiteboard.slingcamel.camel;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ChatMessageRouteBuilder extends RouteBuilder {

  private Logger log = LoggerFactory.getLogger(getClass());
  
  private String chatFilesDirectory;

  private class ChatMessageProcessor implements Processor {
      @Override
      public void process(Exchange exchange) throws Exception {
          Message m = exchange.getIn();
          final String chatMsg = String.format("%s %s says %s", new Date(), m.getHeader("name"), m.getHeader("message"));
          String filename = (String) exchange.getIn().getBody() + ".txt";
          final Path p = Paths.get(chatFilesDirectory, filename); 
          log.info("Writing to {}: {}", p, chatMsg);
          Files.write(
                  p, 
                  Arrays.asList(new String [] { chatMsg }), 
                  UTF_8, APPEND, CREATE);
          exchange.getOut().setBody(chatMsg + "\n");
          CamelRoute.copyHeaders(exchange, ServletOutputRouteBuilder.WRITER_HEADER);
      }
  }
  
  @Override
  public void configure() throws Exception {
      from("direct:addChatMessage")
      .process(new ChatMessageProcessor())
      .to("direct:servletOutputWriter");
  }
}
