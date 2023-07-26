package com.a2m.mail.shared.data;

import org.springframework.stereotype.Service;

import com.a2m.mail.shared.domain.DeleteMessageAction;
import com.a2m.mail.shared.domain.ImapFolder;

@Service
public class DeleteMessageActionImpl implements DeleteMessageAction {

    protected ImapFolder folder;

    public DeleteMessageActionImpl() {
    }
    public DeleteMessageActionImpl(ImapFolder folder) {
        this.folder = folder;
    }

    @Override
    public ImapFolder getFolder() {
        return folder;
    }
    @Override
    public void setFolder(ImapFolder folder) {
        this.folder = folder;
    }

}
