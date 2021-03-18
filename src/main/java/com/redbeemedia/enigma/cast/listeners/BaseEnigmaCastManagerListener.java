package com.redbeemedia.enigma.cast.listeners;

import androidx.annotation.Nullable;

import com.redbeemedia.enigma.cast.session.IEnigmaCastSession;

public class BaseEnigmaCastManagerListener implements IEnigmaCastManagerListener {
    @Override
    public final void _dont_implement_IEnigmaCastManagerListener___instead_extend_BaseEnigmaCastManagerListener_() {
    }

    @Override
    public void onCastSessionChanged(@Nullable IEnigmaCastSession oldSession, @Nullable IEnigmaCastSession newSession) {
    }

    @Override
    public void onException(Exception e) {
        e.printStackTrace();
    }
}
