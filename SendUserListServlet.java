package ua.kiev.prog;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SendUserListServlet extends HttpServlet {
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

        UserList userList = getUserList(user);
        ClientServerXMLParser.jaxbXMLWriter(resp.getOutputStream(), userList);
        resp.getOutputStream().flush();
    }

    private UserList getUserList(User user){
        UserList userList = new UserList();
        UserListSingleton.getInstance().getUsers().stream().filter(userListItem -> user.getCurrentChatRoom().equals(userListItem.getCurrentChatRoom())).forEach(userList::add);
        return userList;
    }
}
