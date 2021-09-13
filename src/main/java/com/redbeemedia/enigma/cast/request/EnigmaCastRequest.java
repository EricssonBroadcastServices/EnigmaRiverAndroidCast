package com.redbeemedia.enigma.cast.request;

import com.redbeemedia.enigma.core.ads.IAdInsertionFactory;
import com.redbeemedia.enigma.core.ads.IAdInsertionParameters;
import com.redbeemedia.enigma.core.context.EnigmaRiverContext;
import com.redbeemedia.enigma.core.playable.AssetPlayable;
import com.redbeemedia.enigma.core.playrequest.PlayRequest;
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
    public String getSessionToken() { return session.getSessionToken(); }

    @Override
    public JSONObject buildCustomData() throws JSONException {
        JSONObject customData = new JSONObject();
        //TODO: [2021-03-18] ericssonexposure will not be used by the new cast receiver, so these lines should be removed once the old receiver is dead.
        JSONObject ericssonexposure = new JSONObject();
        customData.put("customer", session.getBusinessUnit().getCustomerName());
        customData.put("businessUnit", session.getBusinessUnit().getName());
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
            customData.put("locale", language);
        }

        IAdInsertionParameters adInsertionParameters = buildAdInsertionParameters();
        if(adInsertionParameters != null) {
            JSONObject adInsertionData = new JSONObject();
            for(String key : adInsertionParameters.getParameters().keySet()) {
                adInsertionData.put(key, adInsertionParameters.getParameters().get(key));
            }
            customData.put("adParameters", adInsertionData);
        }
        String adobeMediaToken = getPrimetimeMediaToken();
        if(adobeMediaToken != null) {
            customData.put("adobePrimetimeToken", adobeMediaToken);
        }
        return customData;
    }

    protected IAdInsertionParameters buildAdInsertionParameters() {
        IAdInsertionFactory adInsertionFactory = EnigmaRiverContext.getAdInsertionFactory();
        if(adInsertionFactory == null) {
            return null;
        }
        return adInsertionFactory.createParameters(new PlayRequest(new AssetPlayable(assetId), null));
    }

    protected String getPrimetimeMediaToken() {
        if (playbackProperties.getPrimetimeToken() != null) {
            return playbackProperties.getPrimetimeToken().token;
        }
        return null;
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
