package com.redbeemedia.enigma.cast.request;

import androidx.annotation.Nullable;

import com.redbeemedia.enigma.core.playrequest.AdobePrimetime;

import org.json.JSONException;
import org.json.JSONObject;

public interface IEnigmaCastPlaybackProperties {
    EnigmaCastPlayFrom getPlayFrom();

    String getSelectedSubtitleLanguageCode();

    String getSelectedAudioLanguageCode();

    @Nullable AdobePrimetime getPrimetimeToken();

    JSONObject toJSON() throws JSONException;
}
