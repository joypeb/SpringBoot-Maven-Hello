package com.example.hello.hospital.controller;

import com.example.hello.hospital.dao.HospitalDao;
import com.example.hello.hospital.dto.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HospitalController {

    private final HospitalDao hospitalDao;

    @Autowired
    public HospitalController(HospitalDao hospitalDao) {
        this.hospitalDao = hospitalDao;
    }

    @GetMapping("/select-one/{id}")
    public ResponseEntity<String> selectOne(@PathVariable int id) {

        Hospital hospital = hospitalDao.findById(id);

        return new ResponseEntity<>( hospital.toString(), HttpStatus.OK);
    }
}
