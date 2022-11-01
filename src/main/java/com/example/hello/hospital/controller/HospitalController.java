package com.example.hello.hospital.controller;

import com.example.hello.hospital.dao.HospitalDao;
import com.example.hello.hospital.dto.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HospitalController {

    private final HospitalDao hospitalDao;

    @Autowired
    public HospitalController(HospitalDao hospitalDao) {
        this.hospitalDao = hospitalDao;
    }

    @GetMapping("/add-hospital")
    public ResponseEntity<String> addHospital() {
        int cnt = 0;

        return new ResponseEntity<>(cnt + "건 DB추가 완료", HttpStatus.OK);
    }
}
