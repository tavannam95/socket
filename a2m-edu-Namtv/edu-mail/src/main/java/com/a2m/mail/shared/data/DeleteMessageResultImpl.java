package com.a2m.mail.shared.data;

import org.springframework.stereotype.Service;

import com.a2m.mail.shared.domain.DeleteMessageResult;
import com.a2m.mail.shared.domain.ImapFolder;
import com.a2m.mail.shared.domain.User;

@Service
public class DeleteMessageResultImpl implements DeleteMessageResult {

    private User user;
    private ImapFolder folder;
    private int deleteCount;

    public DeleteMessageResultImpl() {
    }

    public DeleteMessageResultImpl(User user, ImapFolder folder, int deleteCount) {
        this.user = user;
        this.folder = folder;
        this.deleteCount = deleteCount;
    }
    @Override
    public int getCount() {
        return deleteCount;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public ImapFolder getFolder() {
        return folder;
    }

}
