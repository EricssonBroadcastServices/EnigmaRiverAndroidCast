package com.redbeemedia.enigma.cast.listeners;

import com.redbeemedia.enigma.cast.models.MediaTrack;
import com.redbeemedia.enigma.cast.models.ReceiverError;

import org.json.JSONObject;

import java.util.List;

public class BaseEnigmaCastListener implements IEnigmaCastListener {
    @Override
    public final void _dont_implement_IEnigmaCastListener___instead_extend_BaseEnigmaCastListener_() {
    }

    @Override
    public void onTracksUpdated(List<MediaTrack> audioTracks, List<MediaTrack> subtitleTracks) {
    }

    @Override
    public void onVolumeChange(float volume, boolean muted) {
    }

    @Override
    public void onTimeshiftEnabled(boolean timeshiftEnabled) {
    }

    @Override
    public void onAutoplay(boolean autoplay) {
    }

    @Override
    public void onLive(boolean isLive) {
    }

    @Override
    public void onProgramChanged(JSONObject program) {
    }

    @Override
    public void onEntitlementChange(JSONObject entitlementJson) {
    }

    @Override
    public void onAssetChanged(JSONObject asset) {
    }

    @Override
    public void onError(ReceiverError error) {
    }

    @Override
    public void onLiveDelay(float liveDelay) {
    }

    @Override
    public void onStartTimeLive(long utcMillis) {
    }

    @Override
    public void onSegmentMissing(long position) {
    }

    @Override
    public void onUnhandledMessageType(String type, JSONObject message) {
    }

    @Override
    public void onCastEnd() {

    }
}
