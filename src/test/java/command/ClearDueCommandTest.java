package command;

import exceptions.IncorrentPaymentException;
import exceptions.MemberNotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import services.DuesService;


import java.util.*;

@ExtendWith(MockitoExtension.class)
public class ClearDueCommandTest {

    @InjectMocks
    private ClearDueCommand clearDueCommand;

    @Mock
    private DuesService duesService;

    @Test
    public void executeTest(){
        try {
            Mockito.when(duesService.clearDueByUser("user1","user2",50)).thenReturn(1);
            List<String> tokens = new ArrayList<>(Arrays.asList("test","user1","user2","50"));
            clearDueCommand.execute(tokens);
        } catch (MemberNotFoundException e) {
            e.printStackTrace();
        } catch (IncorrentPaymentException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void executeTest2(){
        try {
            Mockito.when(duesService.clearDueByUser("user1","user2",50)).thenThrow(new MemberNotFoundException("NOT_FOUND"));
            List<String> tokens = new ArrayList<>(Arrays.asList("test","user1","user2","50"));
            clearDueCommand.execute(tokens);
        } catch (MemberNotFoundException e) {
            Assert.assertSame(e.getMessage(),"NOT_FOUND");
        } catch (IncorrentPaymentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void executeTest3(){
        try {
            Mockito.when(duesService.clearDueByUser("user1","user2",50)).thenThrow(new IncorrentPaymentException("PAYMENT_FAILED"));
            List<String> tokens = new ArrayList<>(Arrays.asList("test","user1","user2","50"));
            clearDueCommand.execute(tokens);
        } catch (MemberNotFoundException e) {
            e.printStackTrace();
        } catch (IncorrentPaymentException e) {
            Assert.assertSame(e.getMessage(),"PAYMENT_FAILED");
        }
    }

}
