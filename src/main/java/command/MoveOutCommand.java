package command;

import exceptions.FailureException;
import exceptions.MemberNotFoundException;
import services.MoveInMoveOutService;

import java.util.List;

public class MoveOutCommand implements ICommand{
    private final MoveInMoveOutService moveOutService;
    public MoveOutCommand(MoveInMoveOutService moveService) {
        this.moveOutService = moveService;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            System.out.println(this.moveOutService.moveOutUser(tokens.get(1)));
        } catch (MemberNotFoundException | FailureException e) {
            System.out.println(e.getMessage());
        }
    }
}
