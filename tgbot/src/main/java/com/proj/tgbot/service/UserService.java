package com.proj.tgbot.service;


import com.google.gson.Gson;
import com.proj.tgbot.domain.UserCredentials;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserService {
    PostGetService postGetService = new PostGetService();

    public boolean registerUser(UserCredentials userCredentials) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(userCredentials);
        return postGetService.sendPost(json, "/register");
    }
//        final Collection<NameValuePair> params = new ArrayList<>();
//        params.add(new BasicNameValuePair("login", login));
//        params.add(new BasicNameValuePair("password", password));
//
//        final Content postResultForm = Request.Post(url + "/register")
//                .bodyForm(params, Charset.defaultCharset())
//                .execute().returnContent();
//        System.out.println(postResultForm.asString());


    public UserCredentials findUserByTgId(Long chatId) {
        String jsonString = postGetService.sendGet("/tgUser/" + chatId);
        if (jsonString.equals("")) {
            return null;
        }
        return new Gson().fromJson(jsonString, UserCredentials.class);
    }

    public UserCredentials findUserByLoginOrId( String id) {
        String jsonString = postGetService.sendGet("/user/" + id);
        if (jsonString.equals("")) {
            return null;
        }
        return new Gson().fromJson(jsonString, UserCredentials.class);
    }
}
