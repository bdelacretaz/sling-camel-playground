package org.apache.sling.whiteboard.slingcamel.camel;

import java.util.Arrays;
import java.util.List;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.scr.AbstractCamelRunner;
import org.apache.camel.spi.ComponentResolver;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.ReferencePolicyOption;
import org.apache.felix.scr.annotations.References;

/**
 * @author Ionut-Maxim Margelatu (imargela@adobe.com)
 */
@Component(label = PlaygroundCamelContext.COMPONENT_LABEL,
           description = PlaygroundCamelContext.COMPONENT_DESCRIPTION, immediate = true, metatype = true)
@Properties({
                @Property(name = "camelContextId", value = "sling-camel-playground"),
                @Property(name = "active", value = "true"),

                // Default error handler settings
                @Property(name = "maximumRedeliveries", value = "0"),
                @Property(name = "redeliveryDelay", value = "5000"),
                @Property(name = "backOffMultiplier", value = "2"),
                @Property(name = "maximumRedeliveryDelay", value = "60000"),

                // File load route settings
                @Property(name = "fileLoadDirectory", value = "/tmp/sling-camel"),
                
                // Chat message settings
                @Property(name = "chatFilesDirectory", value = "/tmp/sling-camel"),
            })
@References({
                @Reference(name = "camelComponent", referenceInterface = ComponentResolver.class,
                           cardinality = ReferenceCardinality.MANDATORY_MULTIPLE, policy = ReferencePolicy.DYNAMIC,
                           policyOption = ReferencePolicyOption.GREEDY, bind = "gotCamelComponent", unbind = "lostCamelComponent")
            })
public class PlaygroundCamelContext extends AbstractCamelRunner {

  public static final String COMPONENT_LABEL = "org.apache.sling.whiteboard.slingcamel.PlaygroundCamelRunner";
  public static final String COMPONENT_DESCRIPTION = "Sling Camel Playground";

  @Override
  protected List<RoutesBuilder> getRouteBuilders() {
      return Arrays.asList(new RouteBuilder [] {
          new DefaultRouteBuilder(),
          new FileLoadRouteBuilder(),
          new ChatMessageRouteBuilder(),
          new ServletOutputRouteBuilder()
      });
  }
}
