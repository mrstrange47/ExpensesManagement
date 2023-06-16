package config;

import command.*;
import constants.ApplicationConstants;
import entity.SplitWise;
import services.DuesService;
import services.MoveInMoveOutService;
import services.SpendService;

public class ApplicationConfig {
    private final CommandInvoker commandInvoker = new CommandInvoker();
    private final SplitWise splitWise = new SplitWise();
    private final DuesService duesService = new DuesService(splitWise);
    private final MoveInMoveOutService moveService = new MoveInMoveOutService(splitWise);
    private final SpendService spendService = new SpendService(splitWise);
    private final MoveInCommand moveInCommand = new MoveInCommand(moveService);
    private final MoveOutCommand moveOutCommand = new MoveOutCommand(moveService);
    private final DuesCommand duesCommand = new DuesCommand(duesService);
    private final ClearDueCommand clearDueCommand = new ClearDueCommand(duesService);
    private final SpendCommand spendCommand = new SpendCommand(spendService);
    public CommandInvoker getCommandInvoker(){
        commandInvoker.register(ApplicationConstants.MOVE_IN,moveInCommand);
        commandInvoker.register(ApplicationConstants.SPEND,spendCommand);
        commandInvoker.register(ApplicationConstants.DUES,duesCommand);
        commandInvoker.register(ApplicationConstants.CLEAR_DUE,clearDueCommand);
        commandInvoker.register(ApplicationConstants.MOVE_OUT,moveOutCommand);
        return commandInvoker;
    }
}
