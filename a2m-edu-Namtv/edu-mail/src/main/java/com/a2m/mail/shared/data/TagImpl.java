package com.a2m.mail.shared.data;


import com.a2m.mail.shared.domain.Tag;

public class TagImpl implements Tag {

    public final static String PREFIX = "TAG.";
    private String tagName;

    public TagImpl() {
    }

    public TagImpl(String tagName) {
        this.tagName = tagName;
    }

    public String getName() {
        return tagName;
    }

    public String getPrefix() {
        return PREFIX;
    }

    public String toString() {
        return PREFIX + tagName;
    }

}
