package services;

import entity.SplitWise;
import entity.User;
import entity.UserExpense;
import exceptions.IncorrentPaymentException;
import exceptions.MemberNotFoundException;

import java.util.*;

public class DuesService {
    private final SplitWise splitWise;

    public DuesService(SplitWise splitWise){
        this.splitWise = splitWise;
    }

    public boolean getDuesByUser(String name) throws MemberNotFoundException {
        if(!this.splitWise.getUserMap().containsKey(name)){
            throw new MemberNotFoundException("MEMBER_NOT_FOUND");
        }

        User user = this.splitWise.getUserByName(name);

        List<UserExpense> expenses = new ArrayList<>();
        user.getExpenseMap().forEach((key,value) ->{
            if(value <= 0){
               expenses.add(new UserExpense(user,key,Math.abs(value)));
            }
            else{
                expenses.add(new UserExpense(user,key,0));
            }

        });

        Collections.sort(expenses, new Comp());
        for(UserExpense expense: expenses){
            System.out.println(expense.getSpendTo().getName() +" "+ expense.getAmount());
        }
        return true;
    }
   static class Comp implements Comparator<UserExpense>{
        public int compare(UserExpense user1, UserExpense user2){
            if(user1.getAmount() == user2.getAmount()){
                return user1.getSpendTo().getName().compareTo(user2.getSpendTo().getName());
            }
            else{
                if(user1.getAmount() > user2.getAmount())return -1;
                else return 1;
            }
        }
   }
    public int clearDueByUser(String oweName, String lentName, int amount) throws MemberNotFoundException, IncorrentPaymentException {
        if(!this.splitWise.getUserMap().containsKey(oweName)){
            throw new MemberNotFoundException("MEMBER_NOT_FOUND");
        }

        if(!this.splitWise.getUserMap().containsKey(lentName)){
            throw new MemberNotFoundException("MEMBER_NOT_FOUND");
        }

        User oweUser = this.splitWise.getUserByName(oweName);
        User lentUser = this.splitWise.getUserByName(lentName);
        if(!oweUser.getExpenseMap().containsKey(lentUser)){
            throw new MemberNotFoundException("MEMBER_NOT_FOUND");
        }
        if(oweUser.getExpenseMap().get(lentUser) > 0 || Math.abs(oweUser.getExpenseMap().get(lentUser)) < amount){
            throw new IncorrentPaymentException("INCORRECT_PAYMENT");
        }
        return rebalanceAmount(oweUser,lentUser,amount);
    }

    private int rebalanceAmount(User oweUser, User lentUser, int amount){
        oweUser.getExpenseMap().put(lentUser,oweUser.getExpenseMap().get(lentUser) + amount);
        lentUser.getExpenseMap().put(oweUser,lentUser.getExpenseMap().get(oweUser) - amount);
        return Math.abs(oweUser.getExpenseMap().get(lentUser));
    }
}
