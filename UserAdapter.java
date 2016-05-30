package ua.kiev.prog;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class UserAdapter extends XmlAdapter<String, User> {
    @Override
    public User unmarshal(String v) throws Exception {
        return UserListSingleton.getInstance().getUserByName(v);
    }

    @Override
    public String marshal(User v) throws Exception {
        return v.getName();
    }
}
