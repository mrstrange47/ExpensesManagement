package command;

import exceptions.MemberNotFoundException;
import services.SpendService;

import java.util.ArrayList;
import java.util.List;

public class SpendCommand implements ICommand{
    private final SpendService spendService;
    public SpendCommand(SpendService spendService) {
        this.spendService = spendService;
    }

    @Override
    public void execute(List<String> tokens) {
        List<String> members = new ArrayList<>();
        for(int i=3; i<tokens.size(); i++){
            members.add(tokens.get(i));
        }
        try {
            System.out.println(this.spendService.spend(tokens.get(1),tokens.get(2),members));
        } catch (MemberNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
