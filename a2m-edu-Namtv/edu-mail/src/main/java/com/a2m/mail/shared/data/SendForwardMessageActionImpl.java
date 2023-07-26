package com.a2m.mail.shared.data;

import com.a2m.mail.shared.domain.ImapFolder;
import com.a2m.mail.shared.domain.SendForwardMessageAction;
import com.a2m.mail.shared.domain.SmtpMessage;

public class SendForwardMessageActionImpl extends SendMessageActionImpl implements SendForwardMessageAction {
	
	private long uid;
    private ImapFolder folder;
    private String inReplyTo;
    private String references;

    public SendForwardMessageActionImpl() {
    }

    public SendForwardMessageActionImpl(SmtpMessage msg, ImapFolder folder, long uid) {
        super(msg);
        this.uid = uid;
        this.folder = folder;
    }

    @Override
    public void setFolder(ImapFolder folder) {
        this.folder = folder;
    }

    @Override
    public void setUid(long uid) {
        this.uid = uid;
    }

    @Override
    public long getUid() {
        return uid;
    }

    @Override
    public ImapFolder getFolder() {
        return folder;
    }

    @Override
    public String getInReplyTo() {
        return inReplyTo;
    }

    @Override
    public String getReferences() {
        return references;
    }

    @Override
    public void setInReplyTo(String inReplyTo) {
        this.inReplyTo = inReplyTo;
    }

    @Override
    public void setReferences(String references) {
        this.references = references;
    }

}
