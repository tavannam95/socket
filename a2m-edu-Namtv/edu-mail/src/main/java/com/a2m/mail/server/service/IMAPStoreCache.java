package com.a2m.mail.server.service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;

import com.a2m.mail.shared.domain.User;
import com.sun.mail.imap.IMAPStore;

public interface IMAPStoreCache {
    void delete(String username);
    void delete(User user);
    IMAPStore get(User user) throws MessagingException;
    Session getMailSession(User user);
    String sendMessage(Message msg);
}
