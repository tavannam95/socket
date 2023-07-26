package com.a2m.mail.shared.domain;

import java.util.Date;
import java.util.List;

import com.a2m.mail.shared.data.MessageImpl.IMAPFlag;

public interface Message {
    List<IMAPFlag> getFlags();
    Date getReceivedDate();
    boolean getHasAttachment();
    String getFrom();
    String getSubject();
    List<String> getCc();
    List<String> getTo();
    long getUid();
    String getReplyto();
    List<String> getExtra();

    void setTo(List<String> to);
    void setCc(List<String> cc);
    void setReplyto(String string);
    void setUid(long uid);
    void setFlags(List<IMAPFlag> iFlags);
    void setTags(List<Tag> tags);
    void setHasAttachments(boolean hasAttachment);
    void setSubject(String cellValue);
    void setFrom(String cellValue);
    void setReceivedDate(Date cellValue);
    void setExtra(List<String> extra);
}
