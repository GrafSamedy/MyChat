package ua.kiev.prog;
import javax.xml.bind.annotation.adapters.XmlAdapter;


public class UserStatusAdapter extends XmlAdapter<String, UserStatus> {
    @Override
    public UserStatus unmarshal(String v) throws Exception {
        if ("".equals(v)) {
            v = "online";
        }
        UserStatus userStatus = UserStatusList.getInstance().getUserStatusByName(v);
        if (userStatus == null) {
            UserStatusList.getInstance().add(userStatus);
        }
        return UserStatusList.getInstance().getUserStatusByName(v);
    }

    @Override
    public String marshal(UserStatus v) throws Exception {
        return v.getName();
    }
}
