package services;
import entity.SplitWise;
import entity.User;
import exceptions.IncorrentPaymentException;
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
public class DuesServiceTest {

    @InjectMocks
    private DuesService duesService;

    @Mock
    private SplitWise splitWise;

    @Test
    public void clearDueByUserTest(){
        Mockito.when(splitWise.getUserMap()).thenReturn(new HashMap<>());
        try {
            int result = duesService.clearDueByUser("test_user1","test_user2",50);
        } catch (MemberNotFoundException e) {
            Assert.assertSame(e.getMessage(), "MEMBER_NOT_FOUND");
        } catch (IncorrentPaymentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clearDueByUserTest2(){
        Map<String, User> map = new HashMap<>();
        map.put("test_user1", new User("test_user1"));
        Mockito.when(splitWise.getUserMap()).thenReturn(map);
        try {
            int result = duesService.clearDueByUser("test_user1","test_user2",50);
        } catch (MemberNotFoundException e) {
            Assert.assertSame(e.getMessage(), "MEMBER_NOT_FOUND");
        } catch (IncorrentPaymentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clearDueByUserTest3(){
        Map<String, User> map = new HashMap<>();
        User user = new User("test_user1");
        user.getExpenseMap().put(new User("test_user2"), 50);
        map.put("test_user1", user);
        User user1 = new User("test_user2");
        map.put("test_user2", user1);
        Mockito.when(splitWise.getUserMap()).thenReturn(map);
        Mockito.when(splitWise.getUserByName("test_user1")).thenReturn(user);
        Mockito.when(splitWise.getUserByName("test_user2")).thenReturn(user1);
        try {
            int result = duesService.clearDueByUser("test_user1","test_user2",50);
        } catch (MemberNotFoundException e) {
            Assert.assertSame(e.getMessage(), "MEMBER_NOT_FOUND");
        } catch (IncorrentPaymentException e) {
            Assert.assertSame(e.getMessage(), "INCORRECT_PAYMENT");
        }
    }

    @Test
    public void clearDueByUserTest4(){
        Map<String, User> map = new HashMap<>();
        User user = new User("test_user1");
        User user1 = new User("test_user2");
        map.put("test_user2", user1);
        user1.getExpenseMap().put(user,50);
        user.getExpenseMap().put(user1, -50);
        map.put("test_user1", user);

        Mockito.when(splitWise.getUserMap()).thenReturn(map);
        Mockito.when(splitWise.getUserByName("test_user1")).thenReturn(user);
        Mockito.when(splitWise.getUserByName("test_user2")).thenReturn(user1);
        try {
            int result = duesService.clearDueByUser("test_user1","test_user2",50);
            Assert.assertEquals(result,0);
        } catch (MemberNotFoundException e) {
            Assert.assertSame(e.getMessage(), "MEMBER_NOT_FOUND");
        } catch (IncorrentPaymentException e) {
            Assert.assertSame(e.getMessage(), "INCORRECT_PAYMENT");
        }
    }

    @Test
    public void clearDueByUserTest5(){
        Map<String, User> map = new HashMap<>();
        User user = new User("test_user1");
        User user1 = new User("test_user2");
        map.put("test_user2", user1);
        user1.getExpenseMap().put(user,-50);
        user.getExpenseMap().put(user1, 50);
        map.put("test_user1", user);

        Mockito.when(splitWise.getUserMap()).thenReturn(map);
        Mockito.when(splitWise.getUserByName("test_user1")).thenReturn(user);
        Mockito.when(splitWise.getUserByName("test_user2")).thenReturn(user1);
        try {
            int result = duesService.clearDueByUser("test_user1","test_user2",50);
            Assert.assertEquals(result,0);
        } catch (MemberNotFoundException e) {
            Assert.assertSame(e.getMessage(), "MEMBER_NOT_FOUND");
        } catch (IncorrentPaymentException e) {
            Assert.assertSame(e.getMessage(), "INCORRECT_PAYMENT");
        }
    }

    @Test
    public void getDuesByUserTest(){
        Mockito.when(splitWise.getUserMap()).thenReturn(new HashMap<>());
        try {
            duesService.getDuesByUser("test_user1");
        } catch (MemberNotFoundException e) {
            Assert.assertSame(e.getMessage(), "MEMBER_NOT_FOUND");
        }
    }

    @Test
    public void getDuesByUserTest2(){
        Map<String, User> map = new HashMap<>();
        User user = new User("test_user1");
        User user1 = new User("test_user2");
        User user2 = new User("test_user3");
        map.put("test_user2", user1);
        user1.getExpenseMap().put(user,50);
        user.getExpenseMap().put(user1, -50);
        user.getExpenseMap().put(user2, 20);
        map.put("test_user1", user);

        Mockito.lenient().when(splitWise.getUserMap()).thenReturn(map);
        Mockito.lenient().when(splitWise.getUserByName("test_user1")).thenReturn(user);
        Mockito.lenient().when(splitWise.getUserByName("test_user2")).thenReturn(user1);
        try {
            boolean result = duesService.getDuesByUser("test_user1");
            Assert.assertEquals(result,true);
        } catch (MemberNotFoundException e) {
            Assert.assertSame(e.getMessage(), "MEMBER_NOT_FOUND");
        }
    }
}
