package org.apache.sling.whiteboard.slingcamel.servlets;

import org.apache.camel.CamelContext;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.ReferencePolicyOption;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.whiteboard.slingcamel.camel.CamelRoute;
import org.apache.sling.whiteboard.slingcamel.camel.FileStreamingRoute;

import java.io.IOException;

import javax.servlet.ServletException;

/**
 * @author Ionut-Maxim Margelatu (imargela@adobe.com)
 */
@SuppressWarnings("serial")
@SlingServlet(resourceTypes = "camel/stream", extensions = "provider", methods = "GET")
public class FileStreamProviderServlet extends SlingSafeMethodsServlet {

  @Reference(
      referenceInterface = CamelContext.class,
      target = "(camel.context.symbolicname=org.apache.sling.whiteboard.sling-camel)",
      cardinality = ReferenceCardinality.MANDATORY_UNARY,
      policy = ReferencePolicy.DYNAMIC,
      policyOption = ReferencePolicyOption.GREEDY)
  volatile CamelContext camelContext;

  @Override
  protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
      throws ServletException, IOException {
    final String filename = request.getRequestPathInfo().getSuffix();

    try {
      CamelRoute
          .inContext(camelContext)
          .fromEndpoint(FileStreamingRoute.INPUT_ENDPOINT)
          .withBody(filename)
          .withHeaders(FileStreamingRoute.HEADER_STREAM, response.getOutputStream())
          .withHeaders(FileStreamingRoute.HEADER_STREAM_BUFFER_SIZE, response.getBufferSize())
          .run();
    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}
