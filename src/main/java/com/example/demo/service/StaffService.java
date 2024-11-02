package com.example.demo.service;

import com.example.demo.dto.impl.CropDTO;
import com.example.demo.dto.impl.StaffDTO;

import java.util.List;

public interface StaffService {
    void saveStaff(StaffDTO staffDTO);
    List<StaffDTO> getAllStaff();
}
