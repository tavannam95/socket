package com.a2m.mail.shared.data;

import java.util.List;

import org.springframework.stereotype.Service;

import com.a2m.mail.shared.domain.FetchMessagesResult;
import com.a2m.mail.shared.domain.Message;

@Service
public class FetchMessagesResultImpl implements FetchMessagesResult {

    public FetchMessagesResultImpl() {
    }

    public FetchMessagesResultImpl(List<Message> messages, int start, int offset, int realCount, int realUnreadCount) {
        this.messages = messages;
        this.start = start;
        this.offset = offset;
        this.realCount = realCount;
        this.realUnreadCount = realUnreadCount;
    }

    private List<Message> messages;
    private int start;
    private int offset;
    private int realCount;
    private int realUnreadCount;

    public int getOffset() {
        return offset;
    }

    public int getStart() {
        return start;
    }

    public List<Message> getMessages() {
        return messages;
    }
    public int getRealCount() {
        return realCount;
    }

    public int getRealUnreadCount() {
        return realUnreadCount;
    }
}
