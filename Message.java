package ua.kiev.prog;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.GregorianCalendar;

@XmlRootElement
public class Message {
    private int id;
    @XmlElement
    private String text;
    @XmlElement
    private GregorianCalendar time;
    private User from;
    private User to;
    private ChatRoom room;

    public Message(String text, GregorianCalendar time, User from, User to, ChatRoom room) {
        setId();
        this.text = text;
        this.time = time;
        this.from = from;
        this.to = to;
        this.room = room;
    }

    private Message() {

    }

    public int getId() {
        return id;
    }

    public GregorianCalendar getTime() {
        return time;
    }

    public User getFrom() {
        return from;
    }

    public User getTo() {
        return to;
    }

    public ChatRoom getRoom() {
        return room;
    }

    @XmlElement
    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    @XmlJavaTypeAdapter(UserAdapter.class)
    public void setFrom(User from) {
        this.from = from;
    }

    @XmlElement
    @XmlJavaTypeAdapter(UserAdapter.class)
    public void setTo(User to) {
        this.to = to;
    }

    @XmlElement
    @XmlJavaTypeAdapter(ChatRoomAdapter.class)
    public void setRoom(ChatRoom room) {
        this.room = room;
    }

    public void setId() {
        this.setId(MessageListSingleton.getInstance().getNextMessageId());
    }
}
