package com.a2m.mail.shared.domain;

public interface User {
    public static final String NOT_FOUND = " <<<< User not found in session >>>> ID: ";

    String getId();
    boolean getAuthenticated();
    String getName();
    String getPassword();
    void setId(String string);
    void setName(String string);
    Settings getSettings();
    void setPassword(String password);
    void setAuthenticated(boolean b);
    void setSettings(Settings settings);
}
