package org.apache.sling.whiteboard.slingcamel;

import java.nio.file.NoSuchFileException;

/**
 * @author Ionut-Maxim Margelatu (imargela@adobe.com)
 */
public interface FileProvider {

  String get(String path) throws NoSuchFileException;

}
