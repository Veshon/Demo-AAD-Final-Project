package com.example.demo.service.impl;

import com.example.demo.dao.EquipmentDAO;
import com.example.demo.dao.FieldDAO;
import com.example.demo.dto.impl.CropDTO;
import com.example.demo.dto.impl.EquipmentDTO;
import com.example.demo.entity.impl.CropEntity;
import com.example.demo.entity.impl.EquipmentEntity;
import com.example.demo.exception.DataPersistException;
import com.example.demo.service.EquipmentService;
import com.example.demo.util.AppUtil;
import com.example.demo.util.Mapping;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional

public class EquipmentServiceIMPL implements EquipmentService {

    @Autowired
    public EquipmentDAO equipmentDAO;

    @Autowired
    public Mapping mapping;

    @Override
    public void saveEquipment(EquipmentDTO equipmentDTO) {
        equipmentDTO.setEquipmentId(AppUtil.generateEquipmentId());

        EquipmentEntity savedEquipment =
                equipmentDAO.save(mapping.toEquipmentEntity(equipmentDTO));
        if (savedEquipment == null) {
            throw new DataPersistException("Equipment not saved");
        }
    }
}
