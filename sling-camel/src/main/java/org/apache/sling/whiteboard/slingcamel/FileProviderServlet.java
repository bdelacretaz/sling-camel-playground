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
package org.apache.sling.whiteboard.slingcamel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.ProxyBuilder;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.ReferencePolicyOption;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

import java.io.IOException;

import javax.servlet.ServletException;

@SuppressWarnings("serial")
@SlingServlet(resourceTypes="camel/file", extensions="provider")
public class FileProviderServlet extends SlingSafeMethodsServlet {

    @Reference(
        referenceInterface = CamelContext.class,
        target="(camel.context.symbolicname=org.apache.sling.whiteboard.sling-camel)",
        cardinality = ReferenceCardinality.MANDATORY_UNARY,
        policy = ReferencePolicy.DYNAMIC, 
        policyOption = ReferencePolicyOption.GREEDY)
    volatile CamelContext camelContext;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
             throws ServletException, IOException
    {
        final String suffix = request.getRequestPathInfo().getSuffix();
        final String [] parts = suffix.split(":");
        final String filename = parts[0];
        final String options = parts.length == 1 ? "" : parts[1];

        try {
            FileProvider fileProvider = new ProxyBuilder(camelContext).endpoint("direct:loadFile").build(FileProvider.class);
            String fileContent = fileProvider.get(filename, options);

            response.getOutputStream().println(fileContent);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

}
