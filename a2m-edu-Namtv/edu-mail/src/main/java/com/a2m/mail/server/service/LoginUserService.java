package com.a2m.mail.server.service;

import com.a2m.mail.shared.domain.Settings;
import com.a2m.mail.shared.domain.User;

public interface LoginUserService {
    User login(String username, String password, Settings settings);
    Settings getSettings(String email);
}
