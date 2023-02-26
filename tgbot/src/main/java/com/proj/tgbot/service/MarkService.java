package com.proj.tgbot.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.proj.tgbot.domain.UserCredentials;

public class MarkService {
    PostGetService postGetService = new PostGetService();
    UserService userService = new UserService();

    public boolean addMark(int value, Long chatId, String comment) {
        Gson gson = new Gson();
        String json = gson.toJson(value);
        gson.toJson(comment);
        gson.toJson(userService.findUserByTgId(chatId));
        return postGetService.sendPost(json, "/addMark");
    }

    public boolean addMark(int value, Long chatId) {
        UserCredentials u = userService.findUserByTgId(chatId);

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("user", u.toJson());
        jsonObject.addProperty("comment", "");
        jsonObject.addProperty("value", value+"");

        return postGetService.sendPost(jsonObject.toString(), "/addMark");
    }


}
