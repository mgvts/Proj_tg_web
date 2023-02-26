package com.proj.form;

import com.proj.domain.User;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class MarkCredentials {
    @NotEmpty
    @Pattern(regexp = "[0-9]{1,2}")
    private String value;

    @Size(max = 1000)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
