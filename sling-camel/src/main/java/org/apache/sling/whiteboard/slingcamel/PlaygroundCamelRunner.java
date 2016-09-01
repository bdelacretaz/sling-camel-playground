package org.apache.sling.whiteboard.slingcamel;

import org.apache.camel.RoutesBuilder;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ionut-Maxim Margelatu (imargela@adobe.com)
 */
@Component(label = PlaygroundCamelRunner.COMPONENT_LABEL,
           description = PlaygroundCamelRunner.COMPONENT_DESCRIPTION, immediate = true, metatype = true)
@Properties({
                @Property(name = "camelContextId", value = "sling-camel-playground"),
                @Property(name = "active", value = "true"),

                // Default error handler settings
                @Property(name = "maximumRedeliveries", value = "0"),
                @Property(name = "redeliveryDelay", value = "5000"),
                @Property(name = "backOffMultiplier", value = "2"),
                @Property(name = "maximumRedeliveryDelay", value = "60000"),

                // File load route settings
                @Property(name = "fileLoadDirectory", value = "/Users/imargela/files")
            })
@References({
                @Reference(name = "camelComponent", referenceInterface = ComponentResolver.class,
                           cardinality = ReferenceCardinality.MANDATORY_MULTIPLE, policy = ReferencePolicy.DYNAMIC,
                           policyOption = ReferencePolicyOption.GREEDY, bind = "gotCamelComponent", unbind = "lostCamelComponent")
            })
public class PlaygroundCamelRunner extends AbstractCamelRunner {

  public static final String COMPONENT_LABEL = "org.apache.sling.whiteboard.slingcamel.PlaygroundCamelRunner";
  public static final String COMPONENT_DESCRIPTION = "Sling Camel Playground";

  @Override
  protected List<RoutesBuilder> getRouteBuilders() {
    List<RoutesBuilder> routesBuilders = new ArrayList<>();
    routesBuilders.add(new DefaultRouteBuilder());
    routesBuilders.add(new FileLoadRouteBuilder());
    return routesBuilders;
  }


}
