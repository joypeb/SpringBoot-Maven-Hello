package com.example.hello.hospital.dao;

import com.example.hello.hospital.dto.Hospital;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
@Slf4j
public class HospitalDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HospitalDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(Hospital hospital) {
        String sql = "INSERT INTO nation_wide_hospitals (id, open_service_name, open_local_government_code, management_number, license_date,"+
                " business_status, business_status_code,phone, full_address, road_name_address, hospital_name,"+
                " business_type_name, healthcare_provider_count, patient_room_count, total_number_of_beds, total_area_size)"
         + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

        this.jdbcTemplate.update(sql, hospital.getId(),hospital.getOpenServiceName(),hospital.getOpenLocalGovernmentCode()
                                ,hospital.getManagementNumber(),hospital.getLicenseDate(),hospital.getBusinessStatus(),hospital.getBusinessStatusCode()
                                ,hospital.getPhone(),hospital.getFullAddress(),hospital.getRoadNameAddress(),hospital.getHospitalName()
                                ,hospital.getBusinessTypeName(),hospital.getHealthcareProviderCount(),hospital.getPatientRoomCount()
                                ,hospital.getTotalNumberOfBeds(),hospital.getTotalAreaSize());
    }

    public void deleteAll() {
        this.jdbcTemplate.update("delete from nation_wide_hospitals;");
    }

    public int getCount() {
        int cnt = this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM nation_wide_hospitals;",Integer.class);
        return cnt;
    }

    public Hospital findById(String id) {
        String sql = "SELECT * FROM nation_wide_hospitals WHERE id = ?";
        return this.jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    RowMapper<Hospital> rowMapper = new RowMapper<Hospital>() {
        @Override
        public Hospital mapRow(ResultSet rs, int rowNum) throws SQLException {
            Hospital hospital = new Hospital();
            int year=1900, month=1, day=1;

            hospital.setId(rs.getInt("id"));
            hospital.setOpenServiceName(rs.getString("open_service_name"));
            hospital.setOpenLocalGovernmentCode(rs.getInt("open_local_government_code"));
            hospital.setManagementNumber(rs.getString("management_number"));
            try {
                year = Integer.parseInt(rs.getString("license_date").substring(0, 4));
                month = Integer.parseInt(rs.getString("license_date").substring(5, 7));
                day = Integer.parseInt(rs.getString("license_date").substring(8, 10));

                if(year == 0) year = 1;
                if(month == 0) month = 1;
                if(day == 0) day = 1;
            } catch (Exception e) {
                log.error(rs.getString("license_date") + "id error 날짜 형식 오류 : ");
            }
            hospital.setLicenseDate(LocalDateTime.of(year,month,day,0,0,0));
            hospital.setBusinessStatus(rs.getInt("business_status"));
            hospital.setBusinessStatusCode(rs.getInt("business_status_code"));
            hospital.setPhone(rs.getString("phone"));
            hospital.setFullAddress(rs.getString("full_address"));
            hospital.setRoadNameAddress(rs.getString("road_name_address"));
            hospital.setHospitalName(rs.getString("hospital_name"));
            hospital.setBusinessTypeName(rs.getString("business_type_name"));
            hospital.setHealthcareProviderCount(rs.getInt("healthcare_provider_count"));
            hospital.setPatientRoomCount(rs.getInt("patient_room_count"));
            hospital.setTotalNumberOfBeds(rs.getInt("total_number_of_beds"));
            hospital.setTotalAreaSize(rs.getFloat("total_area_size"));

            return hospital;
        }
    };
}
