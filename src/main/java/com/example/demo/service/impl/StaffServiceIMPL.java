package com.example.demo.service.impl;

import com.example.demo.customStatusCode.SelectedErrorStatus;
import com.example.demo.dao.StaffDAO;
import com.example.demo.dto.StaffStatus;
import com.example.demo.dto.impl.StaffDTO;
import com.example.demo.entity.impl.StaffEntity;
import com.example.demo.exception.DataPersistException;
import com.example.demo.exception.FieldNotFoundException;
import com.example.demo.service.StaffService;
import com.example.demo.util.AppUtil;
import com.example.demo.util.Mapping;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return (StaffStatus) new SelectedErrorStatus(2, "Staff with id " + id + "not found");
    }

    @Override
    public void deleteStaff(String id) {
        Optional<StaffEntity> existedStaff= staffDAO.findById(id);

        if (!existedStaff.isPresent()){
            throw new FieldNotFoundException("Staff id" + id + "not found");
        }else {
            staffDAO.deleteById(id);
        }
    }

    @Override
    public void updateStaff(String id, StaffDTO staffDTO) {
        Optional<StaffEntity> tmpStaff = staffDAO.findById(id); // optional cuz to reduce null point exception
        if (tmpStaff.isPresent()){
            tmpStaff.get().setFirstName(staffDTO.getFirstName());
            tmpStaff.get().setLastName(staffDTO.getLastName());
            tmpStaff.get().setDesignation(staffDTO.getDesignation());
            tmpStaff.get().setGender(staffDTO.getGender());
            tmpStaff.get().setJoinedDate(staffDTO.getJoinedDate());
            tmpStaff.get().setDob(staffDTO.getDob());
            tmpStaff.get().setAddressLine01(staffDTO.getAddressLine01());
            tmpStaff.get().setAddressLine02(staffDTO.getAddressLine02());
            tmpStaff.get().setAddressLine03(staffDTO.getAddressLine03());
            tmpStaff.get().setAddressLine04(staffDTO.getAddressLine04());
            tmpStaff.get().setAddressLine05(staffDTO.getAddressLine05());
            tmpStaff.get().setContactNo(staffDTO.getContactNo());
            tmpStaff.get().setEmail(staffDTO.getEmail());
            tmpStaff.get().setRole(staffDTO.getRole());
        }
    }
}
