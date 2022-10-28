package com.example.hello.controller;

import com.example.hello.domain.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/vi/get-api")
@Slf4j
public class GetController {

    @RequestMapping("/hello")
    //그냥 RequestMapping은 모든 메소드(GET, POST...)가 다 가능하다
    public String hello() {
        return "Hello";
    }

    @RequestMapping(value = "/hello2", method = RequestMethod.GET)
    public String hello2() {
        log.info("hello2로 요청이 들어왔습니다");
        return "hello GET";
    }

    @GetMapping(value = "/hello3")
    public String hello3() {
        return "hello GetMapping";
    }

    @GetMapping(value = "/variable1/{variable}")
    public String getVariable(@PathVariable String variable) {
        log.info("hello/"+variable+"로 요청이 들어왔습니다");
        return "hello " + variable;
    }

    @GetMapping(value = "/request1")
    public String getVariable1(@RequestParam String name, String email ,int num){
        String result = name + ", " + email + ", " + num;
        log.info(result+"요청이 들어왔습니다");
        return result;
    }

    @GetMapping(value = "/request2")
    public String getVariable2(@RequestParam Map<String, String> param) {
        String result = "";
        for(Map.Entry<String,String> map : param.entrySet()) {
            result += map.getKey() + ", " + map.getValue() + "\n";
        }
        return result;
    }

    @GetMapping(value = "/request3")
    public String getVariable3(@RequestParam String name, String email ,String organization) {
        MemberDto memberDto = new MemberDto(name,email,organization);
        return memberDto.toString();
    }

}
