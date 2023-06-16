package services;

import constants.ApplicationConstants;
import entity.SplitWise;
import entity.User;
import exceptions.FailureException;
import exceptions.HouseFulException;
import exceptions.MemberNotFoundException;

import java.util.Map;

public class MoveInMoveOutService {

    private final SplitWise splitWise;

    public MoveInMoveOutService(SplitWise splitWise){
        this.splitWise = splitWise;
    }

    public String moveInUser(String personName) throws HouseFulException {
        if(this.splitWise.getUserMap().size() >= ApplicationConstants.MAX_PEOPLE){
            throw new HouseFulException("HOUSEFUL");
        }
        User user = new User(personName);
        this.splitWise.getUserMap().put(personName,user);
        return "SUCCESS";
    }

    public String moveOutUser(String personName) throws MemberNotFoundException, FailureException {
        if(!this.splitWise.getUserMap().containsKey(personName)){
            throw new MemberNotFoundException("MEMBER_NOT_FOUND");
        }

        User user = this.splitWise.getUserByName(personName);
        for (Map.Entry<User, Integer> entry : user.getExpenseMap().entrySet()) {
            User key = entry.getKey();
            Integer value = entry.getValue();
            if (value != 0) {
                throw new FailureException("FAILURE");
            }
        }
        user.getExpenseMap().forEach((k,v) ->{
            if(k.getExpenseMap().containsKey(user)){
                k.getExpenseMap().remove(user);
            }
        });
        this.splitWise.getUserMap().remove(personName);
        return "SUCCESS";
    }
}
