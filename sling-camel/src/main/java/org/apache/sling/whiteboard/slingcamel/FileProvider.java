package org.apache.sling.whiteboard.slingcamel;

import org.apache.camel.Body;
import org.apache.camel.Header;

import java.nio.file.NoSuchFileException;

/**
 * @author Ionut-Maxim Margelatu (imargela@adobe.com)
 */
public interface FileProvider {

  String get(@Body String path, @Header("options") String options) throws NoSuchFileException;

}
