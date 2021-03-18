package com.redbeemedia.enigma.cast.manager;

import com.google.android.gms.cast.CastDevice;
import com.redbeemedia.enigma.cast.listeners.IEnigmaCastSessionListener;
import com.redbeemedia.enigma.core.util.Collector;

/*package-protected*/ class EnigmaCastSessionCollector extends Collector<IEnigmaCastSessionListener> implements IEnigmaCastSessionListener {

    public EnigmaCastSessionCollector() {
        super(IEnigmaCastSessionListener.class);
    }

    @Override
    public void onMessageConversionFailed(CastDevice sender, String message, Exception exception) {
        exception.printStackTrace();
        forEach(listener -> listener.onMessageConversionFailed(sender, message, exception));
    }

    @Override
    public final void _dont_implement_IEnigmaCastSessionListener___instead_extend_BaseEnigmaCastSessionListener_() {
    }
}
