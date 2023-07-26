
package com.a2m.mail.server.preferences;

import java.util.Arrays;
import java.util.List;

import com.a2m.mail.shared.rpc.ContactsResult.Contact;

/**
 *
 * Abstract class which defines storage operations related
 * with user preferences
 *
 */
public abstract class UserPreferencesStorage {

    protected static final String REGEX_OMITTED_EMAILS = "^.*(reply)[A-z0-9._%\\+\\-]*@.*$";

    /**
     * Add a new contact to the list.
     * The implementation has to check for duplicates
     */
    abstract public void addContact(Contact... c);

    /**
     * Add a new contact to the list.
     * The implementation has to check for duplicates
     */
    final public void addContact(String... mails) {
        if (mails != null) {
            addContact(Arrays.asList(mails));
        }
    }

    /**
     * Add a new contact to the list.
     * The implementation has to check for duplicates
     */
    final public void addContact(List<String> mails) {
        if (mails != null) {
            for (String mail: mails) {
                if (mail != null && !mail.matches(REGEX_OMITTED_EMAILS)) {
                    Contact contact = new Contact(mail);
                    if (!exists(contact)) {
                        addContact(contact);
                    }
                }
            }
        }
    }

    boolean exists (Contact mail) {
        for (Contact c : getContacts()) {
            if (c.mail.equals(mail.mail)) {
                if (c.realname == null || c.realname.isEmpty() || c.realname.equals(c.mail)) {
                    c.realname = mail.realname;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Get the list of contacts
     */
    abstract public Contact[] getContacts();

}
