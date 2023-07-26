package com.a2m.mail.shared.domain;

import java.util.List;

public interface DeleteMessageByUidAction {
    List<Long> getMessageUids();
    void setMessageUids(List<Long> messageUids);
}
