package ua.kiev.prog;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "chatroom")
@XmlJavaTypeAdapter(ChatRoomAdapter.class)
public class ChatRoom {
    @XmlElement
    private String name;
    private List<User> users = new ArrayList<>();

    public ChatRoom(String name) {
        this.name = name;
    }

    public ChatRoom() {
        this("");
    }

    public String getName() {
        return name;
    }

    public void add(User user) {
        users.add(user);
    }
}
