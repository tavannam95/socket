package com.a2m.mail.shared.rpc;

import java.io.Serializable;

public class ContactsResult{

    public static class Contact implements Serializable{
        private static final long serialVersionUID = -8632580327693416473L;
        public String mail;
        public String realname;

        public Contact() {
        }

        public Contact(String address) {
            mail = address.replaceAll("^.*<([^>]+)>\\s*$", "$1");

            realname = mail.equals(address) ? mail : address
                    // remove the email part
                    .replaceAll("<[^<>]+>\\s*$", "")
                    // remove start symbols in the name
                    .replaceAll("^[\\s\"'<]+", "")
                    // remove end symbols in the name
                    .replaceAll("[\\s\"'>]+$", "")
                    ;

            if (realname.isEmpty())
                realname = mail;
        }

        public Contact(String realname, String mail) {
            this.realname = realname;
            this.mail = mail;
        }

        public String toString() {
            return realname != null && !realname.isEmpty() ? realname : mail;
        }

        public String toKey() {
            return toString().replaceAll("[^\\w\\d<@>]+", "").toLowerCase();
        }

        public String getName() {
            return realname;
        }

        public String toIsoAddress() {
            if (!mail.equals(realname))
                return realname + " <" + mail + ">";
            else
                return mail;
        }
    }

    private Contact[] contacts;

    public ContactsResult() {
    }

    public ContactsResult(Contact... contacts) {
        this.contacts = contacts;
    }

    public Contact[] getContacts() {
        return contacts;
    }

    public void setContacts(Contact[] contacts) {
        this.contacts = contacts;
    }

}
