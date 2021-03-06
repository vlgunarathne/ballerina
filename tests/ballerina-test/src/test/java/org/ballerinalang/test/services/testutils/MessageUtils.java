/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinalang.test.services.testutils;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultLastHttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import org.ballerinalang.net.http.Constants;
import org.wso2.carbon.messaging.CarbonMessage;
import org.wso2.carbon.messaging.DefaultCarbonMessage;
import org.wso2.carbon.messaging.Header;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * RequestResponseUtil Class contains method for generating a message.
 *
 * @since 0.8.0
 */
public class MessageUtils {

    public static CarbonMessage generateRawMessage() {
        return new DefaultCarbonMessage();
    }

    public static HTTPTestRequest generateHTTPMessage(String path, String method) {
        return generateHTTPMessage(path, method, null, null);
    }

    public static HTTPTestRequest generateHTTPMessage(String path, String method, String payload) {
        return generateHTTPMessage(path, method, null, payload);
    }

    public static HTTPTestRequest generateHTTPMessage(String path, String method, List<Header> headers,
            String payload) {
        HTTPTestRequest carbonMessage = getHttpTestRequest(path, method);
        HttpHeaders httpHeaders = carbonMessage.getHeaders();
        if (headers != null) {
            for (Header header : headers) {
                httpHeaders.set(header.getName(), header.getValue());
            }
        }
        if (payload != null) {
            carbonMessage.addHttpContent(new DefaultLastHttpContent(Unpooled.wrappedBuffer(payload.getBytes())));
        } else {
            carbonMessage.setEndOfMsgAdded(true);
        }
        return carbonMessage;
    }

    public static HTTPTestRequest generateHTTPMessageForMultiparts(String path, String method) {
        return getHttpTestRequest(path, method);
    }

    private static HTTPTestRequest getHttpTestRequest(String path, String method) {
        HTTPTestRequest carbonMessage = new HTTPTestRequest();
        carbonMessage.setProperty(Constants.PROTOCOL,
                Constants.PROTOCOL_HTTP);
        carbonMessage.setProperty(Constants.LISTENER_INTERFACE_ID,
                Constants.DEFAULT_INTERFACE);
        // Set url
        carbonMessage.setProperty(Constants.TO, path);
        carbonMessage.setProperty(Constants.REQUEST_URL, path);
        carbonMessage.setProperty(Constants.HTTP_METHOD, method.trim().toUpperCase(Locale.getDefault()));
        carbonMessage.setProperty(Constants.LOCAL_ADDRESS,
                new InetSocketAddress(Constants.HTTP_DEFAULT_HOST, 9090));
        carbonMessage.setProperty(Constants.LISTENER_PORT, 9090);
        carbonMessage.setProperty(Constants.RESOURCE_ARGS, new HashMap<String, String>());
        return carbonMessage;
    }
}
