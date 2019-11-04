package com.redbeemedia.enigma.cast.manager;

import android.os.Handler;

import androidx.annotation.NonNull;

import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.redbeemedia.enigma.cast.listeners.IEnigmaCastListener;
import com.redbeemedia.enigma.cast.listeners.IEnigmaCastSessionListener;
import com.redbeemedia.enigma.cast.message.EnigmaMessageConverter;
import com.redbeemedia.enigma.cast.session.EnigmaCastMessage;
import com.redbeemedia.enigma.cast.session.ICastControlRequest;
import com.redbeemedia.enigma.cast.session.ICastControlResultHandler;
import com.redbeemedia.enigma.cast.session.IEnigmaCastSession;
import com.redbeemedia.enigma.core.util.HandlerWrapper;
import com.redbeemedia.enigma.core.util.ProxyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/*package-protected*/ class EnigmaCastSession implements IEnigmaCastSession {
    private static final ICastControlResultHandler NULL_RESULT_HANDLER = new NullResultHandler();

    private static final String NAMESPACE = "urn:x-cast:com.ericsson.cast.receiver";

    private final EnigmaCastSessionCollector castSessionListeners = new EnigmaCastSessionCollector();
    private final EnigmaCastCollector castListeners = new EnigmaCastCollector();
    private final CastSession castSession;

    public EnigmaCastSession(CastSession castSession) {
        this.castSession = castSession;
    }

    public void onStart() throws IOException, JSONException {
        castSession.setMessageReceivedCallbacks(NAMESPACE, new EnigmaMessageConverter(castListeners) {
            @Override
            protected void onConversionFailed(CastDevice sender, String message, Exception exception) {
                castSessionListeners.onMessageConversionFailed(sender, message, exception);
            }
        });
        sendMessage(EnigmaCastMessage.pull());
    }

    public void onEnd() throws IOException {
        castSession.removeMessageReceivedCallbacks(NAMESPACE);
    }

    @Override
    public void sendMessage(ICastControlRequest request) {
        sendMessage(request, NULL_RESULT_HANDLER);
    }

    @Override
    public void sendMessage(ICastControlRequest request, ICastControlResultHandler resultHandler, Handler handler) {
        sendMessage(request, ProxyCallback.createCallbackOnThread(new HandlerWrapper(handler), ICastControlResultHandler.class, resultHandler));
    }

    @Override
    public void sendMessage(ICastControlRequest request, ICastControlResultHandler resultHandler) {
        try {
            request.useWith(new MessageCastControlProvider(resultHandler));
        } catch (Exception e) {
            resultHandler.onException(e);
        }
    }

    private void sendMessage(JSONObject messageJson, ICastControlResultHandler resultHandler) throws JSONException {
        try {
            PendingResult<Status> pendingResult = castSession.sendMessage(NAMESPACE, messageJson.toString());
            pendingResult.setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    if(status.isSuccess()) {
                        resultHandler.onSuccess();
                    } else {
                        resultHandler.onControlFailed(status);
                    }
                }
            });
            resultHandler.onRequestSent();
        } catch (Exception e) {
            resultHandler.onException(e);
        }
    }

    public boolean addListener(IEnigmaCastSessionListener listener) {
        return castSessionListeners.addListener(listener);
    }

    @Override
    public boolean addListener(IEnigmaCastSessionListener listener, Handler handler) {
        return castSessionListeners.addListener(listener, new HandlerWrapper(handler));
    }

    @Override
    public boolean removeListener(IEnigmaCastSessionListener listener) {
        return castSessionListeners.removeListener(listener);
    }

    @Override
    public boolean addCastListener(IEnigmaCastListener listener) {
        return castListeners.addListener(listener);
    }

    @Override
    public boolean addCastListener(IEnigmaCastListener listener, Handler handler) {
        return castListeners.addListener(listener, new HandlerWrapper(handler));
    }

    @Override
    public boolean removeCastListener(IEnigmaCastListener listener) {
        return castListeners.removeListener(listener);
    }

    @Override
    public boolean isConnected() {
        return castSession != null && castSession.isConnected();
    }

    /*package-protected*/ CastSession getCastSession() {
        return castSession;
    }

    private static class NullResultHandler implements ICastControlResultHandler {
        @Override
        public void onRequestSent() {
        }

        @Override
        public void onSuccess() {
        }

        @Override
        public void onException(Exception e) {
        }

        @Override
        public void onControlFailed(Status status) {
        }
    }

    private class MessageCastControlProvider extends CastControlProvider {
        private final ICastControlResultHandler resultHandler;

        private MessageCastControlProvider(ICastControlResultHandler resultHandler) {
            this.resultHandler = resultHandler;
        }

        @Override
        protected void sendMessage(JSONObject jsonObject) throws Exception {
            EnigmaCastSession.this.sendMessage(jsonObject, resultHandler);
        }
    }
}
