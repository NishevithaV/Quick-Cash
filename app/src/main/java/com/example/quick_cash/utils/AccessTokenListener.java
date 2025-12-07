package com.example.quick_cash.utils;

public interface AccessTokenListener {
    void onAccessTokenReceived(String token);
    void onAccessTokenError(Exception exception);
}