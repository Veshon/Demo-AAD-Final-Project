package com.example.demo.service;

import com.example.demo.dto.CropStatus;
import com.example.demo.dto.VehicleStatus;
import com.example.demo.dto.impl.CropDTO;
import com.example.demo.dto.impl.FieldDTO;
import com.example.demo.dto.impl.VehicleDTO;

import java.util.List;

public interface VehicleService {
    void saveVehicle(VehicleDTO vehicleDTO);
    List<VehicleDTO> getAllVehicles();
    VehicleStatus getVehicle(String code);

}
