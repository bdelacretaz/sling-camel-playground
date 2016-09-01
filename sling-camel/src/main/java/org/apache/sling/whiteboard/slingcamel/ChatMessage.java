package org.apache.sling.whiteboard.slingcamel;

import org.apache.camel.Body;
import org.apache.camel.Header;

public interface ChatMessage {
  String add(@Body String channel, @Header("name") String name, @Header("message") String message);
}
