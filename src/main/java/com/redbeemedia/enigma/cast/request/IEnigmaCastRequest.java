package com.redbeemedia.enigma.cast.request;

import org.json.JSONException;
import org.json.JSONObject;

public interface IEnigmaCastRequest {
    String getAssetId();
    JSONObject buildCustomData() throws JSONException;
    String getSessionToken();
}
