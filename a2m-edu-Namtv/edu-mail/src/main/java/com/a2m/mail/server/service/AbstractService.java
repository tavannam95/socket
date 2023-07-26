package com.a2m.mail.server.service;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;

import com.a2m.mail.shared.data.SConsts;
import com.a2m.mail.shared.domain.User;
import com.a2m.mail.shared.exception.HupaException;
import com.a2m.mail.shared.exception.InvalidSessionException;
import com.google.inject.Inject;
import com.google.inject.Provider;


public abstract class AbstractService {

    @Inject protected IMAPStoreCache cache;
    @Inject protected Provider<HttpSession> httpSessionProvider;
    @Inject protected Log logger;

    protected User getUser() throws HupaException {
        User user = (User) httpSessionProvider.get().getAttribute(SConsts.USER_SESS_ATTR);
        System.out.println(user);
        if (user == null) {
            throw new InvalidSessionException(getClass().getSimpleName()
                    + User.NOT_FOUND
                    + httpSessionProvider.get().getId());
        } else {
            return user;
        }
    }
}
