package com.a2m.mail.shared.data;

import com.a2m.mail.shared.domain.ImapFolder;
import com.a2m.mail.shared.domain.SendReplyMessageAction;
import com.a2m.mail.shared.domain.SmtpMessage;

public class SendReplyMessageActionImpl extends SendForwardMessageActionImpl implements SendReplyMessageAction {
	public SendReplyMessageActionImpl() {
    }
    public SendReplyMessageActionImpl(SmtpMessage msg, ImapFolder folder, long uid) {
        super(msg, folder, uid);
    }
}
