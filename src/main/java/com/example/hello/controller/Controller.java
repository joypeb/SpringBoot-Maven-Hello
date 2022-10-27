package com.example.hello.controller;

import com.example.hello.domain.dto.MemberDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/vi/get-api")
public class Controller {

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
    public String getVariable(@PathVariable String variable) {
        return "hello " + variable;
    }

    @GetMapping(value = "/request1")
    public String getVariable1(@RequestParam String name, String email ,int num){
        String result = name + ", " + email + ", " + num;
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

    @RequestMapping(value = "/domain", method = RequestMethod.POST)
    public String postExample() {
        return "Hello Post API";
    }

    @PostMapping("member1")
    public String postMember(@RequestBody Map<String, Object> postData) {
        StringBuilder sb = new StringBuilder(); // Builder Pattern
        postData.entrySet().forEach(map->sb.append(map.getKey()+":"+map.getValue()+"\n"));
        return sb.toString();
    }

    @PostMapping("member2")
    public String postMember2(@RequestBody MemberDto memberDto) {
        return memberDto.toString();
    }

    @PostMapping("member3")
    public ResponseEntity<MemberDto> putMember3(@RequestBody MemberDto memberDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(memberDto);
    }
}
