package ua.kiev.prog;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.GregorianCalendar;

public class SendMessageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String sessionUserName = (String) session.getAttribute("username");
        UserListSingleton userListSingleton = UserListSingleton.getInstance();
        User user = userListSingleton.getUserByName(sessionUserName);
        if (user == null) {
            resp.setStatus(401);
            return;
        }

        MessageList messageList = getMessagesToSend(user);
        ClientServerXMLParser.jaxbXMLWriter(resp.getOutputStream(), messageList);
        resp.getOutputStream().flush();
        user.setLastRequestTime(new GregorianCalendar());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("SendMessage Servlet POST received");
    }

    private MessageList getMessagesToSend(User user) {
        GregorianCalendar minTime = user.getLastRequestTime();
        MessageList messageList = new MessageList();
        for (Message message : MessageListSingleton.getInstance().getMessages()) {
            //Skipping old messages
            if (message.getTime().compareTo(minTime) < 0) {
                continue;
            }
            //Skipping private messages not for current user
            if (message.getTo() != null && !user.equals(message.getTo())) {
                continue;
            }
            //Skipping messages from other chatrooms
            if (!message.getRoom().equals(user.getCurrentChatRoom())) {
                continue;
            }
            messageList.add(message);
        }
        return messageList;
    }
}
