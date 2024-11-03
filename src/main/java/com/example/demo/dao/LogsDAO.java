package com.example.demo.dao;

import com.example.demo.entity.impl.FieldEntity;
import com.example.demo.entity.impl.LogsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface LogsDAO extends JpaRepository<LogsEntity, String> {
}
