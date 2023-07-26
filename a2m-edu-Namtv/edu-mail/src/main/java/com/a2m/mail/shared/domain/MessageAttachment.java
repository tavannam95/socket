package com.a2m.mail.shared.domain;

public interface MessageAttachment {

    String getName();

    void setName(String decodeText);

    void setContentType(String contentType);

    void setSize(int size);

    int getSize();

    boolean isImage();

    void setImage(boolean image);

    String getContentType();
    
    long getSizeAttachment();
    
    void setSizeAttachment(long sizeAttachment);

}
