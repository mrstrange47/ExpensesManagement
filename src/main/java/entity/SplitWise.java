package entity;


import java.util.HashMap;

import java.util.Map;

public class SplitWise {
    Map<String, User> userMap;

    public SplitWise() {
        this.userMap = new HashMap<>();
    }

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public User getUserByName(String name){
        return this.userMap.get(name);
    }
}
