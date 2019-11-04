package com.redbeemedia.enigma.cast.request;

import org.json.JSONException;
import org.json.JSONObject;

public interface IEnigmaCastPlaybackProperties {
    EnigmaCastPlayFrom getPlayFrom();

    JSONObject toJSON() throws JSONException;
}
