package com.a2m.mail.server.utils;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;

import com.a2m.mail.server.FileItemRegistry;
import com.a2m.mail.shared.SConsts;

@Service
public class SessionUtils {
	
	private FileItemRegistry registry = new FileItemRegistry();
	
	public FileItemRegistry getSessionRegistry() {
        return registry;
    }

    /**
     * Remove session attributes, it has to be done in the login and logout actions
     * @param session
     */
    public static void cleanSessionAttributes(HttpSession session) {
        if (session != null) {
            @SuppressWarnings("rawtypes")
            Enumeration en = session.getAttributeNames();
            ArrayList<String> toRemove = new ArrayList<String>();
            while (en.hasMoreElements()) {
                String s = en.nextElement().toString();
                if (s.startsWith("hupa")) {
                    toRemove.add(s);
                }
            }
            for (String attr: toRemove) {
                session.removeAttribute(attr);
            }
        }
    }
}
