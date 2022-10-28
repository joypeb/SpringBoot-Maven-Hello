package com.example.hello.user;

import com.example.hello.dao.UserDao;
import com.example.hello.domain.dto.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserTest {

    @Autowired
    UserDao userDao;

    private User u1;
    private User u2;
    private User u3;

    @BeforeEach
    public void before() {
        this.u1 = new User("11", "test1", "pw2");
        this.u2 = new User("12", "test2", "pw22");
        this.u3 = new User("13", "test3", "p23");
    }

    @Test
    void addAndDelete() {
        int result = userDao.deleteAll();

        int r1 = userDao.addUser(u1);
        int r2 = userDao.addUser(u2);
        int r3 = userDao.addUser(u3);

        assertEquals(1,r1);
        assertEquals(1,r2);
        assertEquals(1,r3);
    }

    @Test
    void deleteUser() {
        int r1 = userDao.deleteUser(this.u1.getId());
        assertEquals(1, r1);

        int deleteResult = userDao.deleteAll();
        assertNotEquals(0, deleteResult);
    }
}
