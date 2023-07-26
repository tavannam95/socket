package com.a2m.mail.server.service;

import java.util.List;

import com.a2m.mail.shared.domain.DeleteMessageResult;
import com.a2m.mail.shared.domain.ImapFolder;
import com.a2m.mail.shared.domain.User;
import com.a2m.mail.shared.exception.HupaException;

public interface DeleteMessageByUidService {
    public DeleteMessageResult delete(User user, ImapFolder folder, List uid) throws HupaException;
    
    public DeleteMessageResult restoreDeleted(User user, ImapFolder folder, List uid, String toFolderName) throws HupaException;
}
