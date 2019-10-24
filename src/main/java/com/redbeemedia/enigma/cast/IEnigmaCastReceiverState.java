package com.redbeemedia.enigma.cast;

public interface IEnigmaCastReceiverState {
    float getVolume();
    boolean isMuted();
    boolean isTimeshiftEnabled();
    boolean isAutoplay();
    boolean isLive();
    float getLiveDelay();
    long getStartTimeLiveUtcMillis();
}
