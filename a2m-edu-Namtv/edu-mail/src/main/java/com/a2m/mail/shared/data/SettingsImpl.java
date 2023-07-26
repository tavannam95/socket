package com.a2m.mail.shared.data;

import java.io.Serializable;

import com.a2m.mail.shared.domain.Settings;


public class SettingsImpl implements Settings, Serializable {

    private static final long serialVersionUID = -2239213237429390655L;

    public static final String DEFAULT_INBOX = "INBOX";
    public static final String DEFAULT_TRASH = "Trash";
    public static final String DEFAULT_SENT = "Sent";
    public static final String DEFAULT_DRAFT = "Draft";

    private String trashFolderName = DEFAULT_TRASH;
    private String sentFolderName = DEFAULT_SENT;
    private String inboxFolderName = DEFAULT_INBOX;
    private String draftsFolderName = DEFAULT_DRAFT;
    private String imapServer;
    private int imapPort;
    private boolean imapSecure;
    private String smptServer;
    private int smtpPort;
    private boolean smtpSecure;
    private boolean smtpAuth = true;

    private int prefetchCount = 20;

    public String toString() {
        String ret = "";
        ret += " smtp" +  (smtpSecure ? "s" : "") + "=" + smptServer + ":" + smtpPort + "(" + smtpAuth + ")";
        ret += "\n imap" +  (imapSecure ? "s" : "") + "=" + imapServer + ":" + imapPort;
        ret += "\n folders=" +  inboxFolderName + " " + sentFolderName + " " + trashFolderName + " " + draftsFolderName;
        return ret;
    }

    public String getInboxFolderName() {
        return inboxFolderName;
    }

    public void setInboxFolderName(String inboxFolderName) {
        this.inboxFolderName = inboxFolderName;
    }

    public String getTrashFolderName() {
        return trashFolderName;
    }

    public void setTrashFolderName(String trashFolderName) {
        this.trashFolderName = trashFolderName;
    }

    public String getSentFolderName() {
        return sentFolderName;
    }

    public void setSentFolderName(String sentFolderName) {
        this.sentFolderName = sentFolderName;
    }

    public int getPostFetchMessageCount() {
        return prefetchCount;
    }

    public void setPostFetchMessageCount(int prefetchCount) {
        this.prefetchCount  = prefetchCount;
    }

    public String getDraftsFolderName() {
        return draftsFolderName;
    }

    public void setDraftsFolderName(String draftFolderName) {
        this.draftsFolderName = draftFolderName;
    }
    public String getImapServer() {
        return imapServer;
    }

    public void setImapServer(String imapServer) {
        this.imapServer = imapServer;
    }

    public int getImapPort() {
        return imapPort;
    }

    public void setImapPort(int imapPort) {
        this.imapPort = imapPort;
    }

    public boolean getImapSecure() {
        return imapSecure;
    }

    public void setImapSecure(boolean imapSecure) {
        this.imapSecure = imapSecure;
    }

    public String getSmtpServer() {
        return smptServer;
    }

    public void setSmtpServer(String smptServer) {
        this.smptServer = smptServer;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    public boolean getSmtpSecure() {
        return smtpSecure;
    }

    public void setSmtpSecure(boolean smtpSecure) {
        this.smtpSecure = smtpSecure;
    }

    public boolean getSmtpAuth() {
        return smtpAuth;
    }

    public void setSmtpAuth(boolean smtpAuth) {
        this.smtpAuth = smtpAuth;
    }

}
