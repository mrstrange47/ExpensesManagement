package command;


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
public class DuesCommandTest {

    @InjectMocks
    private DuesCommand duesCommand;

    @Mock
    private DuesService duesService;

    @Test
    public void executeTest(){
        try {
            Mockito.when(duesService.getDuesByUser("user1")).thenReturn(true);
            List<String> tokens = new ArrayList<>(Arrays.asList("test","user1"));
            duesCommand.execute(tokens);
        } catch (MemberNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void executeTest2(){
        try {
            Mockito.when(duesService.getDuesByUser("user1")).thenThrow(new MemberNotFoundException("NOT_FOUND"));
            List<String> tokens = new ArrayList<>(Arrays.asList("test","user1"));
            duesCommand.execute(tokens);
        } catch (MemberNotFoundException e) {
            Assert.assertSame("NOT_FOUND",e.getMessage());
        }

    }
}
