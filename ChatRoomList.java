package ua.kiev.prog;
import java.util.ArrayList;
import java.util.List;


public class ChatRoomList {
    private static final ChatRoomList chatRoomList = new ChatRoomList();
    private final List<ChatRoom> chatRooms = new ArrayList<>();

    static {
        chatRoomList.add(new ChatRoom("Main"));
    }

    private ChatRoomList() {
    }

    public static ChatRoomList getInstance() {
        return chatRoomList;
    }

    public synchronized void add(ChatRoom ChatRoom) {
        chatRooms.add(ChatRoom);
    }

    public ChatRoom getChatRoomByName(String name) {
        for (ChatRoom chatRoom : chatRooms) {
            if (name.equals(chatRoom.getName())) {
                return chatRoom;
            }
        }
        return null;
    }
}
