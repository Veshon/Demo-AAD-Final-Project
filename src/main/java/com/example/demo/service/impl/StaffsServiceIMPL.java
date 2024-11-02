package com.example.demo.service.impl;

import com.example.demo.dao.CropDAO;
import com.example.demo.dao.StaffDAO;
import com.example.demo.dto.impl.StaffsDTO;
import com.example.demo.entity.impl.CropEntity;
import com.example.demo.entity.impl.StaffEntity;
import com.example.demo.exception.DataPersistException;
import com.example.demo.service.StaffsService;
import com.example.demo.util.AppUtil;
import com.example.demo.util.Mapping;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional

public class StaffsServiceIMPL implements StaffsService {

    @Autowired
    private StaffDAO staffDAO;

    @Autowired
    private Mapping mapping;

    @Override
    public void saveStaff(StaffsDTO staffsDTO) {
        staffsDTO.setId(AppUtil.generateStaffId());

        StaffEntity savedStaff =
                staffDAO.save(mapping.toStaffEntity(staffsDTO));
        if (savedStaff == null) {
            throw new DataPersistException("Staff not saved");
        }
    }
}
