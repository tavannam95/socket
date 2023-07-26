package com.a2m.mail.server.service;

import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;

import com.a2m.mail.shared.domain.FetchMessagesAction;
import com.a2m.mail.shared.domain.FetchMessagesResult;
import com.a2m.mail.shared.domain.User;
import com.a2m.mail.shared.exception.HupaException;

public interface FetchMessagesService {
    FetchMessagesResult fetch(FetchMessagesAction action, User user) throws HupaException;
    List<com.a2m.mail.shared.domain.Message> convert(int offset, com.sun.mail.imap.IMAPFolder folder, Message[] messages) throws MessagingException;
}
