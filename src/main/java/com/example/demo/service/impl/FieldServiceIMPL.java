package com.example.demo.service.impl;

import com.example.demo.dao.FieldDAO;
import com.example.demo.dto.impl.FieldDTO;
import com.example.demo.entity.impl.FieldEntity;
import com.example.demo.exception.DataPersistException;
import com.example.demo.service.FieldService;
import com.example.demo.util.AppUtil;
import com.example.demo.util.Mapping;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional

public class FieldServiceIMPL implements FieldService {

    @Autowired
    public FieldDAO fieldDAO;

    @Autowired
    public Mapping mapping;

    @Override
    public void saveField(FieldDTO fieldDTO) {
        FieldEntity savedField = fieldDAO.save(mapping.toFieldEntity(fieldDTO));
        if (savedField == null) {
            throw new DataPersistException("Field not saved");
        }
    }
}
