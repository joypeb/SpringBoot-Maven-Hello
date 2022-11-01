package com.example.hello.hospital.dao;

import com.example.hello.hospital.dto.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class HospitalDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HospitalDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(Hospital hospital) {
        String sql = "INSERT INTO likelionDB.nation_wide_hospitals (id, open_service_name, open_local_government_code, management_number, license_date,"+
                " business_status, business_status_code,phone, full_address, road_name_address, hospital_name,"+
                " business_type_name, healthcare_provider_count, patient_room_count, total_number_of_beds, total_area_size)"
         + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

        this.jdbcTemplate.update(sql, hospital.getId(),hospital.getOpenServiceName(),hospital.getOpenLocalGovernmentCode()
                                ,hospital.getManagementNumber(),hospital.getLicenseDate(),hospital.getBusinessStatus()
                                ,hospital.getPhone(),hospital.getFullAddress(),hospital.getRoadNameAddress(),hospital.getHospitalName()
                                ,hospital.getBusinessTypeName(),hospital.getHealthcareProviderCount(),hospital.getPatientRoomCount()
                                ,hospital.getTotalNumberOfBeds(),hospital.getTotalAreaSize());
    }
}
