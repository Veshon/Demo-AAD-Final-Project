package com.example.demo.service.impl;

import com.example.demo.customStatusCode.SelectedErrorStatus;
import com.example.demo.dao.EquipmentDAO;
import com.example.demo.dao.FieldDAO;
import com.example.demo.dto.EquipmentStatus;
import com.example.demo.dto.impl.CropDTO;
import com.example.demo.dto.impl.EquipmentDTO;
import com.example.demo.entity.impl.CropEntity;
import com.example.demo.entity.impl.EquipmentEntity;
import com.example.demo.exception.DataPersistException;
import com.example.demo.exception.FieldNotFoundException;
import com.example.demo.service.EquipmentService;
import com.example.demo.util.AppUtil;
import com.example.demo.util.Mapping;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<EquipmentDTO> getAllEquipments() {
        List<EquipmentEntity> allEquipments = equipmentDAO.findAll();
        return mapping.asEquipmentDTOList(allEquipments);
    }

    @Override
    public EquipmentStatus getEquipment(String id) {
        if (equipmentDAO.existsById(id)){
            EquipmentEntity selectedEquipment = equipmentDAO.getReferenceById(id);
            return mapping.toEquipmentDTO(selectedEquipment);
        }
        return (EquipmentStatus) new SelectedErrorStatus(2, "Equipment with id " + id + " not found");
    }

    @Override
    public void deleteEquipment(String id) {
        Optional<EquipmentEntity> existedEquipment = equipmentDAO.findById(id);

        if (!existedEquipment.isPresent()){
            throw new FieldNotFoundException("Equipment id" + id + "Not found");
        }else {
            equipmentDAO.deleteById(id);
        }
    }

    @Override
    public void updateEquipment(String id, EquipmentDTO equipmentDTO) {
        Optional<EquipmentEntity> tmpEquipment = equipmentDAO.findById(id); // optional cuz to reduce null point exception
        if (tmpEquipment.isPresent()){
            tmpEquipment.get().setName(equipmentDTO.getName());
            tmpEquipment.get().setType(equipmentDTO.getType());
            tmpEquipment.get().setStatus(equipmentDTO.getStatus());
        }
    }
}
