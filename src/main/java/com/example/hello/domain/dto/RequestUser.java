package com.example.hello.domain.dto;

import com.example.hello.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RequestUser {

    private final UserDao userDao;

    public RequestUser(UserDao userDao) {
        this.userDao = userDao;
    }

    public String selectAll() {
        List<User> userList = userDao.selectAll();
        StringBuilder sb = new StringBuilder();

        for (User user : userList) {
            sb.append(user);
            sb.append("\n");
        }

        return sb.toString();
    }
}
