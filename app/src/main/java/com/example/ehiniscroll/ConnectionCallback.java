package com.example.ehiniscroll;

public interface ConnectionCallback {
    void onConnectSuccess();
    void onConnectError(String errorMessage);
}
