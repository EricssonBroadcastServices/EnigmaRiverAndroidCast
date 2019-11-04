package com.redbeemedia.enigma.cast.listeners;

import com.google.android.gms.cast.CastDevice;

public class BaseEnigmaCastSessionListener extends BaseEnigmaCastListener implements IEnigmaCastSessionListener {
    @Override
    public final void _dont_implement_IEnigmaCastSessionListener___instead_extend_BaseEnigmaCastSessionListener_() {
    }

    @Override
    public void onMessageConversionFailed(CastDevice sender, String message, Exception exception) {
    }
}
