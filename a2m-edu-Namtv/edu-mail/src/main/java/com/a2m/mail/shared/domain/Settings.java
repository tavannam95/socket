package com.a2m.mail.shared.domain;

public interface Settings {

    void setInboxFolderName(String inboxFolder);
    void setSentFolderName(String sentFolder);
    void setTrashFolderName(String trashFolder);
    void setDraftsFolderName(String draftFolder);
    void setPostFetchMessageCount(int postCount);
    String getTrashFolderName();
    String getInboxFolderName();
    String getSentFolderName();
    String getDraftsFolderName();

    String getImapServer();
    void setImapServer(String imapServer);
    int getImapPort();
    void setImapPort(int imapPort);
    boolean getImapSecure();
    void setImapSecure(boolean imapSecure);
    String getSmtpServer();
    void setSmtpServer(String smptServer);
    int getSmtpPort();
    void setSmtpPort(int smtpPort);
    boolean getSmtpSecure();
    void setSmtpSecure(boolean smtpSecure);
    boolean getSmtpAuth();
    void setSmtpAuth(boolean smtpAuth);
}
