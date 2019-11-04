package com.redbeemedia.enigma.cast.session;

import com.google.android.gms.common.api.Status;
import com.redbeemedia.enigma.core.util.IInternalCallbackObject;

public interface ICastControlResultHandler extends IInternalCallbackObject {
    void onRequestSent();
    void onSuccess();
    void onException(Exception e);
    void onControlFailed(Status status);
}
