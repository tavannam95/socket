package com.a2m.mail.shared.data;

import java.util.ArrayList;
import java.util.List;

import com.a2m.mail.shared.domain.MessageAttachment;
import com.a2m.mail.shared.domain.SmtpMessage;

public class SmtpMessageImpl extends AbstractMessageImpl implements SmtpMessage {
	private List<String> bcc;
    private String text;
    private List<MessageAttachmentImpl> messageAttachments;
    private List<MailHeaderImpl> mailHeaders;

    public String toString() {
        StringBuffer bccList = new StringBuffer("");
        if (bcc != null)
            for (String s : bcc)
                bccList.append(s).append(" ");

        StringBuffer attachNames = new StringBuffer("");
        for (MessageAttachment m : messageAttachments)
            attachNames.append(m.getName()).append(" ");

        return super.toString() + " Bcc='" + bccList.toString() + "'\nAttachments=" + attachNames.toString()
                + "'\nMessage:\n" + text + " MessageAttachmentImpl" + messageAttachments + " MailHeaderImpl" + mailHeaders;
    }

    public List<String> getBcc() {
        return bcc;
    }
    public void setBcc(List<String> bcc) {
        this.bcc = bcc;
    }

    /**
     * Set the body text of the content
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Return the body text of the content
     *
     * @return The text
     */
    public String getText() {
        return text;
    }

    /**
     * Set the attachments
     *
     * @param aList
     */
    public void setMessageAttachments(List<MessageAttachmentImpl> aList) {
        this.messageAttachments = aList;
    }

    /**
     * Return the attachments
     *
     * @return aList
     */
    public List<MessageAttachmentImpl> getMessageAttachments() {
        return messageAttachments;
    }

    @Override
    public List<MailHeaderImpl> getMailHeaders() {
        return mailHeaders;
    }

    @Override
    public void setMailHeaders(List<MailHeaderImpl> mailHeaders) {
        this.mailHeaders = mailHeaders == null ? new ArrayList<MailHeaderImpl>(): mailHeaders;
    }
}
