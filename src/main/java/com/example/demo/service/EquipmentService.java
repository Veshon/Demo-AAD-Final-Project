package com.example.demo.service;

import com.example.demo.dto.impl.CropDTO;
import com.example.demo.dto.impl.EquipmentDTO;

import java.util.List;

public interface EquipmentService {
    void saveEquipment(EquipmentDTO equipmentDTO);
    List<EquipmentDTO> getAllEquipments();
}
