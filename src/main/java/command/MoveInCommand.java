package command;

import exceptions.HouseFulException;
import services.MoveInMoveOutService;

import java.util.List;

public class MoveInCommand implements ICommand{
    private final MoveInMoveOutService moveService;
    public MoveInCommand(MoveInMoveOutService moveService) {
        this.moveService = moveService;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            System.out.println(this.moveService.moveInUser(tokens.get(1)));
        } catch (HouseFulException e) {
            System.out.println(e.getMessage());
        }
    }
}
