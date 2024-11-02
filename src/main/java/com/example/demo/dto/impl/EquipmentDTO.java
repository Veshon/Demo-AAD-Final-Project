package com.example.demo.dto.impl;

import com.example.demo.dto.EquipmentStatus;
import com.example.demo.entity.EquipmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class EquipmentDTO implements EquipmentStatus {

    private String equipmentId;
    private String name;
    private String type;
    private String status;
    private String fieldCode;
    private String staffId;
}
