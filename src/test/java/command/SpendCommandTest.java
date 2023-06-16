package command;


import exceptions.MemberNotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import services.SpendService;


import java.util.*;

@ExtendWith(MockitoExtension.class)
public class SpendCommandTest {

    @InjectMocks
    private SpendCommand spendCommand;

    @Mock
    private SpendService spendService;

    @Test
    public void executeTest(){
        try {
            List<String> members = new ArrayList<>(Arrays.asList("user2","user3"));
            Mockito.when(spendService.spend("50","user1",members)).thenReturn("SUCCESS");
            List<String> tokens = new ArrayList<>(Arrays.asList("test","50","user1","user2","user3"));
            spendCommand.execute(tokens);
        } catch (MemberNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void executeTest2(){
        try {
            List<String> members = new ArrayList<>(Arrays.asList("user2","user3"));
            Mockito.when(spendService.spend("50","user1",members)).thenThrow(new MemberNotFoundException("NOT_FOUND"));
            List<String> tokens = new ArrayList<>(Arrays.asList("test","50","user1","user2","user3"));
            spendCommand.execute(tokens);
        } catch (MemberNotFoundException e) {
            Assert.assertSame("NOT_FOUND",e.getMessage());
        }
    }
}
