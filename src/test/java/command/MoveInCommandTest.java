package command;
import exceptions.FailureException;
import exceptions.HouseFulException;
import exceptions.MemberNotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import services.MoveInMoveOutService;


import java.util.*;

@ExtendWith(MockitoExtension.class)
public class MoveInCommandTest {

    @InjectMocks
    private MoveInCommand moveInCommand;

    @Mock
    private MoveInMoveOutService moveInMoveOutService;

    @Test
    public void executeTest(){
        try {
            Mockito.when(moveInMoveOutService.moveInUser("user1")).thenReturn("SUCCESS");
            List<String> tokens = new ArrayList<>(Arrays.asList("test","user1"));
            moveInCommand.execute(tokens);
        } catch (HouseFulException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void executeTest2(){
        try {
            Mockito.when(moveInMoveOutService.moveInUser("user1")).thenThrow(new HouseFulException("FULL"));
            List<String> tokens = new ArrayList<>(Arrays.asList("test","user1"));
            moveInCommand.execute(tokens);
        } catch (HouseFulException e) {
            Assert.assertSame("FULL",e.getMessage());
        }
    }
}
