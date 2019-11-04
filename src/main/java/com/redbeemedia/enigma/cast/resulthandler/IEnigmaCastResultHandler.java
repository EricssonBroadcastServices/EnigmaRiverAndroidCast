package com.redbeemedia.enigma.cast.resulthandler;

import com.google.android.gms.common.api.Status;
import com.redbeemedia.enigma.core.util.IInternalCallbackObject;

public interface IEnigmaCastResultHandler extends IInternalCallbackObject {
    void onRequestSent();
    void onSuccess();
    void onException(Exception e);
    void onCastFailed(Status status);
}
