package entity;

public class UserExpense {
    User spendBy;
    User spendTo;
    int amount;

    public UserExpense(User spendBy, User spendTo, int amount) {
        this.spendBy = spendBy;
        this.spendTo = spendTo;
        this.amount = amount;
    }

    public User getSpendBy() {
        return spendBy;
    }

    public User getSpendTo() {
        return spendTo;
    }

    public int getAmount() {
        return amount;
    }
}
