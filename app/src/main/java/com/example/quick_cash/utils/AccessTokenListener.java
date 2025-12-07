package com.example.quick_cash.utils;

/**
 * The interface Access token listener.
 */
public interface AccessTokenListener {
    /**
     * On access token received.
     *
     * @param token the token
     */
    void onAccessTokenReceived(String token);

    /**
     * On access token error.
     *
     * @param exception the exception
     */
    void onAccessTokenError(Exception exception);
}