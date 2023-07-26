package com.a2m.mail.shared.domain;

public interface DeleteMessageResult {
    int getCount();
    User getUser();
    ImapFolder getFolder();
}
