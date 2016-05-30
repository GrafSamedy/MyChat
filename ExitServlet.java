package ua.kiev.prog;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.GregorianCalendar;


public class ExitServlet extends HttpServlet {
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

        UserListSingleton.getInstance().remove(user);
        Message systemMessage = new Message(
                user.getName() + " has left chat room.",
                new GregorianCalendar(),
                UserListSingleton.getInstance().getUserByName("System"),
                null,
                user.getCurrentChatRoom());
        MessageListSingleton.getInstance().add(systemMessage);
    }
}
