package org.apache.sling.whiteboard.slingcamel.camel;

import org.apache.camel.builder.RouteBuilder;

public class ChatMessageRoute extends RouteBuilder {

  public static final String INPUT_ENDPOINT = "direct:addChatMessage";
  public static final String HEADER_CHANNEL = "channel";
  public static final String HEADER_NAME = "name";
  public static final String HEADER_MESSAGE = "message";
  public static final String HEADER_STREAM = "stream";

  private String chatFilesDirectory;

  @Override
  public void configure() throws Exception {
    from(INPUT_ENDPOINT)
        .setHeader("CamelFileName", simple("${in.header.channel}.txt"))
        .setBody(simple("${date:now:yyyy-MM-dd'T'HH:mm:ssX} ${in.header.name} says ${in.header.message}"))
        .to("file:" + chatFilesDirectory + "?autoCreate=true&charset=utf-8&fileExist=Append")
        .to("stream:header");
  }
}
