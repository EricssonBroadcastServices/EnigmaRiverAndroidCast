package com.redbeemedia.enigma.cast.listeners;

import com.redbeemedia.enigma.cast.session.IEnigmaCastSession;

public class BaseEnigmaCastManagerListener implements IEnigmaCastManagerListener {
    @Override
    public final void _dont_implement_IEnigmaCastManagerListener___instead_extend_BaseEnigmaCastManagerListener_() {
    }

    @Override
    public void onCastSessionChanged(IEnigmaCastSession oldSession, IEnigmaCastSession newSession) {
    }

    @Override
    public void onException(Exception e) {
        e.printStackTrace();
    }
}
