package com.redbeemedia.enigma.cast.message;

import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastDevice;
import com.redbeemedia.enigma.cast.listeners.IEnigmaCastListener;
import com.redbeemedia.enigma.cast.models.MediaTrack;
import com.redbeemedia.enigma.cast.models.ReceiverError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class EnigmaMessageConverter implements Cast.MessageReceivedCallback {
    private final IEnigmaCastListener listener;

    public EnigmaMessageConverter(IEnigmaCastListener listener) {
        this.listener = listener;
    }

    protected abstract void onConversionFailed(CastDevice sender, String message, Exception exception);

    @Override
    public void onMessageReceived(CastDevice castDevice, String namespace, String message) {
        try {
            JSONObject jsonObject = new JSONObject(message);
            String type = jsonObject.getString("type");
            switch(type) {
                case "tracksupdated": {
                    JSONObject tracksInfo = jsonObject.getJSONObject("data").getJSONObject("tracksInfo");
                    JSONArray activeTrackIdsArray = tracksInfo.getJSONArray("activeTrackIds");
                    List<Integer> activeTrackIds = new ArrayList<>();
                    for(int i = 0; i < activeTrackIdsArray.length(); i++) {
                        activeTrackIds.add(activeTrackIdsArray.getInt(i));
                    }

                    List<MediaTrack> audioTracks = new ArrayList<>();
                    List<MediaTrack> subtitleTracks = new ArrayList<>();
                    JSONArray tracks = tracksInfo.getJSONArray("tracks");
                    for(int i = 0; i < tracks.length(); i++) {
                        JSONObject track = tracks.getJSONObject(i);
                        String label = track.getString("label");
                        int trackId = track.getInt("trackId");
                        String language = track.getString("language");
                        String trackType = track.getString("type");
                        MediaTrack mediaTrack = new MediaTrack(trackId, label, language, activeTrackIds.contains(trackId));
                        if("text".equals(trackType)) {
                            subtitleTracks.add(mediaTrack);
                        } else if("audio".equals(trackType)) {
                            audioTracks.add(mediaTrack);
                        }
                    }
                    listener.onTracksUpdated(audioTracks, subtitleTracks);
                } break;
                case "volumechange": {
                    JSONObject data = jsonObject.getJSONObject("data");
                    float volume = (float) data.getDouble("volume");
                    boolean muted = data.getBoolean("muted");
                    listener.onVolumeChange(volume, muted);
                } break;
                case "timeShiftEnabled": {
                    boolean enabled = jsonObject.getBoolean("data");
                    listener.onTimeshiftEnabled(enabled);
                } break;
                case "autoplay": {
                    boolean autoplay = jsonObject.getBoolean("data");
                    listener.onAutoplay(autoplay);
                } break;
                case "isLive": {
                    boolean live = jsonObject.getBoolean("data");
                    listener.onLive(live);
                } break;
                case "liveDelay": {
                    float liveDelay = (float) jsonObject.getDouble("data");
                    listener.onLiveDelay(liveDelay);
                } break;
                case "startTimeLive": {
                    long utcMillis = jsonObject.getLong("data");
                    listener.onStartTimeLive(utcMillis);
                } break;
                case "programchanged": {
                    JSONObject program = jsonObject.getJSONObject("data").getJSONObject("program");
                    listener.onProgramChanged(program);
                } break;
                case "assetchanged": {
                    JSONObject asset = jsonObject.getJSONObject("data").getJSONObject("asset");
                    listener.onAssetChanged(asset);
                } break;
                case "entitlementchange": {
                    JSONObject entitlement = jsonObject.getJSONObject("data").getJSONObject("entitlement");
                    listener.onEntitlementChange(entitlement);
                } break;
                case "error": {
                    JSONObject error = jsonObject.getJSONObject("data");
                    listener.onError(new ReceiverError(error));
                } break;
                case "segmentmissing": {
                    long position = jsonObject.getLong("data");
                    listener.onSegmentMissing(position);
                } break;
                default: {
                    listener.onUnhandledMessageType(type, jsonObject);
                } break;
            }
        } catch (Exception e) {
            onConversionFailed(castDevice, message, e);
        }
    }
}
