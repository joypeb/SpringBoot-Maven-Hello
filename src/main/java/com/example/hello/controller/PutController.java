package com.example.hello.controller;

import com.example.hello.domain.dto.MemberDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/vi/put-api")
public class PutController {

    @PostMapping("member3")
    public ResponseEntity<MemberDto> putMember3(@RequestBody MemberDto memberDto) {
        return ResponseEntity.status(HttpStatus.OK).body(memberDto);
    }

    @PostMapping("member4")
    public ResponseEntity<String> putMember4(@RequestBody MemberDto memberDto) {
        URI location = URI.create("locationTest");
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        responseHeaders.set("MyResponseHeader", "MyValue");
        responseHeaders.set("test1","test2");
        return new ResponseEntity<String>("bodybody", responseHeaders, HttpStatus.CREATED);
    }
}
