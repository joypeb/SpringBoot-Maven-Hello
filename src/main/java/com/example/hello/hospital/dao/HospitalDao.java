package com.example.hello.hospital.dao;

import com.example.hello.hospital.dto.Hospital;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class HospitalDao {
    private final JdbcTemplate jdbcTemplate;

    int batchSize = 10000;

    @Autowired
    public HospitalDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveAll(List<Hospital> hospitalList) {
        //배치의 개수를 위한 카운트 설정
        int batchCount = 0;
        List<Hospital> subHospitals = new ArrayList<>();

        //데이터의 개수만큼 for문을 실행하며 subHospitals라는 여러 조각으로 나눈 list에 담음
        for (int i = 0; i < hospitalList.size(); i++) {
            subHospitals.add(hospitalList.get(i));
            //설정한 배치의 사이즈만큼 list가 가득차면 batchInsert실행
            //돌려받은 배치카운트에 값을 넣음 즉 batchCount++
            if ((i + 1) % batchSize == 0) {
                batchCount = batchInsert(batchCount, subHospitals);
            }
        }
        //for문이 끝나고 남은 데이터가 있을경우 insert해줌
        if (!subHospitals.isEmpty()) {
            batchCount = batchInsert(batchCount, subHospitals);
        }
        System.out.println("batchCount: " + batchCount);
    }

    /*//simpleBatch - SimpleJdbcTemplate필요
    private int simpleBatch(List<Hospital> subHospitals) {
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(subHospitals.toArray());
        jdbcTemplate.batchUpdate("INSERT INTO nation_wide_hospitals (id, open_service_name, open_local_government_code, management_number, license_date," +
                " business_status, business_status_code,phone, full_address, road_name_address, hospital_name," +
                " business_type_name, healthcare_provider_count, patient_room_count, total_number_of_beds, total_area_size)"
                + "VALUES (:id,:openServiceName,:openLocalGovernmentCode,:managementNumber,:licenseDate,:businessStatus,:businessStatusCode," +
                ":phone,:fullAddress,:roadNameAddress,:hospitalName,:businessTypeName,:healthcareProviderCount,:patientRoomCount,:totalNumberOfBeds,:totalAreaSize);", batch);

    }*/

    private int batchInsert(int batchCount, List<Hospital> subHospitals) {
        jdbcTemplate.batchUpdate("INSERT INTO nation_wide_hospitals (id, open_service_name, open_local_government_code, management_number, license_date,"+
                " business_status, business_status_code,phone, full_address, road_name_address, hospital_name,"+
                " business_type_name, healthcare_provider_count, patient_room_count, total_number_of_beds, total_area_size)"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, subHospitals.get(i).getId());
                ps.setString(2, subHospitals.get(i).getOpenServiceName());
                ps.setInt(3, subHospitals.get(i).getOpenLocalGovernmentCode());
                ps.setString(4, subHospitals.get(i).getManagementNumber());
                ps.setTimestamp(5, Timestamp.valueOf(subHospitals.get(i).getLicenseDate()));
                ps.setInt(6, subHospitals.get(i).getBusinessStatus());
                ps.setInt(7, subHospitals.get(i).getBusinessStatusCode());
                ps.setString(8, subHospitals.get(i).getPhone());
                ps.setString(9, subHospitals.get(i).getFullAddress());
                ps.setString(10, subHospitals.get(i).getRoadNameAddress());
                ps.setString(11, subHospitals.get(i).getHospitalName());
                ps.setString(12, subHospitals.get(i).getBusinessTypeName());
                ps.setInt(13, subHospitals.get(i).getHealthcareProviderCount());
                ps.setInt(14, subHospitals.get(i).getPatientRoomCount());
                ps.setInt(15, subHospitals.get(i).getTotalNumberOfBeds());
                ps.setFloat(16, subHospitals.get(i).getTotalAreaSize());

            }

            @Override
            public int getBatchSize() {
                return subHospitals.size();
            }
        });
        subHospitals.clear();
        batchCount++;
        return batchCount;
    }

    public int add(Hospital hospital) {
        String sql = "INSERT INTO nation_wide_hospitals (id, open_service_name, open_local_government_code, management_number, license_date,"+
                " business_status, business_status_code,phone, full_address, road_name_address, hospital_name,"+
                " business_type_name, healthcare_provider_count, patient_room_count, total_number_of_beds, total_area_size)"
         + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

        return this.jdbcTemplate.update(sql, hospital.getId(),hospital.getOpenServiceName(),hospital.getOpenLocalGovernmentCode()
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

    public Hospital findById(int id) {
        String sql = "SELECT * FROM nation_wide_hospitals WHERE id = ?";
        return this.jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    RowMapper<Hospital> rowMapper = new RowMapper<Hospital>() {
        @Override
        public Hospital mapRow(ResultSet rs, int rowNum) throws SQLException {
            Hospital hospital = new Hospital();

            hospital.setId(rs.getInt("id"));
            hospital.setOpenServiceName(rs.getString("open_service_name"));
            hospital.setOpenLocalGovernmentCode(rs.getInt("open_local_government_code"));
            hospital.setManagementNumber(rs.getString("management_number"));
            hospital.setLicenseDate(rs.getTimestamp("license_date").toLocalDateTime());
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
