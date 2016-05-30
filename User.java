package ua.kiev.prog;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.GregorianCalendar;

@XmlRootElement(name = "user")
public class User {
    @XmlElement
    private String name;
    @XmlElement
    private String pass;
    private ChatRoom currentChatRoom;
    private UserStatus currentStatus;
    private GregorianCalendar lastRequestTime;

    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    private User() {
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public ChatRoom getCurrentChatRoom() {
        return currentChatRoom;
    }

    public UserStatus getCurrentStatus() {
        return currentStatus;
    }

    public GregorianCalendar getLastRequestTime() {
        return lastRequestTime;
    }

    @XmlElement
    @XmlJavaTypeAdapter(ChatRoomAdapter.class)
    public void setCurrentChatRoom(ChatRoom currentChatRoom) {
        this.currentChatRoom = currentChatRoom;
    }

    @XmlElement
    @XmlJavaTypeAdapter(UserStatusAdapter.class)
    public void setCurrentStatus(UserStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    @XmlElement
    public void setLastRequestTime(GregorianCalendar lastRequestTime) {
        this.lastRequestTime = lastRequestTime;
    }
}
