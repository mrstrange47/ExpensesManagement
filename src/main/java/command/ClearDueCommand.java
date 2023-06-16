package command;

import exceptions.IncorrentPaymentException;
import exceptions.MemberNotFoundException;
import services.DuesService;

import java.util.List;

public class ClearDueCommand implements ICommand{
    private final DuesService duesService;
    public ClearDueCommand(DuesService duesService) {
        this.duesService = duesService;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            System.out.println(this.duesService.clearDueByUser(tokens.get(1),tokens.get(2),Integer.parseInt(tokens.get(3))));
        } catch (MemberNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IncorrentPaymentException e) {
            System.out.println(e.getMessage());
        }
    }
}
