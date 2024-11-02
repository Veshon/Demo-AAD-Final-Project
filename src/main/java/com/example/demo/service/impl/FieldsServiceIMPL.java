package com.example.demo.service.impl;

import com.example.demo.dao.CropDAO;
import com.example.demo.dao.FieldsDAO;
import com.example.demo.dto.impl.FieldsDTO;
import com.example.demo.entity.impl.CropEntity;
import com.example.demo.entity.impl.FieldEntity;
import com.example.demo.exception.DataPersistException;
import com.example.demo.service.FieldsService;
import com.example.demo.util.AppUtil;
import com.example.demo.util.Mapping;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional

public class FieldsServiceIMPL implements FieldsService {

    @Autowired
    private FieldsDAO fieldsDAO;

    @Autowired
    private Mapping mapping;

    @Override
    public void saveField(FieldsDTO fieldsDTO) {
        fieldsDTO.setFieldCode(AppUtil.generateFieldCode());

        FieldEntity saveField =
                fieldsDAO.save(mapping.toFieldEntity(fieldsDTO));
        if (saveField == null) {
            throw new DataPersistException("Field not saved");
        }
    }
}
