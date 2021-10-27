package com.redbeemedia.enigma.cast.manager;

import com.redbeemedia.enigma.cast.listeners.IEnigmaCastListener;
import com.redbeemedia.enigma.cast.models.MediaTrack;
import com.redbeemedia.enigma.cast.models.ReceiverError;
import com.redbeemedia.enigma.core.util.Collector;

import org.json.JSONObject;

import java.util.List;

/*package-protected*/ class EnigmaCastCollector extends Collector<IEnigmaCastListener> implements IEnigmaCastListener {
    public EnigmaCastCollector() {
        super(IEnigmaCastListener.class);
    }

    @Override
    public void onTracksUpdated(List<MediaTrack> audioTracks, List<MediaTrack> subtitleTracks) {
        forEach(listener -> listener.onTracksUpdated(audioTracks, subtitleTracks));
    }

    @Override
    public void onVolumeChange(float volume, boolean muted) {
        forEach(listener -> listener.onVolumeChange(volume, muted));
    }

    @Override
    public void onTimeshiftEnabled(boolean timeshiftEnabled) {
        forEach(listener -> listener.onTimeshiftEnabled(timeshiftEnabled));
    }

    @Override
    public void onAutoplay(boolean autoplay) {
        forEach(listener -> listener.onAutoplay(autoplay));
    }

    @Override
    public void onLive(boolean live) {
        forEach(listener -> listener.onLive(live));
    }

    @Override
    public void onProgramChanged(JSONObject program) {
        forEach(listener -> listener.onProgramChanged(program));
    }

    @Override
    public void onEntitlementChange(JSONObject entitlementJson) {
        forEach(listener -> listener.onEntitlementChange(entitlementJson));
    }

    @Override
    public void onAssetChanged(JSONObject asset) {
        forEach(listener -> listener.onAssetChanged(asset));
    }

    @Override
    public void onError(ReceiverError error) {
        forEach(listener -> listener.onError(error));
    }

    @Override
    public void onLiveDelay(float liveDelay) {
        forEach(listener -> listener.onLiveDelay(liveDelay));
    }

    @Override
    public void onStartTimeLive(long utcMillis) {
        forEach(listener -> listener.onStartTimeLive(utcMillis));
    }

    @Override
    public void onSegmentMissing(long position) {
        forEach(listener -> listener.onSegmentMissing(position));
    }

    @Override
    public void onUnhandledMessageType(String type, JSONObject message) {
        forEach(listener -> listener.onUnhandledMessageType(type, message));
    }

    @Override
    public void onCastEnd() {
        forEach(IEnigmaCastListener::onCastEnd);
    }

    @Override
    public final void _dont_implement_IEnigmaCastListener___instead_extend_BaseEnigmaCastListener_() {
    }
}
