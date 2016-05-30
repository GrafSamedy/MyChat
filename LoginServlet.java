package ua.kiev.prog;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.GregorianCalendar;


public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = ClientServerXMLParser.jaxbXMLReader(req.getInputStream(), User.class);
        if (user == null || "".equals(user.getName()) || "".equals(user.getPass())) {
            resp.setStatus(400);
            return;
        }
        String userName = user.getName();
        HttpSession session = req.getSession(true);
        String sessionUserName = (String) session.getAttribute("username");
        UserListSingleton userListSingleton = UserListSingleton.getInstance();
        User userInList = userListSingleton.getUserByName(userName);
        if (userInList != null) {
            if (user.getPass().equals(userInList.getPass())) { //correct name + password
                if (user.getCurrentStatus() != null) {
                    userInList.setCurrentStatus(user.getCurrentStatus());
                }
                if (user.getCurrentChatRoom() != null) {
                    userInList.setCurrentChatRoom(user.getCurrentChatRoom());
                }
                if (!userName.equals(sessionUserName)) {
                    session.invalidate();
                    session = req.getSession();
                    session.setAttribute("username", userName);
                }
                //Update user information on client side
                ClientServerXMLParser.jaxbXMLWriter(resp.getOutputStream(), user);
                resp.getOutputStream().flush();
            } else { //wrong password
                session.invalidate();
                resp.setStatus(401);
            }
        } else { //new user
            if (user.getCurrentChatRoom() == null) {
                user.setCurrentChatRoom(ChatRoomList.getInstance().getChatRoomByName("Main"));
            }
            if (user.getCurrentStatus() == null) {
                user.setCurrentStatus(UserStatusList.getInstance().getUserStatusByName("online"));
            }
            userListSingleton.add(user);
            user.getCurrentChatRoom().add(user);
            Message systemMessage = new Message(
                    user.getName() + " has joined chat room.",
                    new GregorianCalendar(),
                    UserListSingleton.getInstance().getUserByName("System"),
                    null,
                    user.getCurrentChatRoom());

            MessageListSingleton.getInstance().add(systemMessage);
            session.invalidate();
            session = req.getSession();
            session.setAttribute("username", userName);
            //Update user information on client side
            ClientServerXMLParser.jaxbXMLWriter(resp.getOutputStream(), user);
            resp.getOutputStream().flush();
        }
    }
}
