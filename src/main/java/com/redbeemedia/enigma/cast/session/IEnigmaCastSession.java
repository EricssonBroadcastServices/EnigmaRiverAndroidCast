package com.redbeemedia.enigma.cast.session;

import android.os.Handler;

import com.redbeemedia.enigma.cast.listeners.IEnigmaCastListener;
import com.redbeemedia.enigma.cast.listeners.IEnigmaCastSessionListener;

public interface IEnigmaCastSession {
    void sendMessage(ICastControlRequest request);
    void sendMessage(ICastControlRequest request, ICastControlResultHandler resultHandler);
    void sendMessage(ICastControlRequest request, ICastControlResultHandler resultHandler, Handler handler);

    boolean addListener(IEnigmaCastSessionListener listener);
    boolean addListener(IEnigmaCastSessionListener listener, Handler handler);
    boolean removeListener(IEnigmaCastSessionListener listener);

    boolean addCastListener(IEnigmaCastListener listener);
    boolean addCastListener(IEnigmaCastListener listener, Handler handler);
    boolean removeCastListener(IEnigmaCastListener listener);

    boolean isConnected();
}
