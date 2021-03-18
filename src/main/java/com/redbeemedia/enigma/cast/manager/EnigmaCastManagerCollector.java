package com.redbeemedia.enigma.cast.manager;

import androidx.annotation.Nullable;

import com.redbeemedia.enigma.cast.listeners.IEnigmaCastManagerListener;
import com.redbeemedia.enigma.cast.session.IEnigmaCastSession;
import com.redbeemedia.enigma.core.util.Collector;

/*package-protected*/ class EnigmaCastManagerCollector extends Collector<IEnigmaCastManagerListener> implements IEnigmaCastManagerListener {
    public EnigmaCastManagerCollector() {
        super(IEnigmaCastManagerListener.class);
    }

    @Override
    public void onCastSessionChanged(@Nullable IEnigmaCastSession oldSession, @Nullable IEnigmaCastSession newSession) {
        forEach(listener -> listener.onCastSessionChanged(oldSession, newSession));
    }

    @Override
    public void onException(Exception e) {
        e.printStackTrace();
        forEach(listener -> listener.onException(e));
    }

    @Override
    public final void _dont_implement_IEnigmaCastManagerListener___instead_extend_BaseEnigmaCastManagerListener_() {
    }
}
