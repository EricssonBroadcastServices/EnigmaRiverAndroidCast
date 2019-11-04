package com.redbeemedia.enigma.cast.models;
/*
 * Copyright (c) 2017 Ericsson. All Rights Reserved
 *
 * This SOURCE CODE FILE, which has been provided by Ericsson as part
 * of an Ericsson software product for use ONLY by licensed users of the
 * product, includes CONFIDENTIAL and PROPRIETARY information of Ericsson.
 *
 * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS OF
 * THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
 * THE PRODUCT.
 */

public final class MediaTrack {
    private final int id;
    private final String label;
    private final String language;
    private boolean active;

    public MediaTrack(int id, String label, String language, boolean active) {
        this.id = id;
        this.label = label;
        this.language = language;
        this.active = active;
    }

    public MediaTrack(int id, String label, String language) {
        this(id, label, language, false);
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
