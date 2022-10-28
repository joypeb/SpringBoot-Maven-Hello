package com.example.hello.controller;

import com.example.hello.dao.UserDao;
import com.example.hello.domain.dto.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @PostMapping("/user-add")
    public String addUser(User user) {
        int result = userDao.addUser(user);
        if(result == 1) {
            return user.toString() + "\n추가 성공";
        } else {
            return "추가 실패";
        }
    }

    @GetMapping("/delete-all")
    public String deleteAll() {
        int result = userDao.deleteAll();
        if(result == 1) {
            return "전체삭제 성공";
        } else {
            return "전체삭제 실패";
        }
    }

    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam String id) {
        int result = userDao.deleteUser(id);
        if(result == 1) {
            return "id : " + id + " 삭제 성공";
        } else {
            return "삭제 실패";
        }
    }
}
