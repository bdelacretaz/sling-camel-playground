package org.apache.sling.whiteboard.slingcamel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Ionut-Maxim Margelatu (imargela@adobe.com)
 */
public class FileLoadProcessor implements Processor {

  private final String fileLoadDirectory;

  public FileLoadProcessor(String fileLoadDirectory) {
    this.fileLoadDirectory = fileLoadDirectory;
  }

  @Override
  public void process(Exchange exchange) throws Exception {
    String filename = (String) exchange.getIn().getBody();
    exchange.getOut().setBody(new String(Files.readAllBytes(Paths.get(fileLoadDirectory, filename))));
  }
}
