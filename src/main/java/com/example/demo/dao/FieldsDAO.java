package com.example.demo.dao;

import com.example.demo.entity.impl.FieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldsDAO extends JpaRepository<FieldEntity, String> {
}
