package com.redbeemedia.enigma.cast.request;

import androidx.annotation.Nullable;

import com.redbeemedia.enigma.core.playrequest.AdobePrimetime;
import com.redbeemedia.enigma.core.time.Duration;

import org.json.JSONException;
import org.json.JSONObject;

public final class EnigmaCastPlaybackProperties implements IEnigmaCastPlaybackProperties {
    private final EnigmaCastPlayFrom playFrom;
    private final Duration startOffset;
    private final Long startTime;
    private AdobePrimetime adobePrimetime;
    private final String selectedSubtitleLanguageCode;
    private final String selectedAudioLanguageCode;

    private EnigmaCastPlaybackProperties(EnigmaCastPlayFrom playFrom,
                                         Duration startOffset,
                                         Long startTime,
                                         AdobePrimetime adobePrimetime,
                                         String selectedSubtitleLanguageCode,
                                         String selectedAudioLanguageCode) {
        if(playFrom == null) {
            throw new NullPointerException();
        }
        this.playFrom = playFrom;
        this.startOffset = startOffset;
        this.startTime = startTime;
        this.adobePrimetime = adobePrimetime;
        this.selectedSubtitleLanguageCode = selectedSubtitleLanguageCode;
        this.selectedAudioLanguageCode = selectedAudioLanguageCode;
    }

    @Override
    public String getSelectedAudioLanguageCode() {
        return selectedAudioLanguageCode;
    }
    @Override
    public String getSelectedSubtitleLanguageCode() {
        return selectedSubtitleLanguageCode;
    }

    @Override
    public EnigmaCastPlayFrom getPlayFrom() {
        return playFrom;
    }

    @Override
    @Nullable public AdobePrimetime getPrimetimeToken() {
        return adobePrimetime;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject playbackProperties = new JSONObject();
        playbackProperties.put("playFrom", playFrom.stringValue);
        if(startOffset != null) {
            playbackProperties.put("startOffset", startOffset.inWholeUnits(Duration.Unit.MILLISECONDS));
        }
        if(startTime != null) {
            playbackProperties.put("startTime", startTime.longValue());
        }
        return playbackProperties;
    }

    public static final class Builder {
        private EnigmaCastPlayFrom playFrom = null;
        private Duration startOffset = null;
        private Long startTime = null;
        private AdobePrimetime adobePrimetime;
        private String selectedSubtitleLanguageCode;
        private String selectedAudioLanguageCode;

        public Builder setPlayFrom(EnigmaCastPlayFrom playFrom) {
            this.playFrom = playFrom;
            return this;
        }

        public Builder setStartOffset(Duration startOffset) {
            this.playFrom = EnigmaCastPlayFrom.START_TIME;
            this.startOffset = startOffset;
            return this;
        }

        public Builder setAdobePrimetime(@Nullable AdobePrimetime primetime) {
            this.adobePrimetime = primetime;
            return this;
        }

        public Builder setStartOffsetMillis(long startOffsetMillis) {
            return setStartOffset(Duration.millis(startOffsetMillis));
        }

        public Builder setStartTimeUtcMillis(long startTimeUtcMillis) {
            this.playFrom = EnigmaCastPlayFrom.START_TIME;
            this.startTime = startTimeUtcMillis;
            return this;
        }

        public void setSelectedSubtitleLanguageCode(String selectedSubtitleLanguageCode) {
            this.selectedSubtitleLanguageCode = selectedSubtitleLanguageCode;
        }

        public void setSelectedAudioLanguageCode(String selectedAudioLanguageCode) {
            this.selectedAudioLanguageCode = selectedAudioLanguageCode;
        }

        public EnigmaCastPlaybackProperties build() {
            return new EnigmaCastPlaybackProperties(playFrom != null ? playFrom : EnigmaCastPlayFrom.DEFAULT_BEHAVIOUR, startOffset, startTime, adobePrimetime, selectedSubtitleLanguageCode, selectedAudioLanguageCode);
        }
    }
}
