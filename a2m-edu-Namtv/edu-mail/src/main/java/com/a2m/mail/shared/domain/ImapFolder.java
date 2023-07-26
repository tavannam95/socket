
package com.a2m.mail.shared.domain;

import java.util.List;

import com.a2m.mail.shared.data.HasFullName;
import com.a2m.mail.shared.data.HasName;


public interface ImapFolder extends HasName, HasFullName {


    int getUnseenMessageCount();
    List<ImapFolder> getChildren();
    void setChildren(List<ImapFolder> children);
    void setUnseenMessageCount(int count);
    void setMessageCount(int realCount);
    int getMessageCount();
    String getDelimiter();
    void setDelimiter(String delimiter);
    void setSubscribed(boolean subscribed);
    boolean getSubscribed();

    boolean getHasChildren();
    void setHasChildren(boolean hasChildren);

    /**
     * use this to proxy the dumping method, or an alternative to clone, for the ValueProxy's must be set/get-ter
     * @param folder to be dumped
     */
    void setFolderTo(ImapFolder folder);
}
