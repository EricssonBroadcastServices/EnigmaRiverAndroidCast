package com.redbeemedia.enigma.cast.models;

import androidx.annotation.NonNull;

import org.json.JSONObject;

/**
 * Error originating from the receiver
 */
public final class ReceiverError {
    private final String type;
    private final String code;
    private final String message;

    public ReceiverError(JSONObject error) {
        this.type = error.optString("type");
        this.code = error.optString("code", "N/A");
        this.message = error.optString("message", "");
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    @NonNull
    @Override
    public String toString() {
        return type+"(code "+code+"): "+message;
    }
}
