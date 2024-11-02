package com.example.demo.service;

import com.example.demo.dto.FieldStatus;
import com.example.demo.dto.impl.CropDTO;
import com.example.demo.dto.impl.FieldDTO;

import java.util.List;

public interface FieldService {
    void saveField(FieldDTO fieldDTO);
    List<FieldDTO> getAllFields();
    FieldStatus getField(String fieldCode);
    void deleteField(String fieldCode);
    void updateField(String fieldCode, FieldDTO userDTO);

}
