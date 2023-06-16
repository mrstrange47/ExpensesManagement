package command;

import exceptions.FailureException;

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
public class MoveOutCommandTest {

    @InjectMocks
    private MoveOutCommand moveOutCommand;

    @Mock
    private MoveInMoveOutService moveOutService;

    @Test
    public void executeTest(){
        try {
            Mockito.when(moveOutService.moveOutUser("user1")).thenReturn("SUCCESS");
            List<String> tokens = new ArrayList<>(Arrays.asList("test","user1"));
            moveOutCommand.execute(tokens);
        } catch (MemberNotFoundException e) {
            e.printStackTrace();
        } catch (FailureException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void executeTest2(){
        try {
            Mockito.when(moveOutService.moveOutUser("user1")).thenThrow(new MemberNotFoundException("NOT_FOUND"));
            List<String> tokens = new ArrayList<>(Arrays.asList("test","user1"));
            moveOutCommand.execute(tokens);
        } catch (MemberNotFoundException e) {
            Assert.assertSame("NOT_FOUND",e.getMessage());
        } catch (FailureException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void executeTest3(){
        try {
            Mockito.when(moveOutService.moveOutUser("user1")).thenThrow(new FailureException("FAILED"));
            List<String> tokens = new ArrayList<>(Arrays.asList("test","user1"));
            moveOutCommand.execute(tokens);
        } catch (MemberNotFoundException e) {
            Assert.assertSame("NOT_FOUND",e.getMessage());
        } catch (FailureException e) {
            Assert.assertSame("FAILED",e.getMessage());
        }

    }
}
