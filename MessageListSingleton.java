package ua.kiev.prog;

import java.util.ArrayList;
import java.util.List;

public class MessageListSingleton {
    private static final MessageListSingleton MESSAGE_LIST_SINGLETON = new MessageListSingleton();
    private final List<Message> messages = new ArrayList<>();

    private MessageListSingleton() {
    }

    public static MessageListSingleton getInstance() {
        return MESSAGE_LIST_SINGLETON;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public synchronized void add(Message message) {
        messages.add(message);
    }

    public synchronized int getNextMessageId() {
        if (messages.size() == 0) {
            return 1;
        } else {
            return messages.stream().mapToInt(message -> message.getId()).max().getAsInt() + 1;
        }
    }
}
