package com.redbeemedia.enigma.cast.manager;

import android.content.Context;
import android.os.Handler;

import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaLoadOptions;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.SessionManager;
import com.google.android.gms.cast.framework.SessionManagerListener;
import com.google.android.gms.common.api.Status;
import com.redbeemedia.enigma.cast.listeners.IEnigmaCastListener;
import com.redbeemedia.enigma.cast.listeners.IEnigmaCastManagerListener;
import com.redbeemedia.enigma.cast.request.IEnigmaCastRequest;
import com.redbeemedia.enigma.cast.resulthandler.IEnigmaCastResultHandler;
import com.redbeemedia.enigma.cast.session.IEnigmaCastSession;
import com.redbeemedia.enigma.core.util.AndroidThreadUtil;
import com.redbeemedia.enigma.core.util.HandlerWrapper;
import com.redbeemedia.enigma.core.util.OpenContainer;
import com.redbeemedia.enigma.core.util.OpenContainerUtil;
import com.redbeemedia.enigma.core.util.ProxyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public final class EnigmaCastManager implements IEnigmaCastManager {
    private static final OpenContainer<IEnigmaCastManager> instance = new OpenContainer<>(null);

    private static final IEnigmaCastResultHandler NULL_RESULT_HANDLER = new NullResultHandler();

    private final EnigmaCastManagerCollector castManagerListeners = new EnigmaCastManagerCollector();
    private final EnigmaCastCollector castListeners = new EnigmaCastCollector();
    private final OpenContainer<EnigmaCastSession> enigmaCastSession = new OpenContainer<>(null);

    /**
     * Must only be called from the main/ui thread.
     *
     * @param context The application context
     * @return An {@link IEnigmaCastManager}
     */
    public static IEnigmaCastManager getSharedInstance(Context context) {
        AndroidThreadUtil.verifyCalledFromUiThread();

        //Ensure we are potentially only holding on to application context
        if(!Objects.equals(context.getApplicationContext(), context)) {
            context = context.getApplicationContext();
        }
        synchronized (instance) {
            if(instance.value == null) {
                EnigmaCastManager castManager = new EnigmaCastManager(CastContext.getSharedInstance(context));
                instance.value = castManager;
            }
            return instance.value;
        }
    }

    private EnigmaCastManager(CastContext castContext) {
        SessionManager sessionManager = castContext.getSessionManager();
        updateEnigmaCastSession(sessionManager.getCurrentCastSession());
        sessionManager.addSessionManagerListener(new SessionManagerListener<CastSession>() {
            @Override
            public void onSessionStarting(CastSession castSession) {
            }

            @Override
            public void onSessionStarted(CastSession castSession, String caseSessionId) {
                updateEnigmaCastSession(castSession);
            }

            @Override
            public void onSessionStartFailed(CastSession castSession, int error) {
                updateEnigmaCastSession(null);
            }

            @Override
            public void onSessionEnding(CastSession castSession) {
            }

            @Override
            public void onSessionEnded(CastSession castSession, int error) {
                updateEnigmaCastSession(null);
            }

            @Override
            public void onSessionResuming(CastSession castSession, String s) {
            }

            @Override
            public void onSessionResumed(CastSession castSession, boolean b) {
                updateEnigmaCastSession(castSession);
            }

            @Override
            public void onSessionResumeFailed(CastSession castSession, int i) {
                updateEnigmaCastSession(null);
            }

            @Override
            public void onSessionSuspended(CastSession castSession, int i) {
            }
        }, CastSession.class);
    }

    @Override
    public IEnigmaCastSession getCurrentEnigmaCastSession() {
        return OpenContainerUtil.getValueSynchronized(enigmaCastSession);
    }

    @Override
    public boolean addListener(IEnigmaCastManagerListener listener) {
        return castManagerListeners.addListener(listener);
    }

    @Override
    public boolean addListener(IEnigmaCastManagerListener listener, Handler handler) {
        return castManagerListeners.addListener(listener, new HandlerWrapper(handler));
    }

    @Override
    public boolean removeListener(IEnigmaCastManagerListener listener) {
        return castManagerListeners.removeListener(listener);
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
        AndroidThreadUtil.verifyCalledFromUiThread();
        synchronized (enigmaCastSession) {
            return enigmaCastSession.value != null && enigmaCastSession.value.isConnected();
        }
    }

    @Override
    public void play(IEnigmaCastRequest castRequest, IEnigmaCastResultHandler suppliedResultHandler) {
        final IEnigmaCastResultHandler resultHandler = suppliedResultHandler != null ? suppliedResultHandler : NULL_RESULT_HANDLER;
        try {
            String assetId = castRequest.getAssetId();
            if (assetId == null) {
                throw new NullPointerException("assetId was null");
            }
            final EnigmaCastSession currentEnigmaCastSession = OpenContainerUtil.getValueSynchronized(enigmaCastSession);
            if (currentEnigmaCastSession == null) {
                throw new RuntimeException("No active cast session!");
            } else {
                MediaInfo mediaInfo = buildMediaInfo(assetId);
                JSONObject customData;
                try {
                    customData = castRequest.buildCustomData();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                MediaLoadOptions loadOptions = new MediaLoadOptions.Builder()
                        .setAutoplay(true)
                        .setCustomData(customData)
                        .setCredentials(castRequest.getSessionToken())
                        .build();

                currentEnigmaCastSession.getCastSession().getRemoteMediaClient().load(mediaInfo, loadOptions).addStatusListener(status -> {
                    if (status.isSuccess()) {
                        resultHandler.onSuccess();
                    } else {
                        resultHandler.onCastFailed(status);
                    }
                });
                resultHandler.onRequestSent();
            }
        } catch (RuntimeException e) {
            resultHandler.onException(e);
            return;
        }
    }

    @Override
    public void play(IEnigmaCastRequest castRequest, IEnigmaCastResultHandler resultHandler, Handler handler) {
        IEnigmaCastResultHandler resultHandlerOnThread = ProxyCallback.createCallbackOnThread(new HandlerWrapper(handler), IEnigmaCastResultHandler.class, resultHandler);
        play(castRequest, resultHandlerOnThread);
    }

    private MediaInfo buildMediaInfo(String assetId) {
        MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);

        return new MediaInfo.Builder(assetId)
                .setMetadata(movieMetadata)
                .build();
    }

    private void updateEnigmaCastSession(CastSession castSession) {
        synchronized (enigmaCastSession) {
            if(castSession != null) {
                if(enigmaCastSession.value == null || !Objects.equals(enigmaCastSession.value.getCastSession(), castSession)) {
                    changeEnigmaCastSession(new EnigmaCastSession(castSession));
                }
            } else {
                if(enigmaCastSession.value != null) {
                    changeEnigmaCastSession(null);
                }
            }
        }
    }

    private void changeEnigmaCastSession(EnigmaCastSession newEnigmaCastSession) {
        OpenContainerUtil.setValueSynchronized(enigmaCastSession, newEnigmaCastSession, ((oldValue, newValue) -> {
            if(oldValue != null) {
                try {
                    try {
                        oldValue.onEnd();
                    } finally {
                        oldValue.removeCastListener(castListeners);
                    }
                } catch (Exception e) {
                    castManagerListeners.onException(e);
                }
            }
            if(newValue != null) {
                try {
                    try {
                        newValue.addCastListener(castListeners);
                    } finally {
                        newValue.onStart();
                    }
                } catch (Exception e) {
                    castManagerListeners.onException(e);
                }
            }
            castManagerListeners.onCastSessionChanged(oldValue, newValue);
        }));
    }

    private static class NullResultHandler implements IEnigmaCastResultHandler {
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
        public void onCastFailed(Status status) {
        }
    }
}
