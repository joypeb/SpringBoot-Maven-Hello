package com.example.hello.controller;

import com.example.hello.dao.UserDao;
import com.example.hello.domain.dto.RequestUser;
import com.example.hello.domain.dto.User;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserDao userDao;
    private final RequestUser requestUser;

    public UserController(UserDao userDao, RequestUser requestUser) {
        this.userDao = userDao;
        this.requestUser = requestUser;
    }


    @PostMapping("/user-add")
    public ResponseEntity<String> addUser(User user) {
        int result = userDao.addUser(user);
        if(result >= 1) {
            return new ResponseEntity<>(user.toString(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("생성 실패", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<String> deleteAll() {
        int result = userDao.deleteAll();
        if(result >= 1) {
            return new ResponseEntity<>("전체삭제 성공",HttpStatus.OK);
        } else {
            return new ResponseEntity<>("전체삭제 실패",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<String> deleteUser(@RequestParam String id) {
        int result = userDao.deleteUser(id);
        if(result == 1) {
            return new ResponseEntity<>("id : " + id + " 삭제 성공",HttpStatus.OK);
        } else {
            return new ResponseEntity<>("삭제 실패",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/select-all")
    public ResponseEntity<String> selectAll() {
        String result = requestUser.selectAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("select-one")
    public ResponseEntity<String> selectOne(@RequestParam String id) {
        User userOne = userDao.selectOne(id);
        return new ResponseEntity<>(userOne.toString(),HttpStatus.OK);
    }
}
