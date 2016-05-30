package ua.kiev.prog;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GetMessageServlet extends HttpServlet {
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

        Message message = ClientServerXMLParser.jaxbXMLReader(req.getInputStream(), Message.class);
        if (message == null) {
            resp.setStatus(400);
            return;
        }

        message.setFrom(user);
        message.setRoom(user.getCurrentChatRoom());
        message.setId();
        MessageListSingleton.getInstance().add(message);
        if (user == null || "".equals(user.getName()) || "".equals(user.getPass())) {
            resp.setStatus(400);
            return;
        }
    }
}
