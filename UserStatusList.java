package ua.kiev.prog;
import java.util.ArrayList;
import java.util.List;


public class UserStatusList {
    private static final UserStatusList userStatusList = new UserStatusList();
    private final List<UserStatus> userStatuses = new ArrayList<>();

    static {
        userStatusList.add(new UserStatus("online"));
        userStatusList.add(new UserStatus("offline"));
        userStatusList.add(new UserStatus("away"));
        userStatusList.add(new UserStatus("brb"));
        userStatusList.add(new UserStatus("sleeping"));
        userStatusList.add(new UserStatus("dead"));
    }

    private UserStatusList() {
    }

    public static UserStatusList getInstance() {
        return userStatusList;
    }

    public synchronized void add(UserStatus userStatus) {
        userStatuses.add(userStatus);
    }

    public UserStatus getUserStatusByName(String name) {
        for (UserStatus userStatus : userStatuses) {
            if (name.equals(userStatus.getName())) {
                return userStatus;
            }
        }
        return null;
    }
}
