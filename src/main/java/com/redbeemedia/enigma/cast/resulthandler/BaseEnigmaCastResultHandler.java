package com.redbeemedia.enigma.cast.resulthandler;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;

public abstract class BaseEnigmaCastResultHandler implements IEnigmaCastResultHandler {
    @Override
    public void onRequestSent() {
    }

    @Override
    public abstract void onSuccess();

    @Override
    public abstract void onException(Exception e);

    @Override
    public void onCastFailed(Status status) {
        onException(new ApiException(status));
    }
}
