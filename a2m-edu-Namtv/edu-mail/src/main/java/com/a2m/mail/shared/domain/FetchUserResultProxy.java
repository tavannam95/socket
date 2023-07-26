package com.a2m.mail.shared.domain;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

public interface FetchUserResultProxy {
    int getOffset();
    int getStart();
    List<JamesUserProxy> getUsers();
    int getRealCount();
}
