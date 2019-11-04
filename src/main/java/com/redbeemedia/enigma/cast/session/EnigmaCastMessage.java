package com.redbeemedia.enigma.cast.session;

public class EnigmaCastMessage {
    private EnigmaCastMessage() {};

    public static ICastControlRequest pull() {
        return handler -> handler.pull();
    }

    public static ICastControlRequest showTextTrack(String language, String kind) {
        return handler -> handler.showTextTrack(language, kind);
    }

    public static ICastControlRequest hideTextTrack() {
        return handler -> handler.hideTextTrack();
    }

    public static ICastControlRequest selectAudioTrack(String language, String kind) {
        return handler -> handler.selectAudioTrack(language, kind);
    }

    public static ICastControlRequest setPlayaheadTime(long utcMillis) {
        return handler -> handler.setPlayaheadTime(utcMillis);
    }

    public static ICastControlRequest playNextProgram() {
        return handler -> handler.playNextProgram();
    }

    public static ICastControlRequest playPreviousProgram() {
        return handler -> handler.playPreviousProgram(false);
    }

    public static ICastControlRequest playPreviousProgram(boolean startFromEnd) {
        return handler -> handler.playPreviousProgram(startFromEnd);
    }

    public static ICastControlRequest goToLive() {
        return handler -> handler.goToLive();
    }

    public static ICastControlRequest loadNextSource() {
        return handler -> handler.loadNextSource();
    }
}
