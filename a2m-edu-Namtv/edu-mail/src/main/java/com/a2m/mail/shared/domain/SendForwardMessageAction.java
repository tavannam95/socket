package com.a2m.mail.shared.domain;

public interface SendForwardMessageAction extends SendMessageAction {
	ImapFolder getFolder();

    long getUid();

    void setFolder(ImapFolder folder);

    void setUid(long uid);
}
