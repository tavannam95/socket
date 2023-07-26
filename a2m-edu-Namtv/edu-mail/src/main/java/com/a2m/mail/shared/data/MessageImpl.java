package com.a2m.mail.shared.data;

import java.util.Date;
import java.util.List;

import com.a2m.mail.shared.domain.Message;
import com.a2m.mail.shared.domain.Tag;

public class MessageImpl extends AbstractMessageImpl implements Message{

    private List<String> extra;
    private long uid;
    private List<IMAPFlag> flags;
    private List<Tag> tags;
    private Date rDate;

    public enum IMAPFlag {
        SEEN, DELETED, RECENT, ANSWERED, JUNK, DRAFT, FLAGGED, USER
    }

    public void setFlags(List<IMAPFlag> flags) {
        this.flags = flags;
    }

    public List<IMAPFlag> getFlags() {
        return flags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }


    public void setReceivedDate(Date rDate) {
        this.rDate = rDate;
    }

    public Date getReceivedDate() {
        return rDate == null ? new Date(): rDate;
    }


    public String toString() {
        return String.valueOf(getUid());
    }

    public boolean equals(Object obj) {
        if (obj instanceof MessageImpl) {
            if (((MessageImpl)obj).getUid() == getUid()) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Long.valueOf(getUid()).hashCode();
    }

    @Override
    public void setExtra(List<String> extra) {
        this.extra = extra;

    }

    @Override
    public List<String> getExtra() {
        return extra;
    }
}
