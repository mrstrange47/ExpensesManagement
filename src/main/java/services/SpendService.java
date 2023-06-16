package services;

import entity.SplitWise;
import entity.User;
import entity.UserExpense;
import exceptions.MemberNotFoundException;

import java.util.*;

public class SpendService {

    private final SplitWise splitWise;

    public SpendService(SplitWise splitWise) {
        this.splitWise = splitWise;
    }

    public String spend(String amount, String spentBy, List<String> members) throws MemberNotFoundException {
        if(!isMemberFound(this.splitWise.getUserMap(),spentBy)){
            throw new MemberNotFoundException("MEMBER_NOT_FOUND");
        }
        for(String member: members){
            if(!isMemberFound(this.splitWise.getUserMap(),member)){
                throw new MemberNotFoundException("MEMBER_NOT_FOUND");
            }
        }
        int memberCount = 1+members.size();
        int spendAmountRatio = 0;
        try{
            int parsedAmount = Integer.parseInt(amount);
            spendAmountRatio = parsedAmount / memberCount;
        }
        catch (Exception e){

        }

        return distributeLentAmount(spendAmountRatio,spentBy,members);
    }
    private String distributeLentAmount(int amount, String spendBy, List<String> members){
        User user = this.splitWise.getUserByName(spendBy);
        for(String member: members){
            User friend = this.splitWise.getUserByName(member);
            if(user.getExpenseMap().containsKey(friend)){
                int prevAmount = user.getExpenseMap().get(friend);
                user.getExpenseMap().put(friend,prevAmount+amount);
            }
            else{
                user.getExpenseMap().put(friend,amount);
            }
            distributeBorrowAmount(user,friend,amount);
        }
        simplifyAmount();
        return "SUCCESS";
    }

    private void distributeBorrowAmount(User spentBy, User spentTo, int amount){
        if(spentTo.getExpenseMap().containsKey(spentBy)){
            spentTo.getExpenseMap().put(spentBy,spentTo.getExpenseMap().get(spentBy)-amount);
        }
        else{
            spentTo.getExpenseMap().put(spentBy,-1*amount);
        }

    }

    private void simplifyAmount() {
        Map<User,Integer> map = new HashMap<>();
        for(User user: this.splitWise.getUserMap().values()){
            int count = 0;
            for(int val : user.getExpenseMap().values()){
                count += val;
            }
            map.put(user,count);
        }
        List<UserExpense> expenses = new ArrayList<>();
        simplifyAmountRecur(map, expenses);
        adjustAmount(expenses);
    }

    private void adjustAmount(List<UserExpense> expenses) {
        for(User user: this.splitWise.getUserMap().values()){
            for(User u: user.getExpenseMap().keySet()){
                user.getExpenseMap().put(u,0);
            }
        }
        for(UserExpense expense: expenses){
            if(expense.getSpendBy().getExpenseMap().containsKey(expense.getSpendTo())){
                expense.getSpendBy().getExpenseMap().put(expense.getSpendTo(),expense.getAmount()+expense.getSpendBy().getExpenseMap().get(expense.getSpendTo()));
            }
            else{
                expense.getSpendBy().getExpenseMap().put(expense.getSpendTo(),expense.getAmount());
            }

            if(expense.getSpendTo().getExpenseMap().containsKey(expense.getSpendBy())){
                expense.getSpendTo().getExpenseMap().put(expense.getSpendBy(),-1*expense.getAmount()+expense.getSpendTo().getExpenseMap().get(expense.getSpendBy()));
            }
            else{
                expense.getSpendTo().getExpenseMap().put(expense.getSpendBy(),-1*expense.getAmount());
            }

        }
    }

    private void simplifyAmountRecur(Map<User, Integer> map, List<UserExpense> expenses) {
        int maxValue =  Collections.max(map.values());
        int minValue =  Collections.min(map.values());
        if(maxValue != minValue){
            User maxUser = getKeyFromAmount(map,maxValue);
            User minUser = getKeyFromAmount(map,minValue);
            int sum = maxValue + minValue;
            if(sum >= 0){
                expenses.add(new UserExpense(maxUser,minUser,Math.abs(minValue)));
                map.remove(maxUser);
                map.remove(minUser);
                map.put(maxUser,sum);
                map.put(minUser,0);
            }
            else{
                expenses.add(new UserExpense(maxUser,minUser,Math.abs(maxValue)));
                map.remove(maxUser);
                map.remove(minUser);
                map.put(maxUser,0);
                map.put(minUser,sum);
            }
            simplifyAmountRecur(map,expenses);
        }
    }
    public static User getKeyFromAmount(Map <User, Integer> userMap, int value) {
        for (User user : userMap.keySet()) {
            if (userMap.get(user).equals(value)) {
                return user;
            }
        }
        return null;
    }
    private boolean isMemberFound(Map<String,User> userMap, String member){
        return userMap.containsKey(member);
    }
}
