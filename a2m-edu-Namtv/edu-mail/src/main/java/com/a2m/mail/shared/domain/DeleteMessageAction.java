package com.a2m.mail.shared.domain;

public interface DeleteMessageAction {
    ImapFolder getFolder();
    void setFolder(ImapFolder folder);
}
