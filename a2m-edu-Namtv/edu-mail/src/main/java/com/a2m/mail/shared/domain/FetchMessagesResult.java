package com.a2m.mail.shared.domain;

import java.util.List;

public interface FetchMessagesResult{
    int getOffset();
    int getStart();
    List<Message> getMessages();
    int getRealCount();
    int getRealUnreadCount();
}
