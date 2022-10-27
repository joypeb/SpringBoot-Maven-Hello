package com.example.hello.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vi/get-api")
public class HelloController {

    @RequestMapping("/hello")
    //그냥 RequestMapping은 모든 메소드(GET, POST...)가 다 가능하다
    public String hello() {
        return "Hello";
    }

    @RequestMapping(value = "/hello2", method = RequestMethod.GET)
    public String hello2() {
        return "hello GET";
    }

    @GetMapping(value = "/hello3")
    public String hello3() {
        return "hello GetMapping";
    }

    @GetMapping(value = "/variable1/{variable}")
    public String getVariable1(@PathVariable String variable) {
        return "hello " + variable;
    }

    @GetMapping(value = "/request1")
    public String getVariable2(@RequestParam String name, String email ,int num){
        String result = name + ", " + email + ", " + num;
        return result;
    }
}
