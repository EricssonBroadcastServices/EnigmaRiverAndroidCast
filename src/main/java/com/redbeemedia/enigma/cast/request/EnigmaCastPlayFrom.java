package com.redbeemedia.enigma.cast.request;

public enum EnigmaCastPlayFrom {
    /**
     * Start at beginning of the program if programId is included otherwise start at live edge
     */
    DEFAULT_BEHAVIOUR("defaultBehaviour"),
    /**
     * Start at a Unix startTime
     */
    START_TIME("startTime"),
    /**
     * Start at the beginning of the program
     */
    BEGINNING("beginning"),
    /**
     * Start at the bookmark returned by EMP backend. If there is no bookmark, it falls back to the defaultBehaviour
     */
    BOOKMARK("bookmark"),
    ;

    public final String stringValue;

    EnigmaCastPlayFrom(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
