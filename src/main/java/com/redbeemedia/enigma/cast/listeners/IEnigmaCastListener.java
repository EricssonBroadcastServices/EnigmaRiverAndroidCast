package com.redbeemedia.enigma.cast.listeners;

import com.redbeemedia.enigma.cast.models.MediaTrack;
import com.redbeemedia.enigma.cast.models.ReceiverError;
import com.redbeemedia.enigma.core.util.IInternalListener;

import org.json.JSONObject;

import java.util.List;

public interface IEnigmaCastListener extends IInternalListener {
    /**
     * <p>Inspired by {@code org.hamcrest.Matcher} from JUnit lib.</p>
     * <br>
     * <p style="margin-left: 25px; font-weight:bold;">It's easy to ignore JavaDoc, but a bit harder to ignore compile errors .</p>
     * <p style="margin-left: 50px">-- Hamcrest source</p>
     */
    @Deprecated
    void _dont_implement_IEnigmaCastListener___instead_extend_BaseEnigmaCastListener_();

    /**
     * Triggered when the selected audio tracks change or when a sender app request it
     * @param audioTracks list of available audio tracks
     * @param subtitleTracks list of available subtitle tracks
     */
    void onTracksUpdated(List<MediaTrack> audioTracks, List<MediaTrack> subtitleTracks);

    /**
     * Triggered when the audio volume changes (eg. using the TV remote)
     * @param volume
     * @param muted
     */
    void onVolumeChange(float volume, boolean muted);

    /**
     * Triggered when timeshift is enabled or disabled (when disabled the user can't pause the stream)
     * @param timeshiftEnabled
     */
    void onTimeshiftEnabled(boolean timeshiftEnabled);

    void onAutoplay(boolean autoplay);

    /**
     * Triggered when the stream type changes (live or timeshift)
     * @param live
     */
    void onLive(boolean live);

    void onProgramChanged(JSONObject program);

    void onEntitlementChange(JSONObject entitlementJson);

    void onAssetChanged(JSONObject asset);

    /**
     * Triggered when the receiver throws an error
     * @param error
     */
    void onError(ReceiverError error);

    void onLiveDelay(float liveDelay);

    void onStartTimeLive(long utcMillis);

    void onSegmentMissing(long position);

    void onUnhandledMessageType(String type, JSONObject message);
}
