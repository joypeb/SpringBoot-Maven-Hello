package com.example.hello.hospital.controller;

import com.example.hello.hospital.dao.HospitalDao;
import com.example.hello.hospital.dto.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
        Optional<Hospital> opt = Optional.ofNullable(hospital);

        StringBuilder sb = new StringBuilder();

        sb.append("id : " + hospital.getId() +"\n");
        sb.append("병원 이름 : " + hospital.getHospitalName() +"\n");
        sb.append("주소 : " + hospital.getFullAddress() +"\n");
        sb.append("도로명 주소 : " + hospital.getRoadNameAddress() +"\n");
        sb.append("의료진 수 : " + hospital.getHealthcareProviderCount()+"\n");
        sb.append("병상 수 : " + hospital.getPatientRoomCount() +"\n");
        sb.append("면적 : " + hospital.getTotalAreaSize() +"\n");
        int code = hospital.getBusinessStatusCode();

        if(code == 13) sb.append("영업 여부 : 정상영업\n");
        else if(code == 2) sb.append("영업 여부 : 휴업\n");
        else if(code == 3) sb.append("영업 여부 : 폐업\n");
        else sb.append("영업 여부 : 알수없음\n");

        return new ResponseEntity<>(sb.toString(), HttpStatus.OK);
    }
}
