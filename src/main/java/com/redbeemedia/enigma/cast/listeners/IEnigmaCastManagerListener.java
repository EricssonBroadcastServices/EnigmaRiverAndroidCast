package com.redbeemedia.enigma.cast.listeners;

import com.redbeemedia.enigma.cast.session.IEnigmaCastSession;
import com.redbeemedia.enigma.core.util.IInternalListener;

public interface IEnigmaCastManagerListener extends IInternalListener {
    /**
     * <p>Inspired by {@code org.hamcrest.Matcher} from JUnit lib.</p>
     * <br>
     * <p style="margin-left: 25px; font-weight:bold;">It's easy to ignore JavaDoc, but a bit harder to ignore compile errors .</p>
     * <p style="margin-left: 50px">-- Hamcrest source</p>
     */
    @Deprecated
    void _dont_implement_IEnigmaCastManagerListener___instead_extend_BaseEnigmaCastManagerListener_();

    void onCastSessionChanged(IEnigmaCastSession oldSession, IEnigmaCastSession newSession);
    void onException(Exception e);
}
