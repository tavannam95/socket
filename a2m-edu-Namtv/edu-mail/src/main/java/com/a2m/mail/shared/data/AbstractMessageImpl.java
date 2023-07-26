package com.a2m.mail.shared.data;

import java.util.List;

public class AbstractMessageImpl{
    private String from;
    private String subject;
    private String replyto;
    private List<String> to;
    private List<String> cc;
    private boolean hasAttachment;

    public String toString() {
        StringBuffer toList = new StringBuffer("");
        if (to != null)
            for (String s: to)
                toList.append(s).append(" ");

        StringBuffer ccList = new StringBuffer("");
        if (cc != null)
            for (String s: cc)
                ccList.append(s).append(" ");

        return "From='" + from
             + "' To='" + toList.toString()
             + "' CC='" + ccList.toString()
             + "' ReplyTo='" + (replyto == null ? "": replyto)
             + "' Subject='" + subject
             + "' Attachments=" + hasAttachment;
    }


    public boolean getHasAttachment() {
        return hasAttachment;
    }

    public void setHasAttachments(boolean hasAttachments) {
        this.hasAttachment = hasAttachments;
    }

    /**
     * Set the From: header field
     *
     * @param from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Return the From: header field
     *
     * @return from
     */
    public String getFrom() {
        return from;
    }


    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    public List<String> getCc() {
        return cc;
    }

    /**
     * Set the Subject: header field
     *
     * @param subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Return the Subject: header field
     *
     * @return subject
     */
    public String getSubject() {
        return subject;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public String getReplyto() {
        return replyto;
    }

    public void setReplyto(String replyto) {
        this.replyto = replyto;
    }


}
