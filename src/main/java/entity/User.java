package entity;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String name;
    private Map<User, Integer> expenseMap;

    public User( String name){
        this.name = name;
        this.expenseMap = new HashMap<>();
    }


    public String getName() {
        return name;
    }

    public Map<User, Integer> getExpenseMap() {
        return expenseMap;
    }
}
