package com.example.hello.hospital;

import com.example.hello.hospital.dao.HospitalDao;
import com.example.hello.hospital.dto.Hospital;
import com.example.hello.hospital.parser.HospitalParser;
import com.example.hello.hospital.parser.ReadLineContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class hospitalTest {

    @Autowired
    ReadLineContext<Hospital> hospitalReadLineContext;

    @Autowired
    HospitalDao hospitalDao;

    String line = "1\",\"의원\",\"01_01_02_P\",\"3620000\",\"PHMA119993620020041100004\",\"19990612\",\"\",\"01\",\"영업/정상\",\"13\",\"영업중\",\"\",\"\",\"\",\"\",\"062-515-2875\",\"\",\"500881\",\"광주광역시 북구 풍향동 565번지 4호 3층\",\"광주광역시 북구 동문대로 24, 3층 (풍향동)\",\"61205\",\"효치과의원\",\"20211115113642\",\"U\",\"2021-11-17 02:40:00.0\",\"치과의원\",\"192630.735112\",\"185314.617632\",\"치과의원\",\"1\",\"0\",\"0\",\"52.29\",\"401\",\"치과\",\"\",\"\",\"\",\"0\",\"0\",\"\",\"\",\"0\",\"";
    @Test
    void name() throws IOException {
        String filename = "./src/main/resources/hospitalResources/hospital_fulldata.csv";
        List<Hospital> hospitalList = hospitalReadLineContext.readByLine(filename);
        System.out.println("사이즈 : " + hospitalList.size());
        assertTrue(hospitalList.size() > 10000);
    }

    @Test
    public void parserTset() {

        HospitalParser hospitalParser = new HospitalParser();

        Hospital hospital = hospitalParser.parse(line);

        System.out.println(hospital.toString());

        assertEquals(1, hospital.getId());
        assertEquals("의원", hospital.getOpenServiceName());
        assertEquals(3620000,hospital.getOpenLocalGovernmentCode());
        assertEquals("PHMA119993620020041100004",hospital.getManagementNumber());
        assertEquals(LocalDateTime.of(1999,06,12,0,0,0), hospital.getLicenseDate());
        assertEquals(1, hospital.getBusinessStatus());
        assertEquals(13, hospital.getBusinessStatusCode());
        assertEquals("062-515-2875", hospital.getPhone());
        assertEquals("광주광역시 북구 풍향동 565번지 4호 3층", hospital.getFullAddress());
        assertEquals("광주광역시 북구 동문대로 24, 3층 (풍향동)", hospital.getRoadNameAddress());
        assertEquals("효치과의원", hospital.getHospitalName());
        assertEquals("치과의원", hospital.getBusinessTypeName());
        assertEquals(1, hospital.getHealthcareProviderCount());
        assertEquals(0, hospital.getPatientRoomCount());
        assertEquals(0, hospital.getTotalNumberOfBeds());
        assertEquals(52.29f, hospital.getTotalAreaSize());

    }


    @Test
    void addHospital() {
        HospitalParser hp = new HospitalParser();
        Hospital hospital = hp.parse(line);

        hospitalDao.deleteAll();
        hospitalDao.add(hospital);
        Hospital hospital1 = hospitalDao.findById(1);

        System.out.println(hospitalDao.getCount());
        System.out.println(hospital1.toString());
    }

    @Test
    void addAllHospital() throws IOException {
        String filename = "./src/main/resources/hospitalResources/hospital_fulldata9.tsv";
        List<Hospital> hospitalList = hospitalReadLineContext.readByLine(filename);

        hospitalDao.deleteAll();
        hospitalDao.saveAll(hospitalList);

        System.out.println(hospitalDao.getCount());
    }
}
