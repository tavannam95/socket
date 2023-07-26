package com.a2m.mail.shared.data;

import com.a2m.mail.shared.domain.MessageAttachment;

public class MessageAttachmentImpl implements MessageAttachment {

    private String contentType;
    private int size;
    private String name;
    private long sizeAttachment;

    /**
     * Set the name of the attachment
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;

    }

    /**
     * Return the name of the attachment
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the content-type of the attachment
     *
     * @param contentType
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Return the content-type of the attachment
     *
     * @return cType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Return whether the attachment is an image
     *
     * @return cType
     */
    public boolean isImage() {
          return contentType != null && contentType.toLowerCase().startsWith("image/");
    }

    /**
     * Set the content size in bytes
     *
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Return the content size in bytes
     *
     * @return size
     */
    public int getSize() {
        return size;
    }

    @Override
    public void setImage(boolean image) {
        //FIXME just for MessageSendActivity's NullPointerException, with adding the RequestContext's create List<MeeageAttachment>
    }

	@Override
	public String toString() {
		return "MessageAttachmentImpl [contentType=" + contentType + ", size=" + size + ", name=" + name + "]";
	}

	public long getSizeAttachment() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setSizeAttachment(long sizeAttachment) {
		// TODO Auto-generated method stub
		
	}
    
}
