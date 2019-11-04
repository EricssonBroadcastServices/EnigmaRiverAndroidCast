package com.redbeemedia.enigma.cast.listeners;

import com.google.android.gms.cast.CastDevice;
import com.redbeemedia.enigma.core.util.IInternalListener;

public interface IEnigmaCastSessionListener extends IInternalListener {
    /**
     * <p>Inspired by {@code org.hamcrest.Matcher} from JUnit lib.</p>
     * <br>
     * <p style="margin-left: 25px; font-weight:bold;">It's easy to ignore JavaDoc, but a bit harder to ignore compile errors .</p>
     * <p style="margin-left: 50px">-- Hamcrest source</p>
     */
    @Deprecated
    void _dont_implement_IEnigmaCastSessionListener___instead_extend_BaseEnigmaCastSessionListener_();

    void onMessageConversionFailed(CastDevice sender, String message, Exception exception);
}
