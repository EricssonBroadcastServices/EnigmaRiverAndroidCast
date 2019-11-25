package com.redbeemedia.enigma.cast.request;

import com.redbeemedia.enigma.core.context.EnigmaRiverContext;
import com.redbeemedia.enigma.core.session.ISession;

import org.json.JSONException;
import org.json.JSONObject;

public final class EnigmaCastRequest implements IEnigmaCastRequest {
    private final String assetId;
    private final ISession session;
    private final IEnigmaCastPlaybackProperties playbackProperties;
    private final String language;

    public EnigmaCastRequest(String assetId, ISession session, IEnigmaCastPlaybackProperties playbackProperties, String language) {
        this.assetId = assetId;
        this.session = session;
        this.playbackProperties = playbackProperties;
        this.language = language;
    }

    @Override
    public String getAssetId() {
        return assetId;
    }

    @Override
    public JSONObject buildCustomData() throws JSONException {
        JSONObject customData = new JSONObject();
        JSONObject ericssonexposure = new JSONObject();
        ericssonexposure.put("customer", session.getBusinessUnit().getCustomerName());
        ericssonexposure.put("businessUnit", session.getBusinessUnit().getName());
        ericssonexposure.put("sessionToken", session.getSessionToken());
        ericssonexposure.put("exposureApiURL", EnigmaRiverContext.getExposureBaseUrl());
        customData.put("ericssonexposure", ericssonexposure);
        customData.put("assetId", assetId);

        if(playbackProperties != null) {
            customData.put("playbackProperties", playbackProperties.toJSON());
        }

        if(language != null) {
            customData.put("language", language);
        }

        return customData;
    }

    public static final class Builder {
        private final String assetId;
        private final ISession session;
        private IEnigmaCastPlaybackProperties playbackProperties = null;
        private String language = null;

        public Builder(String assetId, ISession session) {
            this.assetId = assetId;
            this.session = session;
        }

        public Builder setPlaybackProperties(IEnigmaCastPlaybackProperties playbackProperties) {
            this.playbackProperties = playbackProperties;
            return this;
        }

        public Builder setLanguage(String language) {
            this.language = language;
            return this;
        }

        public EnigmaCastRequest build() {
            return new EnigmaCastRequest(assetId, session, playbackProperties, language);
        }
    }
}
