package org.apache.sling.whiteboard.slingcamel.camel;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author Ionut-Maxim Margelatu (imargela@adobe.com)
 */
class DefaultRouteBuilder extends RouteBuilder {

  // Default error handler
  private Integer maximumRedeliveries;
  private Long redeliveryDelay;
  private Double backOffMultiplier;
  private Long maximumRedeliveryDelay;

  @Override
  public void configure() throws Exception {
    errorHandler(defaultErrorHandler()
                     .retryAttemptedLogLevel(LoggingLevel.WARN)
                     .maximumRedeliveries(maximumRedeliveries)
                     .redeliveryDelay(redeliveryDelay)
                     .backOffMultiplier(backOffMultiplier)
                     .maximumRedeliveryDelay(maximumRedeliveryDelay));
  }

}
