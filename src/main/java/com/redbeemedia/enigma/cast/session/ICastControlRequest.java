package com.redbeemedia.enigma.cast.session;

public interface ICastControlRequest {
    void useWith(ICastControlProvider handler) throws Exception;
}
