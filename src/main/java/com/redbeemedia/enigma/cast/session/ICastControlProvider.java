package com.redbeemedia.enigma.cast.session;

import org.json.JSONObject;

public interface ICastControlProvider {
    void pull() throws Exception;
    void showTextTrack(String language, String kind) throws Exception;
    void hideTextTrack() throws Exception;
    void selectAudioTrack(String language, String kind) throws Exception;
    void setPlayaheadTime(long utcMillis) throws Exception;
    void playNextProgram() throws Exception;
    void playPreviousProgram(boolean startFromEnd) throws Exception;
    void goToLive() throws Exception;
    void loadNextSource() throws Exception;

    void sendCustomMessage(JSONObject jsonObject) throws Exception;
}
