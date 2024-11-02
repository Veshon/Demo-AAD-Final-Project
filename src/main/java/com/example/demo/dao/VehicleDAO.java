package com.example.demo.dao;

import com.example.demo.entity.impl.StaffEntity;
import com.example.demo.entity.impl.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface VehicleDAO extends JpaRepository<VehicleEntity, String> {
}
