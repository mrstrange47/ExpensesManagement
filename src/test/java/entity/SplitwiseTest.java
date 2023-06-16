package entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class SplitwiseTest {

    @InjectMocks
    private SplitWise splitWise;

    @Test
    public void getUserMapTest(){
        Map<String,User> map = new HashMap<>();
        splitWise.getUserMap();
    }

    @Test
    public void getUserByNameTest(){
        User user = splitWise.getUserByName("test_user");
    }
}
