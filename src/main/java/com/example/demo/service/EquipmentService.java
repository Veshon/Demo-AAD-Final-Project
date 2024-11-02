package com.example.demo.service;

import com.example.demo.dto.CropStatus;
import com.example.demo.dto.EquipmentStatus;
import com.example.demo.dto.impl.CropDTO;
import com.example.demo.dto.impl.EquipmentDTO;

import java.util.List;

public interface EquipmentService {
    void saveEquipment(EquipmentDTO equipmentDTO);
    List<EquipmentDTO> getAllEquipments();
    EquipmentStatus getEquipment(String id);
    void deleteEquipment(String id);
    void updateEquipment(String id, EquipmentDTO equipmentDTO);

}
