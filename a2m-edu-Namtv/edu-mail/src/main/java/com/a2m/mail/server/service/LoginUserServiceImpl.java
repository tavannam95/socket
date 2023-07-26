package com.a2m.mail.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a2m.mail.shared.data.UserImpl;
import com.a2m.mail.shared.domain.Settings;
import com.a2m.mail.shared.domain.User;
import com.sun.mail.imap.IMAPStore;

@Service
public class LoginUserServiceImpl implements LoginUserService {
	
	@Autowired
	IMAPStoreCache imapStore;
	
	@Autowired
	SessionService session;
	
	@Override
	public User login(String username, String password, Settings settings) {
		
		try {
			User user = new UserImpl();
            user.setName(username);
            user.setPassword(password);
            user.setSettings(settings);

            IMAPStore imap = imapStore.get(user);
            user.setAuthenticated(true);
            session.getCache().put(user.getName(), user);

            System.out.println("Logged user: " + username);
            
            return user;
		} catch (Exception e) {
			e.printStackTrace();
            throw new RuntimeException(e);
		}
	}

	@Override
	public Settings getSettings(String email) {

		return null;
	}

//    private Settings fix(Settings a) {
//        if (settingsProvider != null) {
//            Settings b = settingsProvider.get();
//            if (a == null) {
//                return b;
//            }
//            a.setImapServer(or(a.getImapServer(), b.getImapServer()));
//            a.setImapPort(or(a.getImapPort(), b.getImapPort()));
//            a.setSmtpServer((or(a.getSmtpServer(), b.getSmtpServer())));
//            a.setSmtpPort(or(a.getSmtpPort(), b.getSmtpPort()));
//
//            a.setInboxFolderName(or(a.getInboxFolderName(), b.getInboxFolderName()));
//            a.setSentFolderName(or(a.getSentFolderName(), b.getSentFolderName()));
//            a.setTrashFolderName(or(a.getTrashFolderName(), b.getTrashFolderName()));
//            a.setDraftsFolderName(or(a.getDraftsFolderName(), b.getDraftsFolderName()));
//        }
//        return a;
//    }
//
//    private <T> T or (T a, T b) {
//        return a == null ? b : a;
//    }
//
//    @Override
//    public Settings getSettings(String email) {
//        if (settingsDiscoverer == null) {
//            settingsDiscoverer = new SettingsDiscoverer();
//        }
//        return settingsDiscoverer.discoverSettings(email);
//    }

}
