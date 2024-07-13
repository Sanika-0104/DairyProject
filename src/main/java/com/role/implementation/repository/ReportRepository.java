package com.role.implementation.repository;

import com.role.implementation.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report,Integer> {

    //List<Report> findByFarmerIdAndCollectorId(int farmerId, int collectorId);
    //List<Report> findByFarmerId(int farmerId);
//    List<Report> findByFarmerNameAndCollectorName(String farmerName, String collectorName);

}