package ua.kiev.prog;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddChatRoomServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String sessionUserName = (String) session.getAttribute("username");
        UserListSingleton userListSingleton = UserListSingleton.getInstance();
        User user = userListSingleton.getUserByName(sessionUserName);
        if (user == null) {
            resp.setStatus(401);
            return;
        }

        ChatRoom chatRoom = ClientServerXMLParser.jaxbXMLReader(req.getInputStream(), ChatRoom.class);
        if (chatRoom == null) {
            resp.setStatus(400);
            return;
        }
        ChatRoomList.getInstance().add(chatRoom);
        System.out.println(chatRoom.getName() + " added to chatroom list.");
    }
}
