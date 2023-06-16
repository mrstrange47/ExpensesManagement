package services;
import  entity.SplitWise;
import entity.User;
import exceptions.MemberNotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.*;

@ExtendWith(MockitoExtension.class)
public class SpendServiceTest {
    @InjectMocks
    private SpendService spendService;

    @Mock
    private SplitWise splitWise;

    @Test
    public void spendTest(){
        Mockito.when(splitWise.getUserMap()).thenReturn(new HashMap<>());
        try {
            String result = spendService.spend("50","test_user1",new ArrayList<>());
        } catch (MemberNotFoundException e) {
            Assert.assertSame(e.getMessage(), "MEMBER_NOT_FOUND");
        }
    }

    @Test
    public void spendTest2(){
        Map<String, User> map = new HashMap<>();
        map.put("test_user1", new User("test_user1"));
        Mockito.when(splitWise.getUserMap()).thenReturn(map);
        List<String> members = new ArrayList<>(Arrays.asList("test_user2"));
        try {
            String result = spendService.spend("50","test_user1",members);
        } catch (MemberNotFoundException e) {
            Assert.assertSame(e.getMessage(), "MEMBER_NOT_FOUND");
        }
    }

    @Test
    public void spendTest3(){
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
        List<String> members = new ArrayList<>(Arrays.asList("test_user2"));
        try {
            String result = spendService.spend("500","test_user1",members);
            Assert.assertEquals(result,"SUCCESS");
        } catch (MemberNotFoundException e) {
            Assert.assertSame(e.getMessage(), "MEMBER_NOT_FOUND");
        }
    }

    @Test
    public void spendTest4(){
        Map<String, User> map = new HashMap<>();
        User user = new User("test_user1");
        User user1 = new User("test_user2");
        User user3 = new User("test_user3");
        map.put("test_user2", user1);
        user1.getExpenseMap().put(user,500);
        user.getExpenseMap().put(user1, -500);
        user.getExpenseMap().put(user3,-1000);
        user3.getExpenseMap().put(user,1000);
        map.put("test_user1", user);
        map.put("test_user3",user3);
        Mockito.when(splitWise.getUserMap()).thenReturn(map);
        Mockito.when(splitWise.getUserByName("test_user1")).thenReturn(user);
        Mockito.when(splitWise.getUserByName("test_user3")).thenReturn(user3);
        Mockito.when(splitWise.getUserByName("test_user2")).thenReturn(user1);
        List<String> members = new ArrayList<>(Arrays.asList("test_user2","test_user3"));
        try {
            String result = spendService.spend("20","test_user1",members);
            Assert.assertEquals(result,"SUCCESS");
        } catch (MemberNotFoundException e) {
            Assert.assertSame(e.getMessage(), "MEMBER_NOT_FOUND");
        }
    }
}
