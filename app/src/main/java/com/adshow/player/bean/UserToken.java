package com.adshow.player.bean;

import java.util.Date;

public class UserToken {

    private String accessToken;

    private Date expiresIn;

    private String refreshToken;

    private Date refreshExpiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Date expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getRefreshExpiresIn() {
        return refreshExpiresIn;
    }

    public void setRefreshExpiresIn(Date refreshExpiresIn) {
        this.refreshExpiresIn = refreshExpiresIn;
    }
}
