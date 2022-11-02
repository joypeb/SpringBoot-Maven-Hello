package com.example.hello.hospital.parser;

import com.example.hello.hospital.dto.Hospital;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class HospitalParser implements Parser<Hospital>{

    @Override
    public Hospital parse(String str) {
        String[] row = str.replaceAll("\"","").split("\t");
        int year = 1900,month = 1,day = 1;
        try {
            year = Integer.parseInt(row[5].substring(0, 4));
            month = Integer.parseInt(row[5].substring(4, 6));
            day = Integer.parseInt(row[5].substring(6, 8));
        } catch (Exception e) {
            log.error(row[0] + "id error 날짜 형식 오류 : " + row[5]);
        }
        Hospital hospital = new Hospital();
        hospital.setId(Integer.parseInt(row[0].replace("\"","")));
        hospital.setOpenServiceName(row[1]);
        hospital.setOpenLocalGovernmentCode(Integer.parseInt(row[3]));
        hospital.setManagementNumber(row[4]);
        hospital.setLicenseDate(LocalDateTime.of(year,month,day,0,0,0));
        hospital.setBusinessStatus(Integer.parseInt(row[7]));
        hospital.setBusinessStatusCode(Integer.parseInt(row[9]));
        hospital.setPhone(row[15]);
        hospital.setFullAddress(row[18]);
        hospital.setRoadNameAddress(row[19].replaceAll("\"",""));
        hospital.setHospitalName(row[21]);
        hospital.setBusinessTypeName(row[25]);
        hospital.setHealthcareProviderCount(Integer.parseInt(row[29]));
        hospital.setPatientRoomCount(Integer.parseInt(row[30]));
        hospital.setTotalNumberOfBeds(Integer.parseInt(row[31]));
        hospital.setTotalAreaSize(Float.parseFloat(row[32]));

        return hospital;
    }
}
