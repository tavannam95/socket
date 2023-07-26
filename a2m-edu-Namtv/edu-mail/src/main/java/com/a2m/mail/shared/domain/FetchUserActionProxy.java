package com.a2m.mail.shared.domain;

public interface FetchUserActionProxy {
    int getStart();
    int getOffset();
    String getSearchString();
    void setStart(int start);
    void setOffset(int offset);
    void setSearchString(String searchString);
}
