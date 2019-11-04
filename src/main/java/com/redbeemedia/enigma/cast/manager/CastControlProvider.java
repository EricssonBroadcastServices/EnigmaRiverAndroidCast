package com.redbeemedia.enigma.cast.manager;

import com.redbeemedia.enigma.cast.session.ICastControlProvider;

import org.json.JSONObject;

/*package-protected*/ abstract class CastControlProvider implements ICastControlProvider {
    protected abstract void sendMessage(JSONObject jsonObject) throws Exception;

    @Override
    public void pull() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "pull");
        jsonObject.put("data", new JSONObject());
        sendMessage(jsonObject);
    }

    @Override
    public void showTextTrack(String language, String kind) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "showtexttrack");
        JSONObject data = new JSONObject();
        data.put("language", language);
        if(kind != null) {
            data.put("kind", kind);
        }
        jsonObject.put("data", data);
        sendMessage(jsonObject);
    }

    @Override
    public void hideTextTrack() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "hidetexttrack");
        sendMessage(jsonObject);
    }

    @Override
    public void selectAudioTrack(String language, String kind) throws Exception  {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "selectaudiotrack");
        JSONObject data = new JSONObject();
        data.put("language", language);
        if(kind != null) {
            data.put("kind", kind);
        }
        jsonObject.put("data", data);
        sendMessage(jsonObject);
    }

    @Override
    public void setPlayaheadTime(long utcMillis) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "playheadtime");
        jsonObject.put("data", utcMillis);
        sendMessage(jsonObject);
    }

    @Override
    public void playNextProgram() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "playnextprogram");
        sendMessage(jsonObject);
    }

    @Override
    public void playPreviousProgram(boolean startFromEnd) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "playpreviousprogram");
        JSONObject data = new JSONObject();
        data.put("end", startFromEnd);
        jsonObject.put("data", data);
        sendMessage(jsonObject);
    }

    @Override
    public void goToLive() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "gotolive");
        sendMessage(jsonObject);
    }

    @Override
    public void loadNextSource() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "loadnextsource");
        sendMessage(jsonObject);
    }

    @Override
    public void sendCustomMessage(JSONObject jsonObject) throws Exception {
        sendMessage(jsonObject);
    }
}
