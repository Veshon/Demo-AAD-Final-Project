package com.example.demo.service.impl;

import com.example.demo.dao.StaffDAO;
import com.example.demo.dao.VehicleDAO;
import com.example.demo.dto.impl.VehicleDTO;
import com.example.demo.entity.impl.FieldEntity;
import com.example.demo.entity.impl.VehicleEntity;
import com.example.demo.exception.DataPersistException;
import com.example.demo.service.VehicleService;
import com.example.demo.util.AppUtil;
import com.example.demo.util.Mapping;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional

public class VehicleServiceIMPL implements VehicleService {

    @Autowired
    public VehicleDAO vehicleDAO;

    @Autowired
    public Mapping mapping;


    @Override
    public void saveVehicle(VehicleDTO vehicleDTO) {
        vehicleDTO.setVehicleCode(AppUtil.generateVehicleCode());

        VehicleEntity savedVehicle =
                vehicleDAO.save(mapping.toVehicleEntity(vehicleDTO));
        if (savedVehicle == null) {
            throw new DataPersistException("Vehicle not saved");
        }
    }
}
