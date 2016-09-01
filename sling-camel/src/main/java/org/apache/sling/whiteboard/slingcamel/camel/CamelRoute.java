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
package org.apache.sling.whiteboard.slingcamel.camel;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;

/** Fluent helper to execute Camel routes */
public class CamelRoute {
    private final CamelContext ctx;
    private Endpoint endpoint;
    private final Map<String, Object> headers = new HashMap<String, Object>();
    private Object body;
    
    private CamelRoute(CamelContext ctx) {
        this.ctx = ctx;
    }
    
    public static CamelRoute inContext(CamelContext ctx) {
        return new CamelRoute(ctx);
    }
    
    public CamelRoute fromEndpoint(String uri) {
        endpoint = ctx.getEndpoint(uri);
        return this;
    }
    
    public CamelRoute withHeaders(Object ...nameValuePairs) {
        for(int i=0; i < nameValuePairs.length; i+=2) {
            headers.put(nameValuePairs[i].toString(), nameValuePairs[i+1]);
        }
        return this;
    }
    
    public CamelRoute withBody(Object body) {
        this.body = body;
        return this;
    }
    
    public Object run() {
        return ctx.createProducerTemplate().requestBodyAndHeaders(endpoint, body, headers);
    }
    
    static void copyHeaders(Exchange e, String ... names) {
        for(String h : names) {
            e.getOut().setHeader(h, e.getIn().getHeader(h));
        }
    }
}
