package com.proj.tgbot.domain;


import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class UserCredentials {
    public UserCredentials(String login, String password, String tgId) {
        this.login = login;
        this.password = password;
        this.tgId = tgId;
    }

    @Setter
    @Getter
    private long id;

    @Setter
    @Getter
    private String login;

    @Getter
    private boolean admin;

    @Setter
    @Getter
    private String password;

    @Setter
    @Getter
    private String tgId;

    @Setter
    @Getter
    private String creationTime;

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", admin='" + admin + '\'' +
                ", tgId='" + tgId + '\'' +
                '}';
    }

    public JsonObject toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("id", this.id);
        res.addProperty("login", this.login);
        res.addProperty("admin", this.admin);
        res.addProperty("tgId", this.tgId);
        res.addProperty("creationTime", this.creationTime);
        return res;
    }
}
