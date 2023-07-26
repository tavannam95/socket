package com.a2m.mail.shared.domain;

public interface FetchMessagesAction {
    ImapFolder getFolder();
    int getStart();
    int getOffset();
    String getSearchString();
    void setFolder(ImapFolder folder);
    void setStart(int start);
    void setOffset(int offset);
    void setSearchString(String searchString);
}
