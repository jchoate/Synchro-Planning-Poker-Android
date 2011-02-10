/*
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2011 the original author or authors.
 */
package com.agile_coder.poker.client.android;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;

public class MessageSender {

    private String host;
    private String user;

    public MessageSender(String host, String user) {
        this.host = host;
        this.user = user;
    }

    public void revealEstimates() {
        HttpClient client = new DefaultHttpClient();
        String message = host + "/poker/reveal";
        HttpPut put = new HttpPut(message);
        try {
            HttpResponse resp = client.execute(put);
            if (resp.getStatusLine().getStatusCode() != 204) {
                throw new IllegalStateException();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void resetEstimates() {
        HttpClient client = new DefaultHttpClient();
        String message = host + "/poker";
        HttpDelete delete = new HttpDelete(message);
        try {
            HttpResponse resp = client.execute(delete);
            if (resp.getStatusLine().getStatusCode() != 204) {
                throw new IllegalStateException();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void submitEstimate(String estimate) throws IOException, IllegalStateException {
        HttpClient client = new DefaultHttpClient();
        String message = host + "/poker/" + user + "/" + estimate;
        HttpPut put = new HttpPut(message);
        HttpResponse resp = client.execute(put);
        if (resp.getStatusLine().getStatusCode() != 204) {
            throw new IllegalStateException();
        }
    }

}
