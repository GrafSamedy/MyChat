package ua.kiev.prog;
import javax.xml.bind.annotation.adapters.XmlAdapter;


public class ChatRoomAdapter extends XmlAdapter<String,ChatRoom> {
    @Override
    public ChatRoom unmarshal(String v) throws Exception {
        if ("".equals(v)){
            v = "Main";
        }
        ChatRoom chatRoom = ChatRoomList.getInstance().getChatRoomByName(v);
        if (chatRoom == null) {
            ChatRoomList.getInstance().add(new ChatRoom(v));
        }
        return ChatRoomList.getInstance().getChatRoomByName(v);
    }

    @Override
    public String marshal(ChatRoom v) throws Exception {
        return v.getName();
    }
}
