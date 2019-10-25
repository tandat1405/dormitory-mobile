package com.datnt.dormitorymanagement.SendModel;

public class SocialUser {
    private String email, idToken;

    public SocialUser(String email, String idToken) {
        this.email = email;
        this.idToken = idToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
