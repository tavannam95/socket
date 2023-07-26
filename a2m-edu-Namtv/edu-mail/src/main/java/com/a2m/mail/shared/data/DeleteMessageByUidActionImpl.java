package com.a2m.mail.shared.data;

import java.util.List;

import org.springframework.stereotype.Service;

import com.a2m.mail.shared.domain.DeleteMessageByUidAction;
import com.a2m.mail.shared.domain.ImapFolder;

@Service
public class DeleteMessageByUidActionImpl implements DeleteMessageByUidAction {

    public DeleteMessageByUidActionImpl(){}
    public DeleteMessageByUidActionImpl(ImapFolder folder, List<Long> messageUids){
        super();
        this.messageUids = messageUids;
    }

    private List<Long> messageUids;

    @Override
    public List<Long> getMessageUids() {
        return messageUids;
    }

    @Override
    public void setMessageUids(List<Long> messageUids) {
        this.messageUids = messageUids;
    }

}
