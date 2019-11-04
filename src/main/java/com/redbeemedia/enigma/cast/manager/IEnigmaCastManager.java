package com.redbeemedia.enigma.cast.manager;

import android.os.Handler;

import com.redbeemedia.enigma.cast.listeners.IEnigmaCastListener;
import com.redbeemedia.enigma.cast.listeners.IEnigmaCastManagerListener;
import com.redbeemedia.enigma.cast.request.IEnigmaCastRequest;
import com.redbeemedia.enigma.cast.resulthandler.IEnigmaCastResultHandler;
import com.redbeemedia.enigma.cast.session.IEnigmaCastSession;

public interface IEnigmaCastManager {
    void play(IEnigmaCastRequest castRequest, IEnigmaCastResultHandler resultHandler);
    void play(IEnigmaCastRequest castRequest, IEnigmaCastResultHandler resultHandler, Handler handler);

    IEnigmaCastSession getCurrentEnigmaCastSession();

    /**
     *  Must be called from the main thread.
     * @return
     */
    boolean isConnected();

    boolean addListener(IEnigmaCastManagerListener listener);
    boolean addListener(IEnigmaCastManagerListener listener, Handler handler);
    boolean removeListener(IEnigmaCastManagerListener listener);

    boolean addCastListener(IEnigmaCastListener listener);
    boolean addCastListener(IEnigmaCastListener listener, Handler handler);
    boolean removeCastListener(IEnigmaCastListener listener);

}
