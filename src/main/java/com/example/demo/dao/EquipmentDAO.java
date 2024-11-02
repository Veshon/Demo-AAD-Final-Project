package com.example.demo.dao;

import com.example.demo.entity.impl.EquipmentEntity;
import com.example.demo.entity.impl.FieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface EquipmentDAO extends JpaRepository<EquipmentEntity, String> {
}
