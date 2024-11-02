package com.example.demo.service.impl;

import com.example.demo.customStatusCode.SelectedErrorStatus;
import com.example.demo.dao.FieldDAO;
import com.example.demo.dao.StaffDAO;
import com.example.demo.dto.StaffStatus;
import com.example.demo.dto.impl.CropDTO;
import com.example.demo.dto.impl.StaffDTO;
import com.example.demo.entity.impl.CropEntity;
import com.example.demo.entity.impl.StaffEntity;
import com.example.demo.exception.DataPersistException;
import com.example.demo.service.StaffService;
import com.example.demo.util.AppUtil;
import com.example.demo.util.Mapping;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional

public class StaffServiceIMPL implements StaffService {

    @Autowired
    public StaffDAO staffDAO;

    @Autowired
    public Mapping mapping;


    @Override
    public void saveStaff(StaffDTO staffDTO) {
        staffDTO.setId(AppUtil.generateStaffId());

        StaffEntity savedStaff =
                staffDAO.save(mapping.toStaffEntity(staffDTO));
        if (savedStaff == null) {
            throw new DataPersistException("Staff not saved");
        }
    }

    @Override
    public List<StaffDTO> getAllStaff() {
        List<StaffEntity> allStaff = staffDAO.findAll();
        return mapping.asStaffDTOList(allStaff);
    }

    @Override
    public StaffStatus getStaff(String id) {
        if (staffDAO.existsById(id)){
            StaffEntity selectedStaff = staffDAO.getReferenceById(id);
            return mapping.toStaffDTO(selectedStaff);
        }
        return (StaffStatus) new SelectedErrorStatus(2, "Staff with code " + id + " not found");
    }
}
