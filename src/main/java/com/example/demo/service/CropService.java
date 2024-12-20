package com.example.demo.service;

import com.example.demo.dto.CropStatus;
import com.example.demo.dto.FieldStatus;
import com.example.demo.dto.impl.CropDTO;

import java.util.List;

public interface CropService {
    void saveCrop(CropDTO cropDTO);
    List<CropDTO> getAllCrops();
    CropStatus getCrop(String code);
    void deleteCrop(String code);
    void updateCrop(String code, CropDTO cropDTO);

}
