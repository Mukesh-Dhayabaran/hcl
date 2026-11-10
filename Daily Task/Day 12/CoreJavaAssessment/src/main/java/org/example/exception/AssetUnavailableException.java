package org.example.exception;

public class AssetUnavailableException extends RuntimeException {

    public AssetUnavailableException(String message) {
        super(message);
    }
}
