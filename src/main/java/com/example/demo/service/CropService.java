package com.example.demo.service;

import com.example.demo.dto.impl.CropDTO;
import com.example.demo.dto.impl.FieldDTO;

import java.util.List;

public interface CropService {
    void saveCrop(CropDTO cropDTO);
    List<CropDTO> getAllCrops();
}
