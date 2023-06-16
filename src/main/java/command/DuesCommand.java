package command;

import exceptions.MemberNotFoundException;
import services.DuesService;

import java.util.List;

public class DuesCommand implements ICommand{
    private final DuesService duesService;

    public DuesCommand(DuesService duesService) {
        this.duesService = duesService;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            this.duesService.getDuesByUser(tokens.get(1));
        } catch (MemberNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
