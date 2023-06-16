package services;


import entity.SplitWise;
import entity.User;
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


import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class MoveInMoveOutServiceTest {

    @InjectMocks
    MoveInMoveOutService moveOutService;

    @Mock
    private SplitWise splitWise;

    @Test
    public void moveInUserTest() {
        Mockito.when(splitWise.getUserMap()).thenReturn(new HashMap<>());
        try {
            String result = moveOutService.moveInUser("test-user");
            Assert.assertSame(result, "SUCCESS");
        } catch (HouseFulException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void moveInUserTest2() {
        Map<String, User> map = new HashMap<>();
        map.put("test_user1", new User("test_user1"));
        map.put("test_user2", new User("test_user2"));
        map.put("test_user3", new User("test_user3"));
        Mockito.when(splitWise.getUserMap()).thenReturn(map);
        try {
            String result = moveOutService.moveInUser("test-user");
            Assert.assertSame(result, "SUCCESS");
        } catch (HouseFulException e) {
            Assert.assertSame(e.getMessage(), "HOUSEFUL");
        }
    }

    @Test
    public void moveOutUserTest() {
        Map<String, User> map = new HashMap<>();
        map.put("test_user1", new User("test_user1"));
        Mockito.when(splitWise.getUserMap()).thenReturn(map);
        try {
            String result = moveOutService.moveOutUser("test-user");
        } catch (MemberNotFoundException e) {
            Assert.assertSame(e.getMessage(), "MEMBER_NOT_FOUND");
        } catch (FailureException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void moveOutUserTest2() {
        Map<String, User> map = new HashMap<>();
        User user = new User("test_user1");
        user.getExpenseMap().put(new User("test_user2"), 50);
        map.put("test_user1", user);

        Mockito.when(splitWise.getUserMap()).thenReturn(map);
        Mockito.when(splitWise.getUserByName("test_user1")).thenReturn(user);
        try {
            String result = moveOutService.moveOutUser("test_user1");
        } catch (MemberNotFoundException e) {
            Assert.assertSame(e.getMessage(), "MEMBER_NOT_FOUND");
        } catch (FailureException e) {
            Assert.assertSame(e.getMessage(), "FAILURE");
        }
    }

    @Test
    public void moveOutUserTest3() {
        Map<String, User> map = new HashMap<>();
        User user = new User("test_user1");
        user.getExpenseMap().put(new User("test_user2"), 0);
        map.put("test_user1", user);

        Mockito.when(splitWise.getUserMap()).thenReturn(map);
        Mockito.when(splitWise.getUserByName("test_user1")).thenReturn(user);
        try {
            String result = moveOutService.moveOutUser("test_user1");
            Assert.assertSame(result, "SUCCESS");
        } catch (MemberNotFoundException e) {
            Assert.assertSame(e.getMessage(), "MEMBER_NOT_FOUND");
        } catch (FailureException e) {
            Assert.assertSame(e.getMessage(), "FAILURE");
        }

    }
}