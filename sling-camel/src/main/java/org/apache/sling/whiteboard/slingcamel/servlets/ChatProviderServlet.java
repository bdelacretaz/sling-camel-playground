/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.sling.whiteboard.slingcamel.servlets;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.camel.CamelContext;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.ReferencePolicyOption;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.whiteboard.slingcamel.camel.CamelRoute;

@SuppressWarnings("serial")
@SlingServlet(resourceTypes="camel/chat", extensions="provider", methods="POST")
public class ChatProviderServlet extends SlingAllMethodsServlet {

    @Reference(
        referenceInterface = CamelContext.class,
        target="(camel.context.symbolicname=org.apache.sling.whiteboard.sling-camel)",
        cardinality = ReferenceCardinality.MANDATORY_UNARY,
        policy = ReferencePolicy.DYNAMIC, 
        policyOption = ReferencePolicyOption.GREEDY)
    volatile CamelContext camelContext;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
             throws ServletException, IOException
    {
        final String channel = request.getRequestPathInfo().getSuffix();

        try {
            CamelRoute
            .inContext(camelContext)
            .fromEndpoint("direct:addChatMessage")
            .withBody(channel)
            .withHeaders(
                "name", request.getParameter("name"),
                "message", request.getParameter("message"),
                "servletOutputWriter", response.getWriter()
            )
            .run();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

}
