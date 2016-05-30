package ua.kiev.prog;


import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

public class UserListSingleton {
    private static final UserListSingleton USER_LIST_SINGLETON = new UserListSingleton();
    private final List<User> users = new ArrayList<>();

    static {
        UserListSingleton.getInstance().add(new User("System", "System"));
    }

    private UserListSingleton() {
    }

    public static UserListSingleton getInstance() {
        updateUserListByTime(30); //Time in minutes
        return USER_LIST_SINGLETON;
    }

    public synchronized void add(User user) {
        users.add(user);
    }

    public synchronized void remove(User user) {
        users.remove(user);
    }

    public User getUserByName(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public List<User> getUsers() {
        return users;
    }

    private static void updateUserListByTime(int minutes) {
        GregorianCalendar minTime = new GregorianCalendar();
        minTime.add(GregorianCalendar.MINUTE, -minutes);
        Iterator<User> it = USER_LIST_SINGLETON.getUsers().iterator();
        while (it.hasNext()) {
            User user = it.next();
            if ("System".equals(user.getName())){
                continue;
            }
            if (minTime.compareTo(user.getLastRequestTime()) > 0) {
                it.remove();
                Message systemMessage = new Message(
                        user.getName() + " has left chat room.",
                        new GregorianCalendar(),
                        UserListSingleton.getInstance().getUserByName("System"),
                        null,
                        user.getCurrentChatRoom());
                MessageListSingleton.getInstance().add(systemMessage);
            }
        }

    }
}
