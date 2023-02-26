package com.proj.tgbot.service;

import com.google.gson.Gson;
import com.proj.tgbot.domain.UserCredentials;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Slf4j
public class PostGetService {
    String url = "http://localhost:8090/api/1";

    public boolean sendPost(String json, String uri) {
        final HttpPost httpPost = new HttpPost(url + uri);
        try {
            final StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
        } catch (UnsupportedEncodingException e) {
            log.error("unsupported encoding in PostGetService:" + e.getMessage());
            return false;
        }
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");


        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(httpPost)) {

            System.out.println(response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                return false;
            }
        } catch (ClientProtocolException e) {
            log.info("cannot register User from UserService ClientProtocolException:" + e.getMessage());
            return false;
        } catch (IOException e) {
            log.info("cannot register User from UserService IOException:" + e.getMessage());
            return false;
        }
        return true;
    }

    public String sendGet(String uri) {
        final HttpGet httpGet = new HttpGet(url + uri);

        httpGet.setHeader("Accept", "application/json");

        CloseableHttpClient client = HttpClients.createDefault();
        try (CloseableHttpResponse response = client.execute(httpGet)) {
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                return null;
            }
            // Get HttpResponse Status

            HttpEntity result = response.getEntity();

            if (result != null) {
                return EntityUtils.toString(result, "UTF-8");
            }

        } catch (ClientProtocolException e) {
            log.error("Error occurred with ClientProtocolException: " + e.getMessage());
        } catch (IOException e) {
            log.error("Error occurred with IOException: " + e.getMessage());
        }
        return null;

    }
}
